package com.centrifugo.client

import com.centrifugo.client.protocol.*
import com.centrifugo.client.protocol.Command
import com.centrifugo.client.protocol.Reply
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.json.JsonElement
import kotlin.time.Duration

enum class ClientState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
}

data class ServerInfo(
    val clientId: String = "",
    val serverVersion: String = "",
    val tokenExpires: Boolean = false,
    val tokenTtl: Int = 0,
    val serverPing: Int = 0,
    val sendPong: Boolean = false,
)

class CentrifugoClient(
    private val url: String,
    private val config: CentrifugoConfig = CentrifugoConfig(),
) {
    private val mutex = Mutex()
    private var _state: ClientState = ClientState.DISCONNECTED
    private var token: String = config.token
    private var connectData: JsonElement? = config.data
    private var connectHeaders: Map<String, String> = config.headers
    private var serverInfo: ServerInfo = ServerInfo()
    private val readyDeferred = CompletableDeferred<Unit>()

    // Batching
    private var batching = false
    private val batchedCommands = mutableListOf<Pair<Command, CompletableDeferred<Result<Reply>>>>()

    // Callbacks
    private var onConnecting: ((ConnectingEvent) -> Unit)? = null
    private var onConnected: ((ConnectedEvent) -> Unit)? = null
    private var onDisconnected: ((DisconnectedEvent) -> Unit)? = null
    private var onError: ((ErrorEvent) -> Unit)? = null
    private var onMessage: ((MessageEvent) -> Unit)? = null

    // Server-side subscription callbacks
    private var onServerSubscribed: ((ServerSubscribedEvent) -> Unit)? = null
    private var onServerSubscribing: ((ServerSubscribingEvent) -> Unit)? = null
    private var onServerUnsubscribed: ((ServerUnsubscribedEvent) -> Unit)? = null
    private var onServerPublication: ((ServerPublicationEvent) -> Unit)? = null
    private var onServerJoin: ((ServerJoinEvent) -> Unit)? = null
    private var onServerLeave: ((ServerLeaveEvent) -> Unit)? = null

    // Subscriptions
    private var nextSubId = 0
    private val subscriptions = HashMap<Int, SubscriptionInner>()
    private val subNameToId = HashMap<String, Int>()

    // Connection management
    private var connectionJob: Job? = null
    private var closerDeferred: CompletableDeferred<Unit>? = null
    private var handler: WebSocketHandler? = null
    private var emulationHandler: EmulationHandler? = null
    private var scope: CoroutineScope? = null
    private var currentTransportIndex = 0
    private var transportWasOpen = false

    private val codec: Codec = when (config.protocol) {
        Protocol.JSON -> JsonCodec()
        Protocol.PROTOBUF -> ProtobufCodec()
    }

    private val httpClient = HttpClient {
        install(WebSockets)
    }

    val state: ClientState get() = _state

    val client: String? get() = if (_state == ClientState.CONNECTED) serverInfo.clientId else null

    fun serverInfo(): ServerInfo = serverInfo

    suspend fun connect() {
        mutex.withLock {
            if (_state != ClientState.DISCONNECTED) return
            moveToConnecting(0, "connect called")
        }
        startConnectionCycle()
    }

    suspend fun disconnect() {
        mutex.withLock {
            if (_state == ClientState.DISCONNECTED) return
            moveToDisconnected(0, "disconnect called")
        }
    }

    suspend fun ready() {
        readyDeferred.await()
    }

    suspend fun publish(channel: String, data: JsonElement) {
        val result = sendCommand(
            Command.Publish(PublishRequest(channel = channel, data = data)),
            config.timeout
        )

        val reply = result.getOrThrow()
        when (reply) {
            is Reply.Publish -> { /* success */ }
            is Reply.Error -> throw RequestError.ErrorResponse(reply.error.code, reply.error.message)
            else -> throw RequestError.UnexpectedReply(reply)
        }
    }

    suspend fun rpc(method: String, data: JsonElement? = null): JsonElement? {
        val result = sendCommand(
            Command.Rpc(RpcRequest(method = method, data = data)),
            config.timeout
        )

        val reply = result.getOrThrow()
        return when (reply) {
            is Reply.Rpc -> reply.result.data
            is Reply.Error -> throw RequestError.ErrorResponse(reply.error.code, reply.error.message)
            else -> throw RequestError.UnexpectedReply(reply)
        }
    }

    suspend fun send(data: JsonElement) {
        sendCommand(Command.Send(SendRequest(data = data)), config.timeout)
    }

    suspend fun history(
        channel: String,
        limit: Int = 0,
        since: StreamPosition? = null,
        reverse: Boolean = false,
    ): HistoryResult {
        val result = sendCommand(
            Command.History(HistoryRequest(
                channel = channel,
                limit = limit,
                since = since,
                reverse = reverse,
            )),
            config.timeout
        )

        val reply = result.getOrThrow()
        return when (reply) {
            is Reply.History -> HistoryResult(
                publications = reply.result.publications.map { PublicationEvent.fromPublication(it) },
                offset = reply.result.offset,
                epoch = reply.result.epoch,
            )
            is Reply.Error -> throw RequestError.ErrorResponse(reply.error.code, reply.error.message)
            else -> throw RequestError.UnexpectedReply(reply)
        }
    }

    suspend fun presence(channel: String): PresenceResult {
        val result = sendCommand(
            Command.Presence(PresenceRequest(channel = channel)),
            config.timeout
        )

        val reply = result.getOrThrow()
        return when (reply) {
            is Reply.Presence -> PresenceResult(clients = reply.result.presence)
            is Reply.Error -> throw RequestError.ErrorResponse(reply.error.code, reply.error.message)
            else -> throw RequestError.UnexpectedReply(reply)
        }
    }

    suspend fun presenceStats(channel: String): PresenceStatsResult {
        val result = sendCommand(
            Command.PresenceStats(PresenceStatsRequest(channel = channel)),
            config.timeout
        )

        val reply = result.getOrThrow()
        return when (reply) {
            is Reply.PresenceStats -> PresenceStatsResult(
                numClients = reply.result.numClients,
                numUsers = reply.result.numUsers,
            )
            is Reply.Error -> throw RequestError.ErrorResponse(reply.error.code, reply.error.message)
            else -> throw RequestError.UnexpectedReply(reply)
        }
    }

    fun newSubscription(channel: String, subConfig: SubscriptionConfig = SubscriptionConfig()): Subscription {
        subNameToId[channel]?.let { existingId ->
            return Subscription(channel, this, existingId)
        }

        val id = nextSubId++
        val inner = SubscriptionInner(channel, subConfig, config.timeout)
        subscriptions[id] = inner
        subNameToId[channel] = id
        return Subscription(channel, this, id)
    }

    fun getSubscription(channel: String): Subscription? {
        val id = subNameToId[channel] ?: return null
        return Subscription(channel, this, id)
    }

    fun removeSubscription(subscription: Subscription) {
        val inner = subscriptions[subscription.id] ?: return
        if (inner.state != SubscriptionState.UNSUBSCRIBED) {
            throw RemoveSubscriptionException.notUnsubscribed()
        }
        subscriptions.remove(subscription.id)
        subNameToId.remove(subscription.channel)
    }

    fun subscriptions(): Map<String, Subscription> {
        return subNameToId.mapValues { (channel, id) -> Subscription(channel, this, id) }
    }

    fun onConnecting(callback: (ConnectingEvent) -> Unit) {
        onConnecting = callback
    }

    fun onConnected(callback: (ConnectedEvent) -> Unit) {
        onConnected = callback
    }

    fun onDisconnected(callback: (DisconnectedEvent) -> Unit) {
        onDisconnected = callback
    }

    fun onError(callback: (ErrorEvent) -> Unit) {
        onError = callback
    }

    fun onMessage(callback: (MessageEvent) -> Unit) {
        onMessage = callback
    }

    fun onServerSubscribed(callback: (ServerSubscribedEvent) -> Unit) {
        onServerSubscribed = callback
    }

    fun onServerSubscribing(callback: (ServerSubscribingEvent) -> Unit) {
        onServerSubscribing = callback
    }

    fun onServerUnsubscribed(callback: (ServerUnsubscribedEvent) -> Unit) {
        onServerUnsubscribed = callback
    }

    fun onServerPublication(callback: (ServerPublicationEvent) -> Unit) {
        onServerPublication = callback
    }

    fun onServerJoin(callback: (ServerJoinEvent) -> Unit) {
        onServerJoin = callback
    }

    fun onServerLeave(callback: (ServerLeaveEvent) -> Unit) {
        onServerLeave = callback
    }

    fun setToken(newToken: String) {
        token = newToken
    }

    fun setData(data: JsonElement?) {
        connectData = data
    }

    fun setHeaders(headers: Map<String, String>) {
        connectHeaders = headers
    }

    fun startBatching() {
        batching = true
    }

    fun stopBatching() {
        batching = false
        val channel = handler?.commandChannel ?: emulationHandler?.commandChannel ?: return
        for ((command, deferred) in batchedCommands) {
            val request = PendingRequest(command, deferred, config.timeout)
            channel.trySend(request)
        }
        batchedCommands.clear()
    }

    // --- Internal APIs used by Subscription ---

    internal fun getSubscriptionState(id: Int): SubscriptionState {
        return subscriptions[id]?.state ?: SubscriptionState.UNSUBSCRIBED
    }

    internal suspend fun doSubscribe(subId: Int) {
        val sub = subscriptions[subId] ?: return
        if (sub.state != SubscriptionState.UNSUBSCRIBED) return

        sub.moveToSubscribing(0, "subscribe called")

        if (_state == ClientState.CONNECTED) {
            performSubscribe(subId)
        }
    }

    internal suspend fun doUnsubscribe(subId: Int) {
        val sub = subscriptions[subId] ?: return
        if (sub.state == SubscriptionState.UNSUBSCRIBED) return

        val wasSubscribed = sub.state == SubscriptionState.SUBSCRIBED

        sub.moveToUnsubscribed(0, "unsubscribe called")

        if (wasSubscribed && _state == ClientState.CONNECTED) {
            try {
                sendCommand(
                    Command.Unsubscribe(UnsubscribeRequest(channel = sub.channel)),
                    config.timeout
                )
            } catch (_: Exception) { }
        }
    }

    internal suspend fun doSubscriptionPublish(subId: Int, data: JsonElement) {
        val sub = subscriptions[subId] ?: throw RequestError.ConnectionClosed
        publish(sub.channel, data)
    }

    internal suspend fun doSubscriptionHistory(
        subId: Int,
        limit: Int,
        since: StreamPosition?,
        reverse: Boolean,
    ): HistoryResult {
        val sub = subscriptions[subId] ?: throw RequestError.ConnectionClosed
        return history(sub.channel, limit, since, reverse)
    }

    internal suspend fun doSubscriptionPresence(subId: Int): PresenceResult {
        val sub = subscriptions[subId] ?: throw RequestError.ConnectionClosed
        return presence(sub.channel)
    }

    internal suspend fun doSubscriptionPresenceStats(subId: Int): PresenceStatsResult {
        val sub = subscriptions[subId] ?: throw RequestError.ConnectionClosed
        return presenceStats(sub.channel)
    }

    internal suspend fun waitSubscriptionReady(subId: Int) {
        val sub = subscriptions[subId] ?: return
        sub.waitReady()
    }

    internal fun setSubscriptionCallback(id: Int, setter: (SubscriptionInner) -> Unit) {
        subscriptions[id]?.let(setter)
    }

    internal fun setSubscriptionTagsFilter(id: Int, tagsFilter: FilterNode?) {
        subscriptions[id]?.tagsFilter = tagsFilter
    }

    // --- Private implementation ---

    private fun moveToConnecting(code: Int, reason: String) {
        _state = ClientState.CONNECTING
        onConnecting?.invoke(ConnectingEvent(code, reason))
    }

    private fun moveToConnected(connectResult: ConnectResult) {
        serverInfo = ServerInfo(
            clientId = connectResult.client,
            serverVersion = connectResult.version,
            tokenExpires = connectResult.expires,
            tokenTtl = connectResult.ttl,
            serverPing = connectResult.ping,
            sendPong = connectResult.pong,
        )
        _state = ClientState.CONNECTED
        if (!readyDeferred.isCompleted) {
            readyDeferred.complete(Unit)
        }
        onConnected?.invoke(ConnectedEvent(
            clientId = connectResult.client,
            version = connectResult.version,
            data = connectResult.data,
        ))

        // Handle server-side subscriptions from connect result
        if (connectResult.subs.isNotEmpty()) {
            for ((channel, subResult) in connectResult.subs) {
                onServerSubscribed?.invoke(ServerSubscribedEvent(
                    channel = channel,
                    wasRecovering = subResult.wasRecovering,
                    recovered = subResult.recovered,
                    data = subResult.data,
                    streamPosition = if (subResult.offset > 0 || subResult.epoch.isNotEmpty()) {
                        StreamPosition(offset = subResult.offset, epoch = subResult.epoch)
                    } else null,
                    positioned = subResult.positioned,
                    recoverable = subResult.recoverable,
                ))
            }
        }
    }

    private fun moveToDisconnected(code: Int, reason: String) {
        connectionJob?.cancel()
        connectionJob = null
        handler?.close()
        handler = null
        emulationHandler?.close()
        emulationHandler = null
        closerDeferred?.complete(Unit)
        closerDeferred = null
        _state = ClientState.DISCONNECTED
        onDisconnected?.invoke(DisconnectedEvent(code, reason))
    }

    private suspend fun startConnectionCycle() {
        val clientScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
        scope = clientScope

        connectionJob = clientScope.launch {
            var reconnectAttempt = 0
            val reconnectStrategy = BackoffReconnect(
                minDelay = config.minReconnectDelay,
                maxDelay = config.maxReconnectDelay,
            )

            while (isActive) {
                reconnectAttempt++

                if (reconnectAttempt > 1) {
                    val delay = reconnectStrategy.timeBeforeNextAttempt(reconnectAttempt - 1)
                    delay(delay)
                }

                if (_state != ClientState.CONNECTING) break

                val shouldReconnect = doSingleConnection()

                if (!isActive) break

                if (shouldReconnect && _state != ClientState.DISCONNECTED) {
                    if (_state == ClientState.CONNECTED) {
                        _state = ClientState.CONNECTING
                        onConnecting?.invoke(ConnectingEvent(0, "reconnecting"))
                    }
                } else {
                    if (_state != ClientState.DISCONNECTED) {
                        moveToDisconnected(0, "connection terminated")
                    }
                    break
                }
            }
        }
    }

    private fun currentTransport(): TransportConfig {
        if (config.transports.isEmpty()) {
            return TransportConfig(TransportType.WEBSOCKET, url)
        }
        return config.transports[currentTransportIndex % config.transports.size]
    }

    private fun buildConnectCommand(): Command {
        return Command.Connect(ConnectRequest(
            token = token,
            data = connectData,
            name = config.name,
            version = config.version,
            headers = connectHeaders,
        ))
    }

    private suspend fun doSingleConnection(): Boolean {
        try {
            // Refresh token if getToken callback is set
            if (config.getToken != null && token.isEmpty()) {
                try {
                    token = config.getToken.invoke()
                } catch (e: Exception) {
                    onError?.invoke(ErrorEvent(e))
                    return true
                }
            }

            val transport = currentTransport()
            return when (transport.transport) {
                TransportType.WEBSOCKET -> doWebSocketConnection(transport.endpoint)
                TransportType.HTTP_STREAM, TransportType.SSE -> doEmulationConnection(transport)
            }
        } catch (e: Exception) {
            handler = null
            emulationHandler = null
            onError?.invoke(ErrorEvent(e))
            // Try next transport if available
            if (config.transports.size > 1 && !transportWasOpen) {
                currentTransportIndex++
            }
            return true
        }
    }

    private suspend fun doWebSocketConnection(endpoint: String): Boolean {
        val wsHandler = WebSocketHandler(
            scope = scope!!,
            codec = codec,
            onPush = { reply -> handlePush(reply) },
            onError = { err -> onError?.invoke(ErrorEvent(err)) },
        )
        handler = wsHandler

        var reconnect = true

        httpClient.webSocket(endpoint, request = {
            if (config.protocol == Protocol.PROTOBUF) {
                header("Sec-WebSocket-Protocol", "centrifuge-protobuf")
            }
        }) {
            val connectCommand = buildConnectCommand()
            val connectDeferred = wsHandler.sendCommand(connectCommand, config.timeout)

            val handlerJob = launch {
                reconnect = wsHandler.handle(this@webSocket)
            }

            val connectResult = try {
                withTimeout(config.timeout) {
                    connectDeferred.await()
                }
            } catch (e: Exception) {
                handlerJob.cancel()
                return@webSocket
            }

            val reply = connectResult.getOrNull()
            when (reply) {
                is Reply.Connect -> {
                    transportWasOpen = true
                    moveToConnected(reply.result)
                    subscribeAll()
                    handlerJob.join()
                }
                is Reply.Error -> {
                    reconnect = reply.error.temporary
                    handlerJob.cancel()
                }
                else -> {
                    reconnect = false
                    handlerJob.cancel()
                }
            }
        }

        handler = null
        return reconnect
    }

    private suspend fun doEmulationConnection(transport: TransportConfig): Boolean {
        val emHandler = EmulationHandler(
            scope = scope!!,
            codec = codec,
            httpClient = httpClient,
            transportType = transport.transport,
            endpoint = transport.endpoint,
            emulationEndpoint = config.emulationEndpoint,
            protocol = config.protocol,
            onPush = { reply -> handlePush(reply) },
            onError = { err -> onError?.invoke(ErrorEvent(err)) },
        )
        emulationHandler = emHandler

        val connectCommand = buildConnectCommand()
        val reconnect = emHandler.handle(connectCommand) { connectResult ->
            transportWasOpen = true
            moveToConnected(connectResult)
            subscribeAll()
        }

        emulationHandler = null
        return reconnect
    }

    private suspend fun subscribeAll() {
        for ((subId, sub) in subscriptions) {
            if (sub.state != SubscriptionState.UNSUBSCRIBED) {
                performSubscribe(subId)
            }
        }
    }

    private suspend fun performSubscribe(subId: Int) {
        val sub = subscriptions[subId] ?: return

        val command = Command.Subscribe(SubscribeRequest(
            channel = sub.channel,
            token = sub.config.token,
            data = sub.config.data,
            positioned = sub.config.positioned,
            recoverable = sub.config.recoverable,
            joinLeave = sub.config.joinLeave,
            delta = sub.config.delta,
            tf = sub.tagsFilter,
        ))

        try {
            val result = sendCommand(command, config.timeout)
            val reply = result.getOrThrow()
            when (reply) {
                is Reply.Subscribe -> {
                    sub.moveToSubscribed(reply.result)
                }
                is Reply.Error -> {
                    sub.moveToUnsubscribed(reply.error.code, reply.error.message)
                }
                else -> {
                    sub.moveToUnsubscribed(0, "unexpected reply")
                }
            }
        } catch (e: Exception) {
            sub.moveToUnsubscribed(0, e.message ?: "subscribe failed")
        }
    }

    private suspend fun sendCommand(command: Command, timeout: Duration): Result<Reply> {
        if (batching) {
            val deferred = CompletableDeferred<Result<Reply>>()
            batchedCommands.add(Pair(command, deferred))
            return try {
                withTimeout(timeout) { deferred.await() }
            } catch (e: TimeoutCancellationException) {
                Result.failure(RequestError.Timeout)
            }
        }
        val wsHandler = handler
        val emHandler = emulationHandler
        val deferred = when {
            wsHandler != null -> wsHandler.sendCommand(command, timeout)
            emHandler != null -> emHandler.sendCommand(command, timeout)
            else -> return Result.failure(RequestError.ConnectionClosed)
        }
        return try {
            withTimeout(timeout) {
                deferred.await()
            }
        } catch (e: TimeoutCancellationException) {
            Result.failure(RequestError.Timeout)
        }
    }

    private fun handlePush(reply: Reply) {
        when (reply) {
            is Reply.Push -> {
                val push = reply.push
                val subId = subNameToId[push.channel]

                when (val data = push.data) {
                    is PushData.Publication -> {
                        if (subId != null) {
                            subscriptions[subId]?.handlePublication(data.publication)
                        } else {
                            onServerPublication?.invoke(ServerPublicationEvent(
                                channel = push.channel,
                                data = data.publication.data,
                                offset = data.publication.offset,
                                info = data.publication.info,
                                tags = data.publication.tags,
                            ))
                        }
                    }
                    is PushData.Unsubscribe -> {
                        if (subId != null) {
                            subscriptions[subId]?.moveToUnsubscribed(
                                data.unsubscribe.code,
                                data.unsubscribe.reason
                            )
                        } else {
                            onServerUnsubscribed?.invoke(ServerUnsubscribedEvent(
                                channel = push.channel,
                            ))
                        }
                    }
                    is PushData.Join -> {
                        val info = data.join.info
                        if (info != null) {
                            if (subId != null) {
                                subscriptions[subId]?.handleJoin(info)
                            } else {
                                onServerJoin?.invoke(ServerJoinEvent(
                                    channel = push.channel,
                                    info = info,
                                ))
                            }
                        }
                    }
                    is PushData.Leave -> {
                        val info = data.leave.info
                        if (info != null) {
                            if (subId != null) {
                                subscriptions[subId]?.handleLeave(info)
                            } else {
                                onServerLeave?.invoke(ServerLeaveEvent(
                                    channel = push.channel,
                                    info = info,
                                ))
                            }
                        }
                    }
                    is PushData.Message -> {
                        onMessage?.invoke(MessageEvent(data = data.message.data))
                    }
                    is PushData.Subscribe -> {
                        onServerSubscribed?.invoke(ServerSubscribedEvent(
                            channel = push.channel,
                            data = data.subscribe.data,
                            recoverable = data.subscribe.recoverable,
                            positioned = data.subscribe.positioned,
                            streamPosition = if (data.subscribe.offset > 0 || data.subscribe.epoch.isNotEmpty()) {
                                StreamPosition(offset = data.subscribe.offset, epoch = data.subscribe.epoch)
                            } else null,
                        ))
                    }
                    is PushData.Disconnect -> {
                        val disconnect = data.disconnect
                        if (disconnect.reconnect) {
                            _state = ClientState.CONNECTING
                            onConnecting?.invoke(ConnectingEvent(disconnect.code, disconnect.reason))
                        } else {
                            moveToDisconnected(disconnect.code, disconnect.reason)
                        }
                    }
                    is PushData.Connect -> { /* handled at connection level */ }
                    is PushData.Refresh -> { /* token refresh push */ }
                    is PushData.Empty -> { /* ignore */ }
                }
            }
            else -> { /* unexpected non-push reply with id=0 */ }
        }
    }

    fun close() {
        connectionJob?.cancel()
        emulationHandler?.close()
        httpClient.close()
        scope?.cancel()
    }
}
