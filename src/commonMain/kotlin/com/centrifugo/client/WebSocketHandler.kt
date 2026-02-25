package com.centrifugo.client

import com.centrifugo.client.protocol.*
import com.centrifugo.client.protocol.Command
import com.centrifugo.client.protocol.Reply
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.atomics.AtomicInt
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.time.Duration

internal data class PendingRequest(
    val command: Command,
    val deferred: CompletableDeferred<Result<Reply>>,
    val timeout: Duration,
)

@OptIn(ExperimentalAtomicApi::class)
internal class WebSocketHandler(
    private val scope: CoroutineScope,
    private val codec: Codec,
    private val onPush: (Reply) -> Unit,
    private val onError: (Exception) -> Unit,
) {
    private val commandId = AtomicInt(1)
    private val replyMapMutex = Mutex()
    private val replyMap = HashMap<Int, Pair<CompletableDeferred<Result<Reply>>, Job?>>()
    private val controlChannel = Channel<PendingRequest>(Channel.BUFFERED)
    private val pingChannel = Channel<Unit>(Channel.CONFLATED)

    val commandChannel: Channel<PendingRequest> get() = controlChannel

    suspend fun handle(session: DefaultClientWebSocketSession): Boolean {
        return coroutineScope {
            val closerDeferred = CompletableDeferred<Boolean>()

            val readerJob = launch {
                try {
                    for (frame in session.incoming) {
                        when (frame) {
                            is Frame.Text -> {
                                handleFrame(FrameData.Text(frame.readText()))
                            }
                            is Frame.Binary -> {
                                handleFrame(FrameData.Binary(frame.readBytes()))
                            }
                            is Frame.Close -> {
                                val code = frame.readReason()?.code?.toInt() ?: 0
                                val reconnect = DisconnectErrorCode.shouldReconnect(code)
                                closerDeferred.complete(reconnect)
                                return@launch
                            }
                            else -> { /* ignore */ }
                        }
                    }
                    // Channel closed normally
                    closerDeferred.complete(true)
                } catch (e: Exception) {
                    onError(e)
                    closerDeferred.complete(true)
                }
            }

            val writerJob = launch {
                try {
                    val batch = mutableListOf<PendingRequest>()
                    while (isActive) {
                        // Wait for first message
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

                            // Set up timeout
                            val timeoutJob = if (request.timeout != Duration.INFINITE) {
                                scope.launch {
                                    delay(request.timeout)
                                    replyMapMutex.withLock {
                                        val entry = replyMap.remove(id)
                                        entry?.first?.complete(Result.failure(RequestError.Timeout))
                                    }
                                }
                            } else {
                                null
                            }

                            replyMapMutex.withLock {
                                replyMap[id] = Pair(request.deferred, timeoutJob)
                            }
                        }
                        batch.clear()

                        if (rawCommands.isNotEmpty()) {
                            sendFrame(session, codec.encodeCommands(rawCommands))
                        }

                        // Handle ping responses
                        while (true) {
                            val ping = pingChannel.tryReceive().getOrNull() ?: break
                            val pongCommand = Command.Empty.toRawCommand(0)
                            sendFrame(session, codec.encodeCommands(listOf(pongCommand)))
                        }
                    }
                } catch (e: Exception) {
                    onError(e)
                }
            }

            // Wait for reader to finish (connection closed)
            val reconnect = closerDeferred.await()

            // Cancel writer
            writerJob.cancel()

            // Clean up pending replies
            replyMapMutex.withLock {
                for ((_, entry) in replyMap) {
                    entry.first.complete(Result.failure(RequestError.ConnectionClosed))
                    entry.second?.cancel()
                }
                replyMap.clear()
            }

            try {
                session.close()
            } catch (_: Exception) { }

            reconnect
        }
    }

    private fun handleFrame(data: FrameData) {
        val replies = codec.decodeReplies(data)
        for (rawReply in replies) {
            val frameId = rawReply.id
            val reply = Reply.fromRawReply(rawReply)

            when {
                reply is Reply.Empty -> {
                    // Server ping, send pong
                    pingChannel.trySend(Unit)
                }
                frameId == 0 -> {
                    // Push message
                    onPush(reply)
                }
                else -> {
                    // Reply to a command
                    scope.launch {
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
        }
    }

    private suspend fun sendFrame(session: DefaultClientWebSocketSession, frameData: FrameData) {
        when (frameData) {
            is FrameData.Text -> session.send(Frame.Text(frameData.text))
            is FrameData.Binary -> session.send(Frame.Binary(true, frameData.data))
        }
    }

    private fun nextId(): Int {
        while (true) {
            val id = commandId.fetchAndAdd(1)
            if (id != 0) return id
        }
    }

    fun sendCommand(command: Command, timeout: Duration): CompletableDeferred<Result<Reply>> {
        val deferred = CompletableDeferred<Result<Reply>>()
        val request = PendingRequest(command, deferred, timeout)
        controlChannel.trySend(request)
        return deferred
    }

    fun close() {
        controlChannel.close()
    }
}
