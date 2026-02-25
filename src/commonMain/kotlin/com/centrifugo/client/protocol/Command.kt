package com.centrifugo.client.protocol

sealed class Command {
    data class Connect(val request: ConnectRequest) : Command()
    data class Subscribe(val request: SubscribeRequest) : Command()
    data class Unsubscribe(val request: UnsubscribeRequest) : Command()
    data class Publish(val request: PublishRequest) : Command()
    data class Presence(val request: PresenceRequest) : Command()
    data class PresenceStats(val request: PresenceStatsRequest) : Command()
    data class History(val request: HistoryRequest) : Command()
    data class Ping(val request: PingRequest) : Command()
    data class Send(val request: SendRequest) : Command()
    data class Rpc(val request: RpcRequest) : Command()
    data class Refresh(val request: RefreshRequest) : Command()
    data class SubRefresh(val request: SubRefreshRequest) : Command()
    data object Empty : Command()

    fun toRawCommand(id: Int = 0): RawCommand {
        return when (this) {
            is Connect -> RawCommand(id = id, connect = request)
            is Subscribe -> RawCommand(id = id, subscribe = request)
            is Unsubscribe -> RawCommand(id = id, unsubscribe = request)
            is Publish -> RawCommand(id = id, publish = request)
            is Presence -> RawCommand(id = id, presence = request)
            is PresenceStats -> RawCommand(id = id, presenceStats = request)
            is History -> RawCommand(id = id, history = request)
            is Ping -> RawCommand(id = id, ping = request)
            is Send -> RawCommand(id = id, send = request)
            is Rpc -> RawCommand(id = id, rpc = request)
            is Refresh -> RawCommand(id = id, refresh = request)
            is SubRefresh -> RawCommand(id = id, subRefresh = request)
            is Empty -> RawCommand(id = id)
        }
    }
}
