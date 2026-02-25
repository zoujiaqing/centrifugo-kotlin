package com.centrifugo.client.protocol

sealed class Reply {
    data class Push(val push: PushMessage) : Reply()
    data class Error(val error: com.centrifugo.client.protocol.Error) : Reply()
    data class Connect(val result: ConnectResult) : Reply()
    data class Subscribe(val result: SubscribeResult) : Reply()
    data class Unsubscribe(val result: UnsubscribeResult) : Reply()
    data class Publish(val result: PublishResult) : Reply()
    data class Presence(val result: PresenceResult) : Reply()
    data class PresenceStats(val result: PresenceStatsResult) : Reply()
    data class History(val result: HistoryResult) : Reply()
    data class Ping(val result: PingResult) : Reply()
    data class Rpc(val result: RpcResult) : Reply()
    data class Refresh(val result: RefreshResult) : Reply()
    data class SubRefresh(val result: SubRefreshResult) : Reply()
    data object Empty : Reply()

    companion object {
        fun fromRawReply(raw: RawReply): Reply {
            raw.error?.let { return Error(it) }
            raw.push?.let { return Push(PushMessage.fromRawPush(it)) }
            raw.connect?.let { return Connect(it) }
            raw.subscribe?.let { return Subscribe(it) }
            raw.unsubscribe?.let { return Unsubscribe(it) }
            raw.publish?.let { return Publish(it) }
            raw.presence?.let { return Presence(it) }
            raw.presenceStats?.let { return PresenceStats(it) }
            raw.history?.let { return History(it) }
            raw.ping?.let { return Ping(it) }
            raw.rpc?.let { return Rpc(it) }
            raw.refresh?.let { return Refresh(it) }
            raw.subRefresh?.let { return SubRefresh(it) }
            return Empty
        }
    }
}

data class PushMessage(
    val channel: String,
    val data: PushData,
) {
    companion object {
        fun fromRawPush(raw: RawPush): PushMessage {
            val data = when {
                raw.publication != null -> PushData.Publication(raw.publication)
                raw.join != null -> PushData.Join(raw.join)
                raw.leave != null -> PushData.Leave(raw.leave)
                raw.unsubscribe != null -> PushData.Unsubscribe(raw.unsubscribe)
                raw.message != null -> PushData.Message(raw.message)
                raw.subscribe != null -> PushData.Subscribe(raw.subscribe)
                raw.connect != null -> PushData.Connect(raw.connect)
                raw.disconnect != null -> PushData.Disconnect(raw.disconnect)
                raw.refresh != null -> PushData.Refresh(raw.refresh)
                else -> PushData.Empty
            }
            return PushMessage(channel = raw.channel, data = data)
        }
    }
}

sealed class PushData {
    data class Publication(val publication: com.centrifugo.client.protocol.Publication) : PushData()
    data class Join(val join: com.centrifugo.client.protocol.Join) : PushData()
    data class Leave(val leave: com.centrifugo.client.protocol.Leave) : PushData()
    data class Unsubscribe(val unsubscribe: com.centrifugo.client.protocol.Unsubscribe) : PushData()
    data class Message(val message: com.centrifugo.client.protocol.Message) : PushData()
    data class Subscribe(val subscribe: com.centrifugo.client.protocol.Subscribe) : PushData()
    data class Connect(val connect: com.centrifugo.client.protocol.Connect) : PushData()
    data class Disconnect(val disconnect: com.centrifugo.client.protocol.Disconnect) : PushData()
    data class Refresh(val refresh: com.centrifugo.client.protocol.Refresh) : PushData()
    data object Empty : PushData()
}
