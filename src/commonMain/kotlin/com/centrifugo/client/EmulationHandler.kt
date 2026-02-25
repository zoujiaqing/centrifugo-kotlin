package com.centrifugo.client

import com.centrifugo.client.protocol.*
import com.centrifugo.client.protocol.Command
import com.centrifugo.client.protocol.Reply
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration

@OptIn(ExperimentalAtomicApi::class)
internal class EmulationHandler(
    private val scope: CoroutineScope,
    private val codec: Codec,
    private val httpClient: HttpClient,
    private val transportType: TransportType,
    private val endpoint: String,
    private val emulationEndpoint: String,
    private val protocol: Protocol,
    private val onPush: (Reply) -> Unit,
    private val onError: (Exception) -> Unit,
) {
    private val commandId = AtomicInt(1)
    private val replyMapMutex = Mutex()
    private val replyMap = HashMap<Int, Pair<CompletableDeferred<Result<Reply>>, Job?>>()
    private val controlChannel = Channel<PendingRequest>(Channel.BUFFERED)
    private val pingChannel = Channel<Unit>(Channel.CONFLATED)

    private var session: String = ""
    private var node: String = ""

    val commandChannel: Channel<PendingRequest> get() = controlChannel

    fun sendCommand(command: Command, timeout: Duration): CompletableDeferred<Result<Reply>> {
        val deferred = CompletableDeferred<Result<Reply>>()
        val request = PendingRequest(command, deferred, timeout)
        controlChannel.trySend(request)
        return deferred
    }

    fun close() {
        controlChannel.close()
    }

    /**
     * Handle an emulation connection.
     * Returns true if should reconnect, false otherwise.
     */
    suspend fun handle(
        connectCommand: Command,
        onConnected: suspend (ConnectResult) -> Unit,
    ): Boolean {
        return coroutineScope {
            val reconnectDeferred = CompletableDeferred<Boolean>()

            // Encode the connect command
            val connectRawCommand = connectCommand.toRawCommand(nextId())
            val initialData = codec.encodeCommands(listOf(connectRawCommand))

            // Set up reply tracking for the connect command
            val connectDeferred = CompletableDeferred<Result<Reply>>()
            replyMapMutex.withLock {
                replyMap[connectRawCommand.id] = Pair(connectDeferred, null)
            }

            // Start the streaming reader
            val readerJob = launch {
                try {
                    when (transportType) {
                        TransportType.HTTP_STREAM -> handleHttpStream(initialData, reconnectDeferred)
                        TransportType.SSE -> handleSse(initialData, reconnectDeferred)
                        else -> {
                            reconnectDeferred.complete(false)
                        }
                    }
                } catch (e: Exception) {
                    onError(e)
                    if (!reconnectDeferred.isCompleted) {
                        reconnectDeferred.complete(true)
                    }
                }
            }

            // Wait for the connect reply
            val connectResult = try {
                withTimeout(Duration.parse("10s")) {
                    connectDeferred.await()
                }
            } catch (e: Exception) {
                readerJob.cancel()
                return@coroutineScope true
            }

            val reply = connectResult.getOrNull()
            when (reply) {
                is Reply.Connect -> {
                    // Store session and node for sending commands
                    session = reply.result.session
                    node = reply.result.node
                    onConnected(reply.result)

                    // Start the writer job for sending subsequent commands
                    val writerJob = launch {
                        try {
                            runWriter()
                        } catch (e: Exception) {
                            onError(e)
                        }
                    }

                    // Wait for reader to finish
                    val reconnect = reconnectDeferred.await()
                    writerJob.cancel()
                    cleanupPendingReplies()
                    reconnect
                }
                is Reply.Error -> {
                    readerJob.cancel()
                    cleanupPendingReplies()
                    reply.error.temporary
                }
                else -> {
                    readerJob.cancel()
                    cleanupPendingReplies()
                    false
                }
            }
        }
    }

    private suspend fun handleHttpStream(
        initialData: FrameData,
        reconnectDeferred: CompletableDeferred<Boolean>,
    ) {
        val contentType = if (protocol == Protocol.JSON) "application/json" else "application/octet-stream"
        val acceptType = contentType

        val bodyBytes = when (initialData) {
            is FrameData.Text -> initialData.text.encodeToByteArray()
            is FrameData.Binary -> initialData.data
        }

        val response = httpClient.post(endpoint) {
            header(HttpHeaders.ContentType, contentType)
            header(HttpHeaders.Accept, acceptType)
            setBody(bodyBytes)
        }

        val channel: ByteReadChannel = response.bodyAsChannel()

        if (protocol == Protocol.JSON) {
            readJsonStream(channel, reconnectDeferred)
        } else {
            readProtobufStream(channel, reconnectDeferred)
        }
    }

    private suspend fun readJsonStream(
        channel: ByteReadChannel,
        reconnectDeferred: CompletableDeferred<Boolean>,
    ) {
        val buffer = StringBuilder()
        try {
            while (!channel.isClosedForRead) {
                val byte = try {
                    channel.readByte()
                } catch (_: Exception) {
                    break
                }
                val char = byte.toInt().toChar()
                if (char == '\n') {
                    val line = buffer.toString()
                    buffer.clear()
                    if (line.isNotBlank()) {
                        val replies = codec.decodeReplies(FrameData.Text(line))
                        for (rawReply in replies) {
                            dispatchReply(rawReply)
                        }
                    }
                } else {
                    buffer.append(char)
                }
            }
        } catch (_: Exception) { }

        // Process any remaining data
        if (buffer.isNotEmpty()) {
            val replies = codec.decodeReplies(FrameData.Text(buffer.toString()))
            for (rawReply in replies) {
                dispatchReply(rawReply)
            }
        }

        if (!reconnectDeferred.isCompleted) {
            reconnectDeferred.complete(true)
        }
    }

    private suspend fun readProtobufStream(
        channel: ByteReadChannel,
        reconnectDeferred: CompletableDeferred<Boolean>,
    ) {
        val buffer = mutableListOf<Byte>()
        try {
            while (!channel.isClosedForRead) {
                val byte = try {
                    channel.readByte()
                } catch (_: Exception) {
                    break
                }
                buffer.add(byte)

                // Try to decode complete messages from buffer
                val bytes = buffer.toByteArray()
                val replies = codec.decodeReplies(FrameData.Binary(bytes))
                if (replies.isNotEmpty()) {
                    // Calculate consumed bytes by re-encoding check
                    // Simple approach: clear buffer after successful decode
                    buffer.clear()
                    for (rawReply in replies) {
                        dispatchReply(rawReply)
                    }
                }
            }
        } catch (_: Exception) { }

        if (!reconnectDeferred.isCompleted) {
            reconnectDeferred.complete(true)
        }
    }

    private suspend fun handleSse(
        initialData: FrameData,
        reconnectDeferred: CompletableDeferred<Boolean>,
    ) {
        // SSE: encode initial data and pass as cf_connect query parameter
        val cfConnect = when (initialData) {
            is FrameData.Text -> initialData.text
            is FrameData.Binary -> initialData.data.decodeToString()
        }

        val sseUrl = if (endpoint.contains("?")) {
            "$endpoint&cf_connect=$cfConnect"
        } else {
            "$endpoint?cf_connect=$cfConnect"
        }

        val response = httpClient.get(sseUrl) {
            header(HttpHeaders.Accept, "text/event-stream")
        }

        val channel: ByteReadChannel = response.bodyAsChannel()

        // Parse SSE stream: lines starting with "data:" contain the reply
        val buffer = StringBuilder()
        try {
            while (!channel.isClosedForRead) {
                val byte = try {
                    channel.readByte()
                } catch (_: Exception) {
                    break
                }
                val char = byte.toInt().toChar()
                if (char == '\n') {
                    val line = buffer.toString()
                    buffer.clear()
                    if (line.startsWith("data:")) {
                        val data = line.removePrefix("data:").trim()
                        if (data.isNotEmpty()) {
                            val replies = codec.decodeReplies(FrameData.Text(data))
                            for (rawReply in replies) {
                                dispatchReply(rawReply)
                            }
                        }
                    } else if (line.isNotBlank() && !line.startsWith(":") && !line.startsWith("event:") && !line.startsWith("id:") && !line.startsWith("retry:")) {
                        // Some SSE implementations send data without "data:" prefix
                        val replies = codec.decodeReplies(FrameData.Text(line))
                        for (rawReply in replies) {
                            dispatchReply(rawReply)
                        }
                    }
                } else {
                    buffer.append(char)
                }
            }
        } catch (_: Exception) { }

        if (!reconnectDeferred.isCompleted) {
            reconnectDeferred.complete(true)
        }
    }

    private suspend fun runWriter() {
        val batch = mutableListOf<PendingRequest>()
        while (true) {
            val first = controlChannel.receiveCatching().getOrNull() ?: break
            batch.add(first)

            // Drain up to 31 more
            while (batch.size < 32) {
                val next = controlChannel.tryReceive().getOrNull() ?: break
                batch.add(next)
            }

            val rawCommands = mutableListOf<RawCommand>()
            for (request in batch) {
                if (request.timeout == Duration.ZERO) {
                    request.deferred.complete(Result.failure(RequestError.Timeout))
                    continue
                }

                val id = nextId()
                val rawCommand = request.command.toRawCommand(id)
                rawCommands.add(rawCommand)

                val timeoutJob = if (request.timeout != Duration.INFINITE) {
                    scope.launch {
                        delay(request.timeout)
                        replyMapMutex.withLock {
                            val entry = replyMap.remove(id)
                            entry?.first?.complete(Result.failure(RequestError.Timeout))
                        }
                    }
                } else null

                replyMapMutex.withLock {
                    replyMap[id] = Pair(request.deferred, timeoutJob)
                }
            }
            batch.clear()

            if (rawCommands.isNotEmpty()) {
                sendEmulationRequest(rawCommands)
            }

            // Handle ping responses
            while (true) {
                pingChannel.tryReceive().getOrNull() ?: break
                val pongCommand = Command.Empty.toRawCommand(0)
                sendEmulationRequest(listOf(pongCommand))
            }
        }
    }

    private suspend fun sendEmulationRequest(commands: List<RawCommand>) {
        try {
            val frameData = codec.encodeCommands(commands)
            val body = codec.encodeEmulationRequest(session, node, frameData)

            val contentType = if (protocol == Protocol.JSON) "application/json" else "application/octet-stream"

            httpClient.post(emulationEndpoint) {
                header(HttpHeaders.ContentType, contentType)
                setBody(body)
            }
        } catch (e: Exception) {
            onError(e)
        }
    }

    private suspend fun dispatchReply(rawReply: RawReply) {
        val frameId = rawReply.id
        val reply = Reply.fromRawReply(rawReply)

        when {
            reply is Reply.Empty -> {
                // Server ping
                pingChannel.trySend(Unit)
            }
            frameId == 0 -> {
                // Push message
                onPush(reply)
            }
            else -> {
                // Reply to a command
                replyMapMutex.withLock {
                    val entry = replyMap.remove(frameId)
                    if (entry != null) {
                        entry.first.complete(Result.success(reply))
                        entry.second?.cancel()
                    }
                }
            }
        }
    }

    private suspend fun cleanupPendingReplies() {
        replyMapMutex.withLock {
            for ((_, entry) in replyMap) {
                entry.first.complete(Result.failure(RequestError.ConnectionClosed))
                entry.second?.cancel()
            }
            replyMap.clear()
        }
    }

    private fun nextId(): Int {
        while (true) {
            val id = commandId.fetchAndAdd(1)
            if (id != 0) return id
        }
    }
}
