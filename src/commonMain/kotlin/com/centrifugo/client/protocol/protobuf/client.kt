@file:OptIn(pbandk.PublicForGeneratedCode::class)

package com.centrifugo.client.protocol.protobuf

@pbandk.Export
public data class Error(
    val code: Int = 0,
    val message: String = "",
    val temporary: Boolean = false,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Error = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Error> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Error> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Error by lazy { com.centrifugo.client.protocol.protobuf.Error() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Error = com.centrifugo.client.protocol.protobuf.Error.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Error> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Error",
            messageClass = com.centrifugo.client.protocol.protobuf.Error::class,
            messageCompanion = this,
            fields = buildList(3) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "code",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "code",
                        value = com.centrifugo.client.protocol.protobuf.Error::code
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "message",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "message",
                        value = com.centrifugo.client.protocol.protobuf.Error::message
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "temporary",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "temporary",
                        value = com.centrifugo.client.protocol.protobuf.Error::temporary
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class EmulationRequest(
    val node: String = "",
    val session: String = "",
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.EmulationRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.EmulationRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.EmulationRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.EmulationRequest by lazy { com.centrifugo.client.protocol.protobuf.EmulationRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.EmulationRequest = com.centrifugo.client.protocol.protobuf.EmulationRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.EmulationRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.EmulationRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.EmulationRequest::class,
            messageCompanion = this,
            fields = buildList(3) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "node",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "node",
                        value = com.centrifugo.client.protocol.protobuf.EmulationRequest::node
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "session",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "session",
                        value = com.centrifugo.client.protocol.protobuf.EmulationRequest::session
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.EmulationRequest::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Command(
    val id: Int = 0,
    val connect: com.centrifugo.client.protocol.protobuf.ConnectRequest? = null,
    val subscribe: com.centrifugo.client.protocol.protobuf.SubscribeRequest? = null,
    val unsubscribe: com.centrifugo.client.protocol.protobuf.UnsubscribeRequest? = null,
    val publish: com.centrifugo.client.protocol.protobuf.PublishRequest? = null,
    val presence: com.centrifugo.client.protocol.protobuf.PresenceRequest? = null,
    val presenceStats: com.centrifugo.client.protocol.protobuf.PresenceStatsRequest? = null,
    val history: com.centrifugo.client.protocol.protobuf.HistoryRequest? = null,
    val ping: com.centrifugo.client.protocol.protobuf.PingRequest? = null,
    val send: com.centrifugo.client.protocol.protobuf.SendRequest? = null,
    val rpc: com.centrifugo.client.protocol.protobuf.RPCRequest? = null,
    val refresh: com.centrifugo.client.protocol.protobuf.RefreshRequest? = null,
    val subRefresh: com.centrifugo.client.protocol.protobuf.SubRefreshRequest? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Command = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Command> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Command> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Command by lazy { com.centrifugo.client.protocol.protobuf.Command() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Command = com.centrifugo.client.protocol.protobuf.Command.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Command> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Command",
            messageClass = com.centrifugo.client.protocol.protobuf.Command::class,
            messageCompanion = this,
            fields = buildList(13) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "id",
                        value = com.centrifugo.client.protocol.protobuf.Command::id
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "connect",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ConnectRequest.Companion),
                        jsonName = "connect",
                        value = com.centrifugo.client.protocol.protobuf.Command::connect
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subscribe",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeRequest.Companion),
                        jsonName = "subscribe",
                        value = com.centrifugo.client.protocol.protobuf.Command::subscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "unsubscribe",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.UnsubscribeRequest.Companion),
                        jsonName = "unsubscribe",
                        value = com.centrifugo.client.protocol.protobuf.Command::unsubscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "publish",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PublishRequest.Companion),
                        jsonName = "publish",
                        value = com.centrifugo.client.protocol.protobuf.Command::publish
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "presence",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PresenceRequest.Companion),
                        jsonName = "presence",
                        value = com.centrifugo.client.protocol.protobuf.Command::presence
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "presence_stats",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PresenceStatsRequest.Companion),
                        jsonName = "presenceStats",
                        value = com.centrifugo.client.protocol.protobuf.Command::presenceStats
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "history",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.HistoryRequest.Companion),
                        jsonName = "history",
                        value = com.centrifugo.client.protocol.protobuf.Command::history
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ping",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PingRequest.Companion),
                        jsonName = "ping",
                        value = com.centrifugo.client.protocol.protobuf.Command::ping
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "send",
                        number = 12,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SendRequest.Companion),
                        jsonName = "send",
                        value = com.centrifugo.client.protocol.protobuf.Command::send
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "rpc",
                        number = 13,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.RPCRequest.Companion),
                        jsonName = "rpc",
                        value = com.centrifugo.client.protocol.protobuf.Command::rpc
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "refresh",
                        number = 14,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.RefreshRequest.Companion),
                        jsonName = "refresh",
                        value = com.centrifugo.client.protocol.protobuf.Command::refresh
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "sub_refresh",
                        number = 15,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubRefreshRequest.Companion),
                        jsonName = "subRefresh",
                        value = com.centrifugo.client.protocol.protobuf.Command::subRefresh
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Reply(
    val id: Int = 0,
    val error: com.centrifugo.client.protocol.protobuf.Error? = null,
    val push: com.centrifugo.client.protocol.protobuf.Push? = null,
    val connect: com.centrifugo.client.protocol.protobuf.ConnectResult? = null,
    val subscribe: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null,
    val unsubscribe: com.centrifugo.client.protocol.protobuf.UnsubscribeResult? = null,
    val publish: com.centrifugo.client.protocol.protobuf.PublishResult? = null,
    val presence: com.centrifugo.client.protocol.protobuf.PresenceResult? = null,
    val presenceStats: com.centrifugo.client.protocol.protobuf.PresenceStatsResult? = null,
    val history: com.centrifugo.client.protocol.protobuf.HistoryResult? = null,
    val ping: com.centrifugo.client.protocol.protobuf.PingResult? = null,
    val rpc: com.centrifugo.client.protocol.protobuf.RPCResult? = null,
    val refresh: com.centrifugo.client.protocol.protobuf.RefreshResult? = null,
    val subRefresh: com.centrifugo.client.protocol.protobuf.SubRefreshResult? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Reply = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Reply> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Reply> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Reply by lazy { com.centrifugo.client.protocol.protobuf.Reply() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Reply = com.centrifugo.client.protocol.protobuf.Reply.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Reply> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Reply",
            messageClass = com.centrifugo.client.protocol.protobuf.Reply::class,
            messageCompanion = this,
            fields = buildList(14) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "id",
                        value = com.centrifugo.client.protocol.protobuf.Reply::id
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "error",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Error.Companion),
                        jsonName = "error",
                        value = com.centrifugo.client.protocol.protobuf.Reply::error
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "push",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Push.Companion),
                        jsonName = "push",
                        value = com.centrifugo.client.protocol.protobuf.Reply::push
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "connect",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ConnectResult.Companion),
                        jsonName = "connect",
                        value = com.centrifugo.client.protocol.protobuf.Reply::connect
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subscribe",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeResult.Companion),
                        jsonName = "subscribe",
                        value = com.centrifugo.client.protocol.protobuf.Reply::subscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "unsubscribe",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.UnsubscribeResult.Companion),
                        jsonName = "unsubscribe",
                        value = com.centrifugo.client.protocol.protobuf.Reply::unsubscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "publish",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PublishResult.Companion),
                        jsonName = "publish",
                        value = com.centrifugo.client.protocol.protobuf.Reply::publish
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "presence",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PresenceResult.Companion),
                        jsonName = "presence",
                        value = com.centrifugo.client.protocol.protobuf.Reply::presence
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "presence_stats",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PresenceStatsResult.Companion),
                        jsonName = "presenceStats",
                        value = com.centrifugo.client.protocol.protobuf.Reply::presenceStats
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "history",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.HistoryResult.Companion),
                        jsonName = "history",
                        value = com.centrifugo.client.protocol.protobuf.Reply::history
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ping",
                        number = 12,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.PingResult.Companion),
                        jsonName = "ping",
                        value = com.centrifugo.client.protocol.protobuf.Reply::ping
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "rpc",
                        number = 13,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.RPCResult.Companion),
                        jsonName = "rpc",
                        value = com.centrifugo.client.protocol.protobuf.Reply::rpc
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "refresh",
                        number = 14,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.RefreshResult.Companion),
                        jsonName = "refresh",
                        value = com.centrifugo.client.protocol.protobuf.Reply::refresh
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "sub_refresh",
                        number = 15,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubRefreshResult.Companion),
                        jsonName = "subRefresh",
                        value = com.centrifugo.client.protocol.protobuf.Reply::subRefresh
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Push(
    val id: Long = 0L,
    val channel: String = "",
    val pub: com.centrifugo.client.protocol.protobuf.Publication? = null,
    val join: com.centrifugo.client.protocol.protobuf.Join? = null,
    val leave: com.centrifugo.client.protocol.protobuf.Leave? = null,
    val unsubscribe: com.centrifugo.client.protocol.protobuf.Unsubscribe? = null,
    val message: com.centrifugo.client.protocol.protobuf.Message? = null,
    val subscribe: com.centrifugo.client.protocol.protobuf.Subscribe? = null,
    val connect: com.centrifugo.client.protocol.protobuf.Connect? = null,
    val disconnect: com.centrifugo.client.protocol.protobuf.Disconnect? = null,
    val refresh: com.centrifugo.client.protocol.protobuf.Refresh? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Push = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Push> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Push> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Push by lazy { com.centrifugo.client.protocol.protobuf.Push() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Push = com.centrifugo.client.protocol.protobuf.Push.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Push> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Push",
            messageClass = com.centrifugo.client.protocol.protobuf.Push::class,
            messageCompanion = this,
            fields = buildList(11) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "id",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "id",
                        value = com.centrifugo.client.protocol.protobuf.Push::id
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.Push::channel
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "pub",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Publication.Companion),
                        jsonName = "pub",
                        value = com.centrifugo.client.protocol.protobuf.Push::pub
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "join",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Join.Companion),
                        jsonName = "join",
                        value = com.centrifugo.client.protocol.protobuf.Push::join
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "leave",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Leave.Companion),
                        jsonName = "leave",
                        value = com.centrifugo.client.protocol.protobuf.Push::leave
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "unsubscribe",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Unsubscribe.Companion),
                        jsonName = "unsubscribe",
                        value = com.centrifugo.client.protocol.protobuf.Push::unsubscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "message",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Message.Companion),
                        jsonName = "message",
                        value = com.centrifugo.client.protocol.protobuf.Push::message
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subscribe",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Subscribe.Companion),
                        jsonName = "subscribe",
                        value = com.centrifugo.client.protocol.protobuf.Push::subscribe
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "connect",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Connect.Companion),
                        jsonName = "connect",
                        value = com.centrifugo.client.protocol.protobuf.Push::connect
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "disconnect",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Disconnect.Companion),
                        jsonName = "disconnect",
                        value = com.centrifugo.client.protocol.protobuf.Push::disconnect
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "refresh",
                        number = 12,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Refresh.Companion),
                        jsonName = "refresh",
                        value = com.centrifugo.client.protocol.protobuf.Push::refresh
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class ClientInfo(
    val user: String = "",
    val client: String = "",
    val connInfo: pbandk.ByteArr = pbandk.ByteArr.empty,
    val chanInfo: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ClientInfo = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ClientInfo> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ClientInfo> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.ClientInfo by lazy { com.centrifugo.client.protocol.protobuf.ClientInfo() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ClientInfo = com.centrifugo.client.protocol.protobuf.ClientInfo.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ClientInfo> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.ClientInfo",
            messageClass = com.centrifugo.client.protocol.protobuf.ClientInfo::class,
            messageCompanion = this,
            fields = buildList(4) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "user",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "user",
                        value = com.centrifugo.client.protocol.protobuf.ClientInfo::user
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "client",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "client",
                        value = com.centrifugo.client.protocol.protobuf.ClientInfo::client
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "conn_info",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "connInfo",
                        value = com.centrifugo.client.protocol.protobuf.ClientInfo::connInfo
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "chan_info",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "chanInfo",
                        value = com.centrifugo.client.protocol.protobuf.ClientInfo::chanInfo
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Publication(
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null,
    val offset: Long = 0L,
    val tags: Map<String, String> = emptyMap(),
    val delta: Boolean = false,
    val time: Long = 0L,
    val channel: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Publication = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Publication> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Publication> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Publication by lazy { com.centrifugo.client.protocol.protobuf.Publication() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Publication = com.centrifugo.client.protocol.protobuf.Publication.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Publication> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Publication",
            messageClass = com.centrifugo.client.protocol.protobuf.Publication::class,
            messageCompanion = this,
            fields = buildList(7) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.Publication::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "info",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ClientInfo.Companion),
                        jsonName = "info",
                        value = com.centrifugo.client.protocol.protobuf.Publication::info
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.Publication::offset
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "tags",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Map<String, String>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Primitive.String()),
                        jsonName = "tags",
                        value = com.centrifugo.client.protocol.protobuf.Publication::tags
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "delta",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "delta",
                        value = com.centrifugo.client.protocol.protobuf.Publication::delta
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "time",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "time",
                        value = com.centrifugo.client.protocol.protobuf.Publication::time
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.Publication::channel
                    )
                )
            }
        )
    }

    public data class TagsEntry(
        override val key: String = "",
        override val value: String = "",
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, String> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Publication.TagsEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Publication.TagsEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Publication.TagsEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.Publication.TagsEntry by lazy { com.centrifugo.client.protocol.protobuf.Publication.TagsEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Publication.TagsEntry = com.centrifugo.client.protocol.protobuf.Publication.TagsEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Publication.TagsEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.Publication.TagsEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.Publication.TagsEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.Publication.TagsEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.Publication.TagsEntry::value
                        )
                    )
                }
            )
        }
    }
}

@pbandk.Export
public data class Join(
    val info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Join = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Join> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Join> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Join by lazy { com.centrifugo.client.protocol.protobuf.Join() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Join = com.centrifugo.client.protocol.protobuf.Join.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Join> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Join",
            messageClass = com.centrifugo.client.protocol.protobuf.Join::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "info",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ClientInfo.Companion),
                        jsonName = "info",
                        value = com.centrifugo.client.protocol.protobuf.Join::info
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Leave(
    val info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Leave = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Leave> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Leave> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Leave by lazy { com.centrifugo.client.protocol.protobuf.Leave() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Leave = com.centrifugo.client.protocol.protobuf.Leave.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Leave> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Leave",
            messageClass = com.centrifugo.client.protocol.protobuf.Leave::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "info",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ClientInfo.Companion),
                        jsonName = "info",
                        value = com.centrifugo.client.protocol.protobuf.Leave::info
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Unsubscribe(
    val code: Int = 0,
    val reason: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Unsubscribe = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Unsubscribe> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Unsubscribe> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Unsubscribe by lazy { com.centrifugo.client.protocol.protobuf.Unsubscribe() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Unsubscribe = com.centrifugo.client.protocol.protobuf.Unsubscribe.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Unsubscribe> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Unsubscribe",
            messageClass = com.centrifugo.client.protocol.protobuf.Unsubscribe::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "code",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "code",
                        value = com.centrifugo.client.protocol.protobuf.Unsubscribe::code
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "reason",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "reason",
                        value = com.centrifugo.client.protocol.protobuf.Unsubscribe::reason
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Subscribe(
    val recoverable: Boolean = false,
    val epoch: String = "",
    val offset: Long = 0L,
    val positioned: Boolean = false,
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Subscribe = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Subscribe> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Subscribe> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Subscribe by lazy { com.centrifugo.client.protocol.protobuf.Subscribe() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Subscribe = com.centrifugo.client.protocol.protobuf.Subscribe.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Subscribe> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Subscribe",
            messageClass = com.centrifugo.client.protocol.protobuf.Subscribe::class,
            messageCompanion = this,
            fields = buildList(5) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "recoverable",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "recoverable",
                        value = com.centrifugo.client.protocol.protobuf.Subscribe::recoverable
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "epoch",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "epoch",
                        value = com.centrifugo.client.protocol.protobuf.Subscribe::epoch
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.Subscribe::offset
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "positioned",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "positioned",
                        value = com.centrifugo.client.protocol.protobuf.Subscribe::positioned
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.Subscribe::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Message(
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Message = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Message> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Message> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Message by lazy { com.centrifugo.client.protocol.protobuf.Message() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Message = com.centrifugo.client.protocol.protobuf.Message.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Message> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Message",
            messageClass = com.centrifugo.client.protocol.protobuf.Message::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.Message::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Connect(
    val client: String = "",
    val version: String = "",
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val subs: Map<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?> = emptyMap(),
    val expires: Boolean = false,
    val ttl: Int = 0,
    val ping: Int = 0,
    val pong: Boolean = false,
    val session: String = "",
    val node: String = "",
    val time: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Connect = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Connect> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Connect> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Connect by lazy { com.centrifugo.client.protocol.protobuf.Connect() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Connect = com.centrifugo.client.protocol.protobuf.Connect.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Connect> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Connect",
            messageClass = com.centrifugo.client.protocol.protobuf.Connect::class,
            messageCompanion = this,
            fields = buildList(11) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "client",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "client",
                        value = com.centrifugo.client.protocol.protobuf.Connect::client
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "version",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "version",
                        value = com.centrifugo.client.protocol.protobuf.Connect::version
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.Connect::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subs",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Map<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeResult.Companion)),
                        jsonName = "subs",
                        value = com.centrifugo.client.protocol.protobuf.Connect::subs
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.Connect::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.Connect::ttl
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ping",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ping",
                        value = com.centrifugo.client.protocol.protobuf.Connect::ping
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "pong",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "pong",
                        value = com.centrifugo.client.protocol.protobuf.Connect::pong
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "session",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "session",
                        value = com.centrifugo.client.protocol.protobuf.Connect::session
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "node",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "node",
                        value = com.centrifugo.client.protocol.protobuf.Connect::node
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "time",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "time",
                        value = com.centrifugo.client.protocol.protobuf.Connect::time
                    )
                )
            }
        )
    }

    public data class SubsEntry(
        override val key: String = "",
        override val value: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null,
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Connect.SubsEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Connect.SubsEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Connect.SubsEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.Connect.SubsEntry by lazy { com.centrifugo.client.protocol.protobuf.Connect.SubsEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Connect.SubsEntry = com.centrifugo.client.protocol.protobuf.Connect.SubsEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Connect.SubsEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.Connect.SubsEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.Connect.SubsEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.Connect.SubsEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeResult.Companion),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.Connect.SubsEntry::value
                        )
                    )
                }
            )
        }
    }
}

@pbandk.Export
public data class Disconnect(
    val code: Int = 0,
    val reason: String = "",
    val reconnect: Boolean = false,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Disconnect = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Disconnect> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Disconnect> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Disconnect by lazy { com.centrifugo.client.protocol.protobuf.Disconnect() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Disconnect = com.centrifugo.client.protocol.protobuf.Disconnect.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Disconnect> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Disconnect",
            messageClass = com.centrifugo.client.protocol.protobuf.Disconnect::class,
            messageCompanion = this,
            fields = buildList(3) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "code",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "code",
                        value = com.centrifugo.client.protocol.protobuf.Disconnect::code
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "reason",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "reason",
                        value = com.centrifugo.client.protocol.protobuf.Disconnect::reason
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "reconnect",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "reconnect",
                        value = com.centrifugo.client.protocol.protobuf.Disconnect::reconnect
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class Refresh(
    val expires: Boolean = false,
    val ttl: Int = 0,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.Refresh = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Refresh> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.Refresh> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.Refresh by lazy { com.centrifugo.client.protocol.protobuf.Refresh() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.Refresh = com.centrifugo.client.protocol.protobuf.Refresh.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.Refresh> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.Refresh",
            messageClass = com.centrifugo.client.protocol.protobuf.Refresh::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.Refresh::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.Refresh::ttl
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class ConnectRequest(
    val token: String = "",
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val subs: Map<String, com.centrifugo.client.protocol.protobuf.SubscribeRequest?> = emptyMap(),
    val name: String = "",
    val version: String = "",
    val headers: Map<String, String> = emptyMap(),
    val flag: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ConnectRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ConnectRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.ConnectRequest by lazy { com.centrifugo.client.protocol.protobuf.ConnectRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ConnectRequest = com.centrifugo.client.protocol.protobuf.ConnectRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.ConnectRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.ConnectRequest::class,
            messageCompanion = this,
            fields = buildList(7) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "token",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "token",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::token
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subs",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Map<String, com.centrifugo.client.protocol.protobuf.SubscribeRequest?>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeRequest.Companion)),
                        jsonName = "subs",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::subs
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "name",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "name",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::name
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "version",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "version",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::version
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "headers",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Map<String, String>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Primitive.String()),
                        jsonName = "headers",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::headers
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "flag",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "flag",
                        value = com.centrifugo.client.protocol.protobuf.ConnectRequest::flag
                    )
                )
            }
        )
    }

    public data class SubsEntry(
        override val key: String = "",
        override val value: com.centrifugo.client.protocol.protobuf.SubscribeRequest? = null,
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeRequest?> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry by lazy { com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry = com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.ConnectRequest.SubsEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeRequest.Companion),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry::value
                        )
                    )
                }
            )
        }
    }

    public data class HeadersEntry(
        override val key: String = "",
        override val value: String = "",
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, String> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry by lazy { com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry = com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.ConnectRequest.HeadersEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry::value
                        )
                    )
                }
            )
        }
    }
}

@pbandk.Export
public data class ConnectResult(
    val client: String = "",
    val version: String = "",
    val expires: Boolean = false,
    val ttl: Int = 0,
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val subs: Map<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?> = emptyMap(),
    val ping: Int = 0,
    val pong: Boolean = false,
    val session: String = "",
    val node: String = "",
    val time: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ConnectResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ConnectResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.ConnectResult by lazy { com.centrifugo.client.protocol.protobuf.ConnectResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ConnectResult = com.centrifugo.client.protocol.protobuf.ConnectResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.ConnectResult",
            messageClass = com.centrifugo.client.protocol.protobuf.ConnectResult::class,
            messageCompanion = this,
            fields = buildList(11) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "client",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "client",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::client
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "version",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "version",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::version
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::ttl
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "subs",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Map<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeResult.Companion)),
                        jsonName = "subs",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::subs
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ping",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ping",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::ping
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "pong",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "pong",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::pong
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "session",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "session",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::session
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "node",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "node",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::node
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "time",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "time",
                        value = com.centrifugo.client.protocol.protobuf.ConnectResult::time
                    )
                )
            }
        )
    }

    public data class SubsEntry(
        override val key: String = "",
        override val value: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null,
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry by lazy { com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry = com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.ConnectResult.SubsEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.SubscribeResult.Companion),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry::value
                        )
                    )
                }
            )
        }
    }
}

@pbandk.Export
public data class RefreshRequest(
    val token: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.RefreshRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RefreshRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.RefreshRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.RefreshRequest by lazy { com.centrifugo.client.protocol.protobuf.RefreshRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.RefreshRequest = com.centrifugo.client.protocol.protobuf.RefreshRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RefreshRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.RefreshRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.RefreshRequest::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "token",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "token",
                        value = com.centrifugo.client.protocol.protobuf.RefreshRequest::token
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class RefreshResult(
    val client: String = "",
    val version: String = "",
    val expires: Boolean = false,
    val ttl: Int = 0,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.RefreshResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RefreshResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.RefreshResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.RefreshResult by lazy { com.centrifugo.client.protocol.protobuf.RefreshResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.RefreshResult = com.centrifugo.client.protocol.protobuf.RefreshResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RefreshResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.RefreshResult",
            messageClass = com.centrifugo.client.protocol.protobuf.RefreshResult::class,
            messageCompanion = this,
            fields = buildList(4) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "client",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "client",
                        value = com.centrifugo.client.protocol.protobuf.RefreshResult::client
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "version",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "version",
                        value = com.centrifugo.client.protocol.protobuf.RefreshResult::version
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.RefreshResult::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.RefreshResult::ttl
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class SubscribeRequest(
    val channel: String = "",
    val token: String = "",
    val recover: Boolean = false,
    val epoch: String = "",
    val offset: Long = 0L,
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
    val joinLeave: Boolean = false,
    val delta: String = "",
    val tf: com.centrifugo.client.protocol.protobuf.FilterNode? = null,
    val flag: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.SubscribeRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubscribeRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.SubscribeRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.SubscribeRequest by lazy { com.centrifugo.client.protocol.protobuf.SubscribeRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.SubscribeRequest = com.centrifugo.client.protocol.protobuf.SubscribeRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubscribeRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.SubscribeRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.SubscribeRequest::class,
            messageCompanion = this,
            fields = buildList(12) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::channel
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "token",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "token",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::token
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "recover",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "recover",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::recover
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "epoch",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "epoch",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::epoch
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::offset
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "positioned",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "positioned",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::positioned
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "recoverable",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "recoverable",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::recoverable
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "join_leave",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "joinLeave",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::joinLeave
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "delta",
                        number = 12,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "delta",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::delta
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "tf",
                        number = 13,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.FilterNode.Companion),
                        jsonName = "tf",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::tf
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "flag",
                        number = 14,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "flag",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeRequest::flag
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class SubscribeResult(
    val expires: Boolean = false,
    val ttl: Int = 0,
    val recoverable: Boolean = false,
    val epoch: String = "",
    val publications: List<com.centrifugo.client.protocol.protobuf.Publication> = emptyList(),
    val recovered: Boolean = false,
    val offset: Long = 0L,
    val positioned: Boolean = false,
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val wasRecovering: Boolean = false,
    val delta: Boolean = false,
    val id: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.SubscribeResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubscribeResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.SubscribeResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.SubscribeResult by lazy { com.centrifugo.client.protocol.protobuf.SubscribeResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.SubscribeResult = com.centrifugo.client.protocol.protobuf.SubscribeResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubscribeResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.SubscribeResult",
            messageClass = com.centrifugo.client.protocol.protobuf.SubscribeResult::class,
            messageCompanion = this,
            fields = buildList(12) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::ttl
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "recoverable",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "recoverable",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::recoverable
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "epoch",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "epoch",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::epoch
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "publications",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Repeated<com.centrifugo.client.protocol.protobuf.Publication>(valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Publication.Companion)),
                        jsonName = "publications",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::publications
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "recovered",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "recovered",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::recovered
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::offset
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "positioned",
                        number = 10,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "positioned",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::positioned
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 11,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "was_recovering",
                        number = 12,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "wasRecovering",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::wasRecovering
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "delta",
                        number = 13,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "delta",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::delta
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "id",
                        number = 14,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int64(),
                        jsonName = "id",
                        value = com.centrifugo.client.protocol.protobuf.SubscribeResult::id
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class SubRefreshRequest(
    val channel: String = "",
    val token: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.SubRefreshRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubRefreshRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.SubRefreshRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.SubRefreshRequest by lazy { com.centrifugo.client.protocol.protobuf.SubRefreshRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.SubRefreshRequest = com.centrifugo.client.protocol.protobuf.SubRefreshRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubRefreshRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.SubRefreshRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.SubRefreshRequest::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.SubRefreshRequest::channel
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "token",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "token",
                        value = com.centrifugo.client.protocol.protobuf.SubRefreshRequest::token
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class SubRefreshResult(
    val expires: Boolean = false,
    val ttl: Int = 0,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.SubRefreshResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubRefreshResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.SubRefreshResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.SubRefreshResult by lazy { com.centrifugo.client.protocol.protobuf.SubRefreshResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.SubRefreshResult = com.centrifugo.client.protocol.protobuf.SubRefreshResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SubRefreshResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.SubRefreshResult",
            messageClass = com.centrifugo.client.protocol.protobuf.SubRefreshResult::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "expires",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "expires",
                        value = com.centrifugo.client.protocol.protobuf.SubRefreshResult::expires
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "ttl",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "ttl",
                        value = com.centrifugo.client.protocol.protobuf.SubRefreshResult::ttl
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class UnsubscribeRequest(
    val channel: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.UnsubscribeRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.UnsubscribeRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.UnsubscribeRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.UnsubscribeRequest by lazy { com.centrifugo.client.protocol.protobuf.UnsubscribeRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.UnsubscribeRequest = com.centrifugo.client.protocol.protobuf.UnsubscribeRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.UnsubscribeRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.UnsubscribeRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.UnsubscribeRequest::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.UnsubscribeRequest::channel
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class UnsubscribeResult(
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.UnsubscribeResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.UnsubscribeResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.UnsubscribeResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.UnsubscribeResult by lazy { com.centrifugo.client.protocol.protobuf.UnsubscribeResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.UnsubscribeResult = com.centrifugo.client.protocol.protobuf.UnsubscribeResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.UnsubscribeResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.UnsubscribeResult",
            messageClass = com.centrifugo.client.protocol.protobuf.UnsubscribeResult::class,
            messageCompanion = this,
            fields = buildList(0) {
            }
        )
    }
}

@pbandk.Export
public data class PublishRequest(
    val channel: String = "",
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PublishRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PublishRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PublishRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PublishRequest by lazy { com.centrifugo.client.protocol.protobuf.PublishRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PublishRequest = com.centrifugo.client.protocol.protobuf.PublishRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PublishRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PublishRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.PublishRequest::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.PublishRequest::channel
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.PublishRequest::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class PublishResult(
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PublishResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PublishResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PublishResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PublishResult by lazy { com.centrifugo.client.protocol.protobuf.PublishResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PublishResult = com.centrifugo.client.protocol.protobuf.PublishResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PublishResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PublishResult",
            messageClass = com.centrifugo.client.protocol.protobuf.PublishResult::class,
            messageCompanion = this,
            fields = buildList(0) {
            }
        )
    }
}

@pbandk.Export
public data class PresenceRequest(
    val channel: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PresenceRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PresenceRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PresenceRequest by lazy { com.centrifugo.client.protocol.protobuf.PresenceRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PresenceRequest = com.centrifugo.client.protocol.protobuf.PresenceRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PresenceRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.PresenceRequest::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.PresenceRequest::channel
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class PresenceResult(
    val presence: Map<String, com.centrifugo.client.protocol.protobuf.ClientInfo?> = emptyMap(),
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PresenceResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PresenceResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PresenceResult by lazy { com.centrifugo.client.protocol.protobuf.PresenceResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PresenceResult = com.centrifugo.client.protocol.protobuf.PresenceResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PresenceResult",
            messageClass = com.centrifugo.client.protocol.protobuf.PresenceResult::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "presence",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Map<String, com.centrifugo.client.protocol.protobuf.ClientInfo?>(keyType = pbandk.FieldDescriptor.Type.Primitive.String(), valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ClientInfo.Companion)),
                        jsonName = "presence",
                        value = com.centrifugo.client.protocol.protobuf.PresenceResult::presence
                    )
                )
            }
        )
    }

    public data class PresenceEntry(
        override val key: String = "",
        override val value: com.centrifugo.client.protocol.protobuf.ClientInfo? = null,
        override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
    ) : pbandk.Message, Map.Entry<String, com.centrifugo.client.protocol.protobuf.ClientInfo?> {
        override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry = protoMergeImpl(other)
        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry> get() = Companion.descriptor
        override val protoSize: Int by lazy { super.protoSize }
        public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry> {
            public val defaultInstance: com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry by lazy { com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry() }
            override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry = com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry.decodeWithImpl(u)

            override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry> = pbandk.MessageDescriptor(
                fullName = "centrifugal.centrifuge.protocol.PresenceResult.PresenceEntry",
                messageClass = com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry::class,
                messageCompanion = this,
                fields = buildList(2) {
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "key",
                            number = 1,
                            type = pbandk.FieldDescriptor.Type.Primitive.String(),
                            jsonName = "key",
                            value = com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry::key
                        )
                    )
                    add(
                        pbandk.FieldDescriptor(
                            messageDescriptor = this@Companion::descriptor,
                            name = "value",
                            number = 2,
                            type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.ClientInfo.Companion),
                            jsonName = "value",
                            value = com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry::value
                        )
                    )
                }
            )
        }
    }
}

@pbandk.Export
public data class PresenceStatsRequest(
    val channel: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PresenceStatsRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceStatsRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PresenceStatsRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PresenceStatsRequest by lazy { com.centrifugo.client.protocol.protobuf.PresenceStatsRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PresenceStatsRequest = com.centrifugo.client.protocol.protobuf.PresenceStatsRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceStatsRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PresenceStatsRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.PresenceStatsRequest::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.PresenceStatsRequest::channel
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class PresenceStatsResult(
    val numClients: Int = 0,
    val numUsers: Int = 0,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PresenceStatsResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceStatsResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PresenceStatsResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PresenceStatsResult by lazy { com.centrifugo.client.protocol.protobuf.PresenceStatsResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PresenceStatsResult = com.centrifugo.client.protocol.protobuf.PresenceStatsResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PresenceStatsResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PresenceStatsResult",
            messageClass = com.centrifugo.client.protocol.protobuf.PresenceStatsResult::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "num_clients",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "numClients",
                        value = com.centrifugo.client.protocol.protobuf.PresenceStatsResult::numClients
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "num_users",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt32(),
                        jsonName = "numUsers",
                        value = com.centrifugo.client.protocol.protobuf.PresenceStatsResult::numUsers
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class StreamPosition(
    val offset: Long = 0L,
    val epoch: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.StreamPosition = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.StreamPosition> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.StreamPosition> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.StreamPosition by lazy { com.centrifugo.client.protocol.protobuf.StreamPosition() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.StreamPosition = com.centrifugo.client.protocol.protobuf.StreamPosition.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.StreamPosition> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.StreamPosition",
            messageClass = com.centrifugo.client.protocol.protobuf.StreamPosition::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.StreamPosition::offset
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "epoch",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "epoch",
                        value = com.centrifugo.client.protocol.protobuf.StreamPosition::epoch
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class HistoryRequest(
    val channel: String = "",
    val limit: Int = 0,
    val since: com.centrifugo.client.protocol.protobuf.StreamPosition? = null,
    val reverse: Boolean = false,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.HistoryRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.HistoryRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.HistoryRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.HistoryRequest by lazy { com.centrifugo.client.protocol.protobuf.HistoryRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.HistoryRequest = com.centrifugo.client.protocol.protobuf.HistoryRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.HistoryRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.HistoryRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.HistoryRequest::class,
            messageCompanion = this,
            fields = buildList(4) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "channel",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "channel",
                        value = com.centrifugo.client.protocol.protobuf.HistoryRequest::channel
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "limit",
                        number = 7,
                        type = pbandk.FieldDescriptor.Type.Primitive.Int32(),
                        jsonName = "limit",
                        value = com.centrifugo.client.protocol.protobuf.HistoryRequest::limit
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "since",
                        number = 8,
                        type = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.StreamPosition.Companion),
                        jsonName = "since",
                        value = com.centrifugo.client.protocol.protobuf.HistoryRequest::since
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "reverse",
                        number = 9,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bool(),
                        jsonName = "reverse",
                        value = com.centrifugo.client.protocol.protobuf.HistoryRequest::reverse
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class HistoryResult(
    val publications: List<com.centrifugo.client.protocol.protobuf.Publication> = emptyList(),
    val epoch: String = "",
    val offset: Long = 0L,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.HistoryResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.HistoryResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.HistoryResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.HistoryResult by lazy { com.centrifugo.client.protocol.protobuf.HistoryResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.HistoryResult = com.centrifugo.client.protocol.protobuf.HistoryResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.HistoryResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.HistoryResult",
            messageClass = com.centrifugo.client.protocol.protobuf.HistoryResult::class,
            messageCompanion = this,
            fields = buildList(3) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "publications",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Repeated<com.centrifugo.client.protocol.protobuf.Publication>(valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.Publication.Companion)),
                        jsonName = "publications",
                        value = com.centrifugo.client.protocol.protobuf.HistoryResult::publications
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "epoch",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "epoch",
                        value = com.centrifugo.client.protocol.protobuf.HistoryResult::epoch
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "offset",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.UInt64(),
                        jsonName = "offset",
                        value = com.centrifugo.client.protocol.protobuf.HistoryResult::offset
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class PingRequest(
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PingRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PingRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PingRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PingRequest by lazy { com.centrifugo.client.protocol.protobuf.PingRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PingRequest = com.centrifugo.client.protocol.protobuf.PingRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PingRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PingRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.PingRequest::class,
            messageCompanion = this,
            fields = buildList(0) {
            }
        )
    }
}

@pbandk.Export
public data class PingResult(
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.PingResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PingResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.PingResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.PingResult by lazy { com.centrifugo.client.protocol.protobuf.PingResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.PingResult = com.centrifugo.client.protocol.protobuf.PingResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.PingResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.PingResult",
            messageClass = com.centrifugo.client.protocol.protobuf.PingResult::class,
            messageCompanion = this,
            fields = buildList(0) {
            }
        )
    }
}

@pbandk.Export
public data class RPCRequest(
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    val method: String = "",
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.RPCRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RPCRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.RPCRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.RPCRequest by lazy { com.centrifugo.client.protocol.protobuf.RPCRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.RPCRequest = com.centrifugo.client.protocol.protobuf.RPCRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RPCRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.RPCRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.RPCRequest::class,
            messageCompanion = this,
            fields = buildList(2) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.RPCRequest::data
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "method",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "method",
                        value = com.centrifugo.client.protocol.protobuf.RPCRequest::method
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class RPCResult(
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.RPCResult = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RPCResult> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.RPCResult> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.RPCResult by lazy { com.centrifugo.client.protocol.protobuf.RPCResult() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.RPCResult = com.centrifugo.client.protocol.protobuf.RPCResult.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.RPCResult> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.RPCResult",
            messageClass = com.centrifugo.client.protocol.protobuf.RPCResult::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.RPCResult::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class SendRequest(
    val data: pbandk.ByteArr = pbandk.ByteArr.empty,
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.SendRequest = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SendRequest> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.SendRequest> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.SendRequest by lazy { com.centrifugo.client.protocol.protobuf.SendRequest() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.SendRequest = com.centrifugo.client.protocol.protobuf.SendRequest.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.SendRequest> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.SendRequest",
            messageClass = com.centrifugo.client.protocol.protobuf.SendRequest::class,
            messageCompanion = this,
            fields = buildList(1) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "data",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.Bytes(),
                        jsonName = "data",
                        value = com.centrifugo.client.protocol.protobuf.SendRequest::data
                    )
                )
            }
        )
    }
}

@pbandk.Export
public data class FilterNode(
    val op: String = "",
    val key: String = "",
    val cmp: String = "",
    val `val`: String = "",
    val vals: List<String> = emptyList(),
    val nodes: List<com.centrifugo.client.protocol.protobuf.FilterNode> = emptyList(),
    override val unknownFields: Map<Int, pbandk.UnknownField> = emptyMap()
) : pbandk.Message {
    override operator fun plus(other: pbandk.Message?): com.centrifugo.client.protocol.protobuf.FilterNode = protoMergeImpl(other)
    override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.FilterNode> get() = Companion.descriptor
    override val protoSize: Int by lazy { super.protoSize }
    public companion object : pbandk.Message.Companion<com.centrifugo.client.protocol.protobuf.FilterNode> {
        public val defaultInstance: com.centrifugo.client.protocol.protobuf.FilterNode by lazy { com.centrifugo.client.protocol.protobuf.FilterNode() }
        override fun decodeWith(u: pbandk.MessageDecoder): com.centrifugo.client.protocol.protobuf.FilterNode = com.centrifugo.client.protocol.protobuf.FilterNode.decodeWithImpl(u)

        override val descriptor: pbandk.MessageDescriptor<com.centrifugo.client.protocol.protobuf.FilterNode> = pbandk.MessageDescriptor(
            fullName = "centrifugal.centrifuge.protocol.FilterNode",
            messageClass = com.centrifugo.client.protocol.protobuf.FilterNode::class,
            messageCompanion = this,
            fields = buildList(6) {
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "op",
                        number = 1,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "op",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::op
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "key",
                        number = 2,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "key",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::key
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "cmp",
                        number = 3,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "cmp",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::cmp
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "val",
                        number = 4,
                        type = pbandk.FieldDescriptor.Type.Primitive.String(),
                        jsonName = "val",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::`val`
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "vals",
                        number = 5,
                        type = pbandk.FieldDescriptor.Type.Repeated<String>(valueType = pbandk.FieldDescriptor.Type.Primitive.String()),
                        jsonName = "vals",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::vals
                    )
                )
                add(
                    pbandk.FieldDescriptor(
                        messageDescriptor = this@Companion::descriptor,
                        name = "nodes",
                        number = 6,
                        type = pbandk.FieldDescriptor.Type.Repeated<com.centrifugo.client.protocol.protobuf.FilterNode>(valueType = pbandk.FieldDescriptor.Type.Message(messageCompanion = com.centrifugo.client.protocol.protobuf.FilterNode.Companion)),
                        jsonName = "nodes",
                        value = com.centrifugo.client.protocol.protobuf.FilterNode::nodes
                    )
                )
            }
        )
    }
}

@pbandk.Export
@pbandk.JsName("orDefaultForError")
public fun Error?.orDefault(): com.centrifugo.client.protocol.protobuf.Error = this ?: Error.defaultInstance

private fun Error.protoMergeImpl(plus: pbandk.Message?): Error = (plus as? Error)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Error.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Error {
    var code = 0
    var message = ""
    var temporary = false

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> code = _fieldValue as Int
            2 -> message = _fieldValue as String
            3 -> temporary = _fieldValue as Boolean
        }
    }

    return Error(code, message, temporary, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForEmulationRequest")
public fun EmulationRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.EmulationRequest = this ?: EmulationRequest.defaultInstance

private fun EmulationRequest.protoMergeImpl(plus: pbandk.Message?): EmulationRequest = (plus as? EmulationRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun EmulationRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): EmulationRequest {
    var node = ""
    var session = ""
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> node = _fieldValue as String
            2 -> session = _fieldValue as String
            3 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return EmulationRequest(node, session, data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForCommand")
public fun Command?.orDefault(): com.centrifugo.client.protocol.protobuf.Command = this ?: Command.defaultInstance

private fun Command.protoMergeImpl(plus: pbandk.Message?): Command = (plus as? Command)?.let {
    it.copy(
        connect = connect?.plus(plus.connect) ?: plus.connect,
        subscribe = subscribe?.plus(plus.subscribe) ?: plus.subscribe,
        unsubscribe = unsubscribe?.plus(plus.unsubscribe) ?: plus.unsubscribe,
        publish = publish?.plus(plus.publish) ?: plus.publish,
        presence = presence?.plus(plus.presence) ?: plus.presence,
        presenceStats = presenceStats?.plus(plus.presenceStats) ?: plus.presenceStats,
        history = history?.plus(plus.history) ?: plus.history,
        ping = ping?.plus(plus.ping) ?: plus.ping,
        send = send?.plus(plus.send) ?: plus.send,
        rpc = rpc?.plus(plus.rpc) ?: plus.rpc,
        refresh = refresh?.plus(plus.refresh) ?: plus.refresh,
        subRefresh = subRefresh?.plus(plus.subRefresh) ?: plus.subRefresh,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Command.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Command {
    var id = 0
    var connect: com.centrifugo.client.protocol.protobuf.ConnectRequest? = null
    var subscribe: com.centrifugo.client.protocol.protobuf.SubscribeRequest? = null
    var unsubscribe: com.centrifugo.client.protocol.protobuf.UnsubscribeRequest? = null
    var publish: com.centrifugo.client.protocol.protobuf.PublishRequest? = null
    var presence: com.centrifugo.client.protocol.protobuf.PresenceRequest? = null
    var presenceStats: com.centrifugo.client.protocol.protobuf.PresenceStatsRequest? = null
    var history: com.centrifugo.client.protocol.protobuf.HistoryRequest? = null
    var ping: com.centrifugo.client.protocol.protobuf.PingRequest? = null
    var send: com.centrifugo.client.protocol.protobuf.SendRequest? = null
    var rpc: com.centrifugo.client.protocol.protobuf.RPCRequest? = null
    var refresh: com.centrifugo.client.protocol.protobuf.RefreshRequest? = null
    var subRefresh: com.centrifugo.client.protocol.protobuf.SubRefreshRequest? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> id = _fieldValue as Int
            4 -> connect = _fieldValue as com.centrifugo.client.protocol.protobuf.ConnectRequest
            5 -> subscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.SubscribeRequest
            6 -> unsubscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.UnsubscribeRequest
            7 -> publish = _fieldValue as com.centrifugo.client.protocol.protobuf.PublishRequest
            8 -> presence = _fieldValue as com.centrifugo.client.protocol.protobuf.PresenceRequest
            9 -> presenceStats = _fieldValue as com.centrifugo.client.protocol.protobuf.PresenceStatsRequest
            10 -> history = _fieldValue as com.centrifugo.client.protocol.protobuf.HistoryRequest
            11 -> ping = _fieldValue as com.centrifugo.client.protocol.protobuf.PingRequest
            12 -> send = _fieldValue as com.centrifugo.client.protocol.protobuf.SendRequest
            13 -> rpc = _fieldValue as com.centrifugo.client.protocol.protobuf.RPCRequest
            14 -> refresh = _fieldValue as com.centrifugo.client.protocol.protobuf.RefreshRequest
            15 -> subRefresh = _fieldValue as com.centrifugo.client.protocol.protobuf.SubRefreshRequest
        }
    }

    return Command(id, connect, subscribe, unsubscribe,
        publish, presence, presenceStats, history,
        ping, send, rpc, refresh,
        subRefresh, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForReply")
public fun Reply?.orDefault(): com.centrifugo.client.protocol.protobuf.Reply = this ?: Reply.defaultInstance

private fun Reply.protoMergeImpl(plus: pbandk.Message?): Reply = (plus as? Reply)?.let {
    it.copy(
        error = error?.plus(plus.error) ?: plus.error,
        push = push?.plus(plus.push) ?: plus.push,
        connect = connect?.plus(plus.connect) ?: plus.connect,
        subscribe = subscribe?.plus(plus.subscribe) ?: plus.subscribe,
        unsubscribe = unsubscribe?.plus(plus.unsubscribe) ?: plus.unsubscribe,
        publish = publish?.plus(plus.publish) ?: plus.publish,
        presence = presence?.plus(plus.presence) ?: plus.presence,
        presenceStats = presenceStats?.plus(plus.presenceStats) ?: plus.presenceStats,
        history = history?.plus(plus.history) ?: plus.history,
        ping = ping?.plus(plus.ping) ?: plus.ping,
        rpc = rpc?.plus(plus.rpc) ?: plus.rpc,
        refresh = refresh?.plus(plus.refresh) ?: plus.refresh,
        subRefresh = subRefresh?.plus(plus.subRefresh) ?: plus.subRefresh,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Reply.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Reply {
    var id = 0
    var error: com.centrifugo.client.protocol.protobuf.Error? = null
    var push: com.centrifugo.client.protocol.protobuf.Push? = null
    var connect: com.centrifugo.client.protocol.protobuf.ConnectResult? = null
    var subscribe: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null
    var unsubscribe: com.centrifugo.client.protocol.protobuf.UnsubscribeResult? = null
    var publish: com.centrifugo.client.protocol.protobuf.PublishResult? = null
    var presence: com.centrifugo.client.protocol.protobuf.PresenceResult? = null
    var presenceStats: com.centrifugo.client.protocol.protobuf.PresenceStatsResult? = null
    var history: com.centrifugo.client.protocol.protobuf.HistoryResult? = null
    var ping: com.centrifugo.client.protocol.protobuf.PingResult? = null
    var rpc: com.centrifugo.client.protocol.protobuf.RPCResult? = null
    var refresh: com.centrifugo.client.protocol.protobuf.RefreshResult? = null
    var subRefresh: com.centrifugo.client.protocol.protobuf.SubRefreshResult? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> id = _fieldValue as Int
            2 -> error = _fieldValue as com.centrifugo.client.protocol.protobuf.Error
            4 -> push = _fieldValue as com.centrifugo.client.protocol.protobuf.Push
            5 -> connect = _fieldValue as com.centrifugo.client.protocol.protobuf.ConnectResult
            6 -> subscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.SubscribeResult
            7 -> unsubscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.UnsubscribeResult
            8 -> publish = _fieldValue as com.centrifugo.client.protocol.protobuf.PublishResult
            9 -> presence = _fieldValue as com.centrifugo.client.protocol.protobuf.PresenceResult
            10 -> presenceStats = _fieldValue as com.centrifugo.client.protocol.protobuf.PresenceStatsResult
            11 -> history = _fieldValue as com.centrifugo.client.protocol.protobuf.HistoryResult
            12 -> ping = _fieldValue as com.centrifugo.client.protocol.protobuf.PingResult
            13 -> rpc = _fieldValue as com.centrifugo.client.protocol.protobuf.RPCResult
            14 -> refresh = _fieldValue as com.centrifugo.client.protocol.protobuf.RefreshResult
            15 -> subRefresh = _fieldValue as com.centrifugo.client.protocol.protobuf.SubRefreshResult
        }
    }

    return Reply(id, error, push, connect,
        subscribe, unsubscribe, publish, presence,
        presenceStats, history, ping, rpc,
        refresh, subRefresh, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPush")
public fun Push?.orDefault(): com.centrifugo.client.protocol.protobuf.Push = this ?: Push.defaultInstance

private fun Push.protoMergeImpl(plus: pbandk.Message?): Push = (plus as? Push)?.let {
    it.copy(
        pub = pub?.plus(plus.pub) ?: plus.pub,
        join = join?.plus(plus.join) ?: plus.join,
        leave = leave?.plus(plus.leave) ?: plus.leave,
        unsubscribe = unsubscribe?.plus(plus.unsubscribe) ?: plus.unsubscribe,
        message = message?.plus(plus.message) ?: plus.message,
        subscribe = subscribe?.plus(plus.subscribe) ?: plus.subscribe,
        connect = connect?.plus(plus.connect) ?: plus.connect,
        disconnect = disconnect?.plus(plus.disconnect) ?: plus.disconnect,
        refresh = refresh?.plus(plus.refresh) ?: plus.refresh,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Push.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Push {
    var id = 0L
    var channel = ""
    var pub: com.centrifugo.client.protocol.protobuf.Publication? = null
    var join: com.centrifugo.client.protocol.protobuf.Join? = null
    var leave: com.centrifugo.client.protocol.protobuf.Leave? = null
    var unsubscribe: com.centrifugo.client.protocol.protobuf.Unsubscribe? = null
    var message: com.centrifugo.client.protocol.protobuf.Message? = null
    var subscribe: com.centrifugo.client.protocol.protobuf.Subscribe? = null
    var connect: com.centrifugo.client.protocol.protobuf.Connect? = null
    var disconnect: com.centrifugo.client.protocol.protobuf.Disconnect? = null
    var refresh: com.centrifugo.client.protocol.protobuf.Refresh? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> id = _fieldValue as Long
            2 -> channel = _fieldValue as String
            4 -> pub = _fieldValue as com.centrifugo.client.protocol.protobuf.Publication
            5 -> join = _fieldValue as com.centrifugo.client.protocol.protobuf.Join
            6 -> leave = _fieldValue as com.centrifugo.client.protocol.protobuf.Leave
            7 -> unsubscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.Unsubscribe
            8 -> message = _fieldValue as com.centrifugo.client.protocol.protobuf.Message
            9 -> subscribe = _fieldValue as com.centrifugo.client.protocol.protobuf.Subscribe
            10 -> connect = _fieldValue as com.centrifugo.client.protocol.protobuf.Connect
            11 -> disconnect = _fieldValue as com.centrifugo.client.protocol.protobuf.Disconnect
            12 -> refresh = _fieldValue as com.centrifugo.client.protocol.protobuf.Refresh
        }
    }

    return Push(id, channel, pub, join,
        leave, unsubscribe, message, subscribe,
        connect, disconnect, refresh, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForClientInfo")
public fun ClientInfo?.orDefault(): com.centrifugo.client.protocol.protobuf.ClientInfo = this ?: ClientInfo.defaultInstance

private fun ClientInfo.protoMergeImpl(plus: pbandk.Message?): ClientInfo = (plus as? ClientInfo)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ClientInfo.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ClientInfo {
    var user = ""
    var client = ""
    var connInfo: pbandk.ByteArr = pbandk.ByteArr.empty
    var chanInfo: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> user = _fieldValue as String
            2 -> client = _fieldValue as String
            3 -> connInfo = _fieldValue as pbandk.ByteArr
            4 -> chanInfo = _fieldValue as pbandk.ByteArr
        }
    }

    return ClientInfo(user, client, connInfo, chanInfo, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPublication")
public fun Publication?.orDefault(): com.centrifugo.client.protocol.protobuf.Publication = this ?: Publication.defaultInstance

private fun Publication.protoMergeImpl(plus: pbandk.Message?): Publication = (plus as? Publication)?.let {
    it.copy(
        info = info?.plus(plus.info) ?: plus.info,
        tags = tags + plus.tags,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Publication.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Publication {
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null
    var offset = 0L
    var tags: pbandk.MessageMap.Builder<String, String>? = null
    var delta = false
    var time = 0L
    var channel = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            4 -> data = _fieldValue as pbandk.ByteArr
            5 -> info = _fieldValue as com.centrifugo.client.protocol.protobuf.ClientInfo
            6 -> offset = _fieldValue as Long
            7 -> tags = (tags ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, String>> }
            8 -> delta = _fieldValue as Boolean
            9 -> time = _fieldValue as Long
            10 -> channel = _fieldValue as String
        }
    }

    return Publication(data, info, offset, pbandk.MessageMap.Builder.fixed(tags),
        delta, time, channel, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPublicationTagsEntry")
public fun Publication.TagsEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.Publication.TagsEntry = this ?: Publication.TagsEntry.defaultInstance

private fun Publication.TagsEntry.protoMergeImpl(plus: pbandk.Message?): Publication.TagsEntry = (plus as? Publication.TagsEntry)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Publication.TagsEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Publication.TagsEntry {
    var key = ""
    var value = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as String
        }
    }

    return Publication.TagsEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForJoin")
public fun Join?.orDefault(): com.centrifugo.client.protocol.protobuf.Join = this ?: Join.defaultInstance

private fun Join.protoMergeImpl(plus: pbandk.Message?): Join = (plus as? Join)?.let {
    it.copy(
        info = info?.plus(plus.info) ?: plus.info,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Join.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Join {
    var info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> info = _fieldValue as com.centrifugo.client.protocol.protobuf.ClientInfo
        }
    }

    return Join(info, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForLeave")
public fun Leave?.orDefault(): com.centrifugo.client.protocol.protobuf.Leave = this ?: Leave.defaultInstance

private fun Leave.protoMergeImpl(plus: pbandk.Message?): Leave = (plus as? Leave)?.let {
    it.copy(
        info = info?.plus(plus.info) ?: plus.info,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Leave.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Leave {
    var info: com.centrifugo.client.protocol.protobuf.ClientInfo? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> info = _fieldValue as com.centrifugo.client.protocol.protobuf.ClientInfo
        }
    }

    return Leave(info, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForUnsubscribe")
public fun Unsubscribe?.orDefault(): com.centrifugo.client.protocol.protobuf.Unsubscribe = this ?: Unsubscribe.defaultInstance

private fun Unsubscribe.protoMergeImpl(plus: pbandk.Message?): Unsubscribe = (plus as? Unsubscribe)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Unsubscribe.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Unsubscribe {
    var code = 0
    var reason = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            2 -> code = _fieldValue as Int
            3 -> reason = _fieldValue as String
        }
    }

    return Unsubscribe(code, reason, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSubscribe")
public fun Subscribe?.orDefault(): com.centrifugo.client.protocol.protobuf.Subscribe = this ?: Subscribe.defaultInstance

private fun Subscribe.protoMergeImpl(plus: pbandk.Message?): Subscribe = (plus as? Subscribe)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Subscribe.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Subscribe {
    var recoverable = false
    var epoch = ""
    var offset = 0L
    var positioned = false
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> recoverable = _fieldValue as Boolean
            4 -> epoch = _fieldValue as String
            5 -> offset = _fieldValue as Long
            6 -> positioned = _fieldValue as Boolean
            7 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return Subscribe(recoverable, epoch, offset, positioned,
        data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForMessage")
public fun Message?.orDefault(): com.centrifugo.client.protocol.protobuf.Message = this ?: Message.defaultInstance

private fun Message.protoMergeImpl(plus: pbandk.Message?): Message = (plus as? Message)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Message.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Message {
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return Message(data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnect")
public fun Connect?.orDefault(): com.centrifugo.client.protocol.protobuf.Connect = this ?: Connect.defaultInstance

private fun Connect.protoMergeImpl(plus: pbandk.Message?): Connect = (plus as? Connect)?.let {
    it.copy(
        subs = subs + plus.subs,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Connect.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Connect {
    var client = ""
    var version = ""
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var subs: pbandk.MessageMap.Builder<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>? = null
    var expires = false
    var ttl = 0
    var ping = 0
    var pong = false
    var session = ""
    var node = ""
    var time = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> client = _fieldValue as String
            2 -> version = _fieldValue as String
            3 -> data = _fieldValue as pbandk.ByteArr
            4 -> subs = (subs ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>> }
            5 -> expires = _fieldValue as Boolean
            6 -> ttl = _fieldValue as Int
            7 -> ping = _fieldValue as Int
            8 -> pong = _fieldValue as Boolean
            9 -> session = _fieldValue as String
            10 -> node = _fieldValue as String
            11 -> time = _fieldValue as Long
        }
    }

    return Connect(client, version, data, pbandk.MessageMap.Builder.fixed(subs),
        expires, ttl, ping, pong,
        session, node, time, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectSubsEntry")
public fun Connect.SubsEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.Connect.SubsEntry = this ?: Connect.SubsEntry.defaultInstance

private fun Connect.SubsEntry.protoMergeImpl(plus: pbandk.Message?): Connect.SubsEntry = (plus as? Connect.SubsEntry)?.let {
    it.copy(
        value = value?.plus(plus.value) ?: plus.value,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Connect.SubsEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Connect.SubsEntry {
    var key = ""
    var value: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as com.centrifugo.client.protocol.protobuf.SubscribeResult
        }
    }

    return Connect.SubsEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForDisconnect")
public fun Disconnect?.orDefault(): com.centrifugo.client.protocol.protobuf.Disconnect = this ?: Disconnect.defaultInstance

private fun Disconnect.protoMergeImpl(plus: pbandk.Message?): Disconnect = (plus as? Disconnect)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Disconnect.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Disconnect {
    var code = 0
    var reason = ""
    var reconnect = false

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> code = _fieldValue as Int
            2 -> reason = _fieldValue as String
            3 -> reconnect = _fieldValue as Boolean
        }
    }

    return Disconnect(code, reason, reconnect, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForRefresh")
public fun Refresh?.orDefault(): com.centrifugo.client.protocol.protobuf.Refresh = this ?: Refresh.defaultInstance

private fun Refresh.protoMergeImpl(plus: pbandk.Message?): Refresh = (plus as? Refresh)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun Refresh.Companion.decodeWithImpl(u: pbandk.MessageDecoder): Refresh {
    var expires = false
    var ttl = 0

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> expires = _fieldValue as Boolean
            2 -> ttl = _fieldValue as Int
        }
    }

    return Refresh(expires, ttl, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectRequest")
public fun ConnectRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.ConnectRequest = this ?: ConnectRequest.defaultInstance

private fun ConnectRequest.protoMergeImpl(plus: pbandk.Message?): ConnectRequest = (plus as? ConnectRequest)?.let {
    it.copy(
        subs = subs + plus.subs,
        headers = headers + plus.headers,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ConnectRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ConnectRequest {
    var token = ""
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var subs: pbandk.MessageMap.Builder<String, com.centrifugo.client.protocol.protobuf.SubscribeRequest?>? = null
    var name = ""
    var version = ""
    var headers: pbandk.MessageMap.Builder<String, String>? = null
    var flag = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> token = _fieldValue as String
            2 -> data = _fieldValue as pbandk.ByteArr
            3 -> subs = (subs ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeRequest?>> }
            4 -> name = _fieldValue as String
            5 -> version = _fieldValue as String
            6 -> headers = (headers ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, String>> }
            7 -> flag = _fieldValue as Long
        }
    }

    return ConnectRequest(token, data, pbandk.MessageMap.Builder.fixed(subs), name,
        version, pbandk.MessageMap.Builder.fixed(headers), flag, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectRequestSubsEntry")
public fun ConnectRequest.SubsEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.ConnectRequest.SubsEntry = this ?: ConnectRequest.SubsEntry.defaultInstance

private fun ConnectRequest.SubsEntry.protoMergeImpl(plus: pbandk.Message?): ConnectRequest.SubsEntry = (plus as? ConnectRequest.SubsEntry)?.let {
    it.copy(
        value = value?.plus(plus.value) ?: plus.value,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ConnectRequest.SubsEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ConnectRequest.SubsEntry {
    var key = ""
    var value: com.centrifugo.client.protocol.protobuf.SubscribeRequest? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as com.centrifugo.client.protocol.protobuf.SubscribeRequest
        }
    }

    return ConnectRequest.SubsEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectRequestHeadersEntry")
public fun ConnectRequest.HeadersEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.ConnectRequest.HeadersEntry = this ?: ConnectRequest.HeadersEntry.defaultInstance

private fun ConnectRequest.HeadersEntry.protoMergeImpl(plus: pbandk.Message?): ConnectRequest.HeadersEntry = (plus as? ConnectRequest.HeadersEntry)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ConnectRequest.HeadersEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ConnectRequest.HeadersEntry {
    var key = ""
    var value = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as String
        }
    }

    return ConnectRequest.HeadersEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectResult")
public fun ConnectResult?.orDefault(): com.centrifugo.client.protocol.protobuf.ConnectResult = this ?: ConnectResult.defaultInstance

private fun ConnectResult.protoMergeImpl(plus: pbandk.Message?): ConnectResult = (plus as? ConnectResult)?.let {
    it.copy(
        subs = subs + plus.subs,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ConnectResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ConnectResult {
    var client = ""
    var version = ""
    var expires = false
    var ttl = 0
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var subs: pbandk.MessageMap.Builder<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>? = null
    var ping = 0
    var pong = false
    var session = ""
    var node = ""
    var time = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> client = _fieldValue as String
            2 -> version = _fieldValue as String
            3 -> expires = _fieldValue as Boolean
            4 -> ttl = _fieldValue as Int
            5 -> data = _fieldValue as pbandk.ByteArr
            6 -> subs = (subs ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, com.centrifugo.client.protocol.protobuf.SubscribeResult?>> }
            7 -> ping = _fieldValue as Int
            8 -> pong = _fieldValue as Boolean
            9 -> session = _fieldValue as String
            10 -> node = _fieldValue as String
            11 -> time = _fieldValue as Long
        }
    }

    return ConnectResult(client, version, expires, ttl,
        data, pbandk.MessageMap.Builder.fixed(subs), ping, pong,
        session, node, time, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForConnectResultSubsEntry")
public fun ConnectResult.SubsEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.ConnectResult.SubsEntry = this ?: ConnectResult.SubsEntry.defaultInstance

private fun ConnectResult.SubsEntry.protoMergeImpl(plus: pbandk.Message?): ConnectResult.SubsEntry = (plus as? ConnectResult.SubsEntry)?.let {
    it.copy(
        value = value?.plus(plus.value) ?: plus.value,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun ConnectResult.SubsEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): ConnectResult.SubsEntry {
    var key = ""
    var value: com.centrifugo.client.protocol.protobuf.SubscribeResult? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as com.centrifugo.client.protocol.protobuf.SubscribeResult
        }
    }

    return ConnectResult.SubsEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForRefreshRequest")
public fun RefreshRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.RefreshRequest = this ?: RefreshRequest.defaultInstance

private fun RefreshRequest.protoMergeImpl(plus: pbandk.Message?): RefreshRequest = (plus as? RefreshRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun RefreshRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): RefreshRequest {
    var token = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> token = _fieldValue as String
        }
    }

    return RefreshRequest(token, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForRefreshResult")
public fun RefreshResult?.orDefault(): com.centrifugo.client.protocol.protobuf.RefreshResult = this ?: RefreshResult.defaultInstance

private fun RefreshResult.protoMergeImpl(plus: pbandk.Message?): RefreshResult = (plus as? RefreshResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun RefreshResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): RefreshResult {
    var client = ""
    var version = ""
    var expires = false
    var ttl = 0

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> client = _fieldValue as String
            2 -> version = _fieldValue as String
            3 -> expires = _fieldValue as Boolean
            4 -> ttl = _fieldValue as Int
        }
    }

    return RefreshResult(client, version, expires, ttl, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSubscribeRequest")
public fun SubscribeRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.SubscribeRequest = this ?: SubscribeRequest.defaultInstance

private fun SubscribeRequest.protoMergeImpl(plus: pbandk.Message?): SubscribeRequest = (plus as? SubscribeRequest)?.let {
    it.copy(
        tf = tf?.plus(plus.tf) ?: plus.tf,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun SubscribeRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): SubscribeRequest {
    var channel = ""
    var token = ""
    var recover = false
    var epoch = ""
    var offset = 0L
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var positioned = false
    var recoverable = false
    var joinLeave = false
    var delta = ""
    var tf: com.centrifugo.client.protocol.protobuf.FilterNode? = null
    var flag = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
            2 -> token = _fieldValue as String
            3 -> recover = _fieldValue as Boolean
            6 -> epoch = _fieldValue as String
            7 -> offset = _fieldValue as Long
            8 -> data = _fieldValue as pbandk.ByteArr
            9 -> positioned = _fieldValue as Boolean
            10 -> recoverable = _fieldValue as Boolean
            11 -> joinLeave = _fieldValue as Boolean
            12 -> delta = _fieldValue as String
            13 -> tf = _fieldValue as com.centrifugo.client.protocol.protobuf.FilterNode
            14 -> flag = _fieldValue as Long
        }
    }

    return SubscribeRequest(channel, token, recover, epoch,
        offset, data, positioned, recoverable,
        joinLeave, delta, tf, flag, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSubscribeResult")
public fun SubscribeResult?.orDefault(): com.centrifugo.client.protocol.protobuf.SubscribeResult = this ?: SubscribeResult.defaultInstance

private fun SubscribeResult.protoMergeImpl(plus: pbandk.Message?): SubscribeResult = (plus as? SubscribeResult)?.let {
    it.copy(
        publications = publications + plus.publications,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun SubscribeResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): SubscribeResult {
    var expires = false
    var ttl = 0
    var recoverable = false
    var epoch = ""
    var publications: pbandk.ListWithSize.Builder<com.centrifugo.client.protocol.protobuf.Publication>? = null
    var recovered = false
    var offset = 0L
    var positioned = false
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var wasRecovering = false
    var delta = false
    var id = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> expires = _fieldValue as Boolean
            2 -> ttl = _fieldValue as Int
            3 -> recoverable = _fieldValue as Boolean
            6 -> epoch = _fieldValue as String
            7 -> publications = (publications ?: pbandk.ListWithSize.Builder()).apply { this += _fieldValue as kotlin.sequences.Sequence<com.centrifugo.client.protocol.protobuf.Publication> }
            8 -> recovered = _fieldValue as Boolean
            9 -> offset = _fieldValue as Long
            10 -> positioned = _fieldValue as Boolean
            11 -> data = _fieldValue as pbandk.ByteArr
            12 -> wasRecovering = _fieldValue as Boolean
            13 -> delta = _fieldValue as Boolean
            14 -> id = _fieldValue as Long
        }
    }

    return SubscribeResult(expires, ttl, recoverable, epoch,
        pbandk.ListWithSize.Builder.fixed(publications), recovered, offset, positioned,
        data, wasRecovering, delta, id, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSubRefreshRequest")
public fun SubRefreshRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.SubRefreshRequest = this ?: SubRefreshRequest.defaultInstance

private fun SubRefreshRequest.protoMergeImpl(plus: pbandk.Message?): SubRefreshRequest = (plus as? SubRefreshRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun SubRefreshRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): SubRefreshRequest {
    var channel = ""
    var token = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
            2 -> token = _fieldValue as String
        }
    }

    return SubRefreshRequest(channel, token, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSubRefreshResult")
public fun SubRefreshResult?.orDefault(): com.centrifugo.client.protocol.protobuf.SubRefreshResult = this ?: SubRefreshResult.defaultInstance

private fun SubRefreshResult.protoMergeImpl(plus: pbandk.Message?): SubRefreshResult = (plus as? SubRefreshResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun SubRefreshResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): SubRefreshResult {
    var expires = false
    var ttl = 0

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> expires = _fieldValue as Boolean
            2 -> ttl = _fieldValue as Int
        }
    }

    return SubRefreshResult(expires, ttl, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForUnsubscribeRequest")
public fun UnsubscribeRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.UnsubscribeRequest = this ?: UnsubscribeRequest.defaultInstance

private fun UnsubscribeRequest.protoMergeImpl(plus: pbandk.Message?): UnsubscribeRequest = (plus as? UnsubscribeRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun UnsubscribeRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): UnsubscribeRequest {
    var channel = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
        }
    }

    return UnsubscribeRequest(channel, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForUnsubscribeResult")
public fun UnsubscribeResult?.orDefault(): com.centrifugo.client.protocol.protobuf.UnsubscribeResult = this ?: UnsubscribeResult.defaultInstance

private fun UnsubscribeResult.protoMergeImpl(plus: pbandk.Message?): UnsubscribeResult = (plus as? UnsubscribeResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun UnsubscribeResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): UnsubscribeResult {

    val unknownFields = u.readMessage(this) { _, _ -> }

    return UnsubscribeResult(unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPublishRequest")
public fun PublishRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.PublishRequest = this ?: PublishRequest.defaultInstance

private fun PublishRequest.protoMergeImpl(plus: pbandk.Message?): PublishRequest = (plus as? PublishRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PublishRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PublishRequest {
    var channel = ""
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
            2 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return PublishRequest(channel, data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPublishResult")
public fun PublishResult?.orDefault(): com.centrifugo.client.protocol.protobuf.PublishResult = this ?: PublishResult.defaultInstance

private fun PublishResult.protoMergeImpl(plus: pbandk.Message?): PublishResult = (plus as? PublishResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PublishResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PublishResult {

    val unknownFields = u.readMessage(this) { _, _ -> }

    return PublishResult(unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPresenceRequest")
public fun PresenceRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.PresenceRequest = this ?: PresenceRequest.defaultInstance

private fun PresenceRequest.protoMergeImpl(plus: pbandk.Message?): PresenceRequest = (plus as? PresenceRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PresenceRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PresenceRequest {
    var channel = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
        }
    }

    return PresenceRequest(channel, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPresenceResult")
public fun PresenceResult?.orDefault(): com.centrifugo.client.protocol.protobuf.PresenceResult = this ?: PresenceResult.defaultInstance

private fun PresenceResult.protoMergeImpl(plus: pbandk.Message?): PresenceResult = (plus as? PresenceResult)?.let {
    it.copy(
        presence = presence + plus.presence,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PresenceResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PresenceResult {
    var presence: pbandk.MessageMap.Builder<String, com.centrifugo.client.protocol.protobuf.ClientInfo?>? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> presence = (presence ?: pbandk.MessageMap.Builder()).apply { this.entries += _fieldValue as kotlin.sequences.Sequence<pbandk.MessageMap.Entry<String, com.centrifugo.client.protocol.protobuf.ClientInfo?>> }
        }
    }

    return PresenceResult(pbandk.MessageMap.Builder.fixed(presence), unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPresenceResultPresenceEntry")
public fun PresenceResult.PresenceEntry?.orDefault(): com.centrifugo.client.protocol.protobuf.PresenceResult.PresenceEntry = this ?: PresenceResult.PresenceEntry.defaultInstance

private fun PresenceResult.PresenceEntry.protoMergeImpl(plus: pbandk.Message?): PresenceResult.PresenceEntry = (plus as? PresenceResult.PresenceEntry)?.let {
    it.copy(
        value = value?.plus(plus.value) ?: plus.value,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PresenceResult.PresenceEntry.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PresenceResult.PresenceEntry {
    var key = ""
    var value: com.centrifugo.client.protocol.protobuf.ClientInfo? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> key = _fieldValue as String
            2 -> value = _fieldValue as com.centrifugo.client.protocol.protobuf.ClientInfo
        }
    }

    return PresenceResult.PresenceEntry(key, value, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPresenceStatsRequest")
public fun PresenceStatsRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.PresenceStatsRequest = this ?: PresenceStatsRequest.defaultInstance

private fun PresenceStatsRequest.protoMergeImpl(plus: pbandk.Message?): PresenceStatsRequest = (plus as? PresenceStatsRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PresenceStatsRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PresenceStatsRequest {
    var channel = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
        }
    }

    return PresenceStatsRequest(channel, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPresenceStatsResult")
public fun PresenceStatsResult?.orDefault(): com.centrifugo.client.protocol.protobuf.PresenceStatsResult = this ?: PresenceStatsResult.defaultInstance

private fun PresenceStatsResult.protoMergeImpl(plus: pbandk.Message?): PresenceStatsResult = (plus as? PresenceStatsResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PresenceStatsResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PresenceStatsResult {
    var numClients = 0
    var numUsers = 0

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> numClients = _fieldValue as Int
            2 -> numUsers = _fieldValue as Int
        }
    }

    return PresenceStatsResult(numClients, numUsers, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForStreamPosition")
public fun StreamPosition?.orDefault(): com.centrifugo.client.protocol.protobuf.StreamPosition = this ?: StreamPosition.defaultInstance

private fun StreamPosition.protoMergeImpl(plus: pbandk.Message?): StreamPosition = (plus as? StreamPosition)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun StreamPosition.Companion.decodeWithImpl(u: pbandk.MessageDecoder): StreamPosition {
    var offset = 0L
    var epoch = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> offset = _fieldValue as Long
            2 -> epoch = _fieldValue as String
        }
    }

    return StreamPosition(offset, epoch, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForHistoryRequest")
public fun HistoryRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.HistoryRequest = this ?: HistoryRequest.defaultInstance

private fun HistoryRequest.protoMergeImpl(plus: pbandk.Message?): HistoryRequest = (plus as? HistoryRequest)?.let {
    it.copy(
        since = since?.plus(plus.since) ?: plus.since,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun HistoryRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): HistoryRequest {
    var channel = ""
    var limit = 0
    var since: com.centrifugo.client.protocol.protobuf.StreamPosition? = null
    var reverse = false

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> channel = _fieldValue as String
            7 -> limit = _fieldValue as Int
            8 -> since = _fieldValue as com.centrifugo.client.protocol.protobuf.StreamPosition
            9 -> reverse = _fieldValue as Boolean
        }
    }

    return HistoryRequest(channel, limit, since, reverse, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForHistoryResult")
public fun HistoryResult?.orDefault(): com.centrifugo.client.protocol.protobuf.HistoryResult = this ?: HistoryResult.defaultInstance

private fun HistoryResult.protoMergeImpl(plus: pbandk.Message?): HistoryResult = (plus as? HistoryResult)?.let {
    it.copy(
        publications = publications + plus.publications,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun HistoryResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): HistoryResult {
    var publications: pbandk.ListWithSize.Builder<com.centrifugo.client.protocol.protobuf.Publication>? = null
    var epoch = ""
    var offset = 0L

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> publications = (publications ?: pbandk.ListWithSize.Builder()).apply { this += _fieldValue as kotlin.sequences.Sequence<com.centrifugo.client.protocol.protobuf.Publication> }
            2 -> epoch = _fieldValue as String
            3 -> offset = _fieldValue as Long
        }
    }

    return HistoryResult(pbandk.ListWithSize.Builder.fixed(publications), epoch, offset, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPingRequest")
public fun PingRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.PingRequest = this ?: PingRequest.defaultInstance

private fun PingRequest.protoMergeImpl(plus: pbandk.Message?): PingRequest = (plus as? PingRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PingRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PingRequest {

    val unknownFields = u.readMessage(this) { _, _ -> }

    return PingRequest(unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForPingResult")
public fun PingResult?.orDefault(): com.centrifugo.client.protocol.protobuf.PingResult = this ?: PingResult.defaultInstance

private fun PingResult.protoMergeImpl(plus: pbandk.Message?): PingResult = (plus as? PingResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun PingResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): PingResult {

    val unknownFields = u.readMessage(this) { _, _ -> }

    return PingResult(unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForRPCRequest")
public fun RPCRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.RPCRequest = this ?: RPCRequest.defaultInstance

private fun RPCRequest.protoMergeImpl(plus: pbandk.Message?): RPCRequest = (plus as? RPCRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun RPCRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): RPCRequest {
    var data: pbandk.ByteArr = pbandk.ByteArr.empty
    var method = ""

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> data = _fieldValue as pbandk.ByteArr
            2 -> method = _fieldValue as String
        }
    }

    return RPCRequest(data, method, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForRPCResult")
public fun RPCResult?.orDefault(): com.centrifugo.client.protocol.protobuf.RPCResult = this ?: RPCResult.defaultInstance

private fun RPCResult.protoMergeImpl(plus: pbandk.Message?): RPCResult = (plus as? RPCResult)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun RPCResult.Companion.decodeWithImpl(u: pbandk.MessageDecoder): RPCResult {
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return RPCResult(data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForSendRequest")
public fun SendRequest?.orDefault(): com.centrifugo.client.protocol.protobuf.SendRequest = this ?: SendRequest.defaultInstance

private fun SendRequest.protoMergeImpl(plus: pbandk.Message?): SendRequest = (plus as? SendRequest)?.let {
    it.copy(
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun SendRequest.Companion.decodeWithImpl(u: pbandk.MessageDecoder): SendRequest {
    var data: pbandk.ByteArr = pbandk.ByteArr.empty

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> data = _fieldValue as pbandk.ByteArr
        }
    }

    return SendRequest(data, unknownFields)
}

@pbandk.Export
@pbandk.JsName("orDefaultForFilterNode")
public fun FilterNode?.orDefault(): com.centrifugo.client.protocol.protobuf.FilterNode = this ?: FilterNode.defaultInstance

private fun FilterNode.protoMergeImpl(plus: pbandk.Message?): FilterNode = (plus as? FilterNode)?.let {
    it.copy(
        vals = vals + plus.vals,
        nodes = nodes + plus.nodes,
        unknownFields = unknownFields + plus.unknownFields
    )
} ?: this

@Suppress("UNCHECKED_CAST")
private fun FilterNode.Companion.decodeWithImpl(u: pbandk.MessageDecoder): FilterNode {
    var op = ""
    var key = ""
    var cmp = ""
    var `val` = ""
    var vals: pbandk.ListWithSize.Builder<String>? = null
    var nodes: pbandk.ListWithSize.Builder<com.centrifugo.client.protocol.protobuf.FilterNode>? = null

    val unknownFields = u.readMessage(this) { _fieldNumber, _fieldValue ->
        when (_fieldNumber) {
            1 -> op = _fieldValue as String
            2 -> key = _fieldValue as String
            3 -> cmp = _fieldValue as String
            4 -> `val` = _fieldValue as String
            5 -> vals = (vals ?: pbandk.ListWithSize.Builder()).apply { this += _fieldValue as kotlin.sequences.Sequence<String> }
            6 -> nodes = (nodes ?: pbandk.ListWithSize.Builder()).apply { this += _fieldValue as kotlin.sequences.Sequence<com.centrifugo.client.protocol.protobuf.FilterNode> }
        }
    }

    return FilterNode(op, key, cmp, `val`,
        pbandk.ListWithSize.Builder.fixed(vals), pbandk.ListWithSize.Builder.fixed(nodes), unknownFields)
}
