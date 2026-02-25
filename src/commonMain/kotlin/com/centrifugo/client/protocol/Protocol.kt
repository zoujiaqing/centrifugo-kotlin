package com.centrifugo.client.protocol

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class Error(
    val code: Int = 0,
    val message: String = "",
    val temporary: Boolean = false,
)

@Serializable
data class RawCommand(
    val id: Int = 0,
    val connect: ConnectRequest? = null,
    val subscribe: SubscribeRequest? = null,
    val unsubscribe: UnsubscribeRequest? = null,
    val publish: PublishRequest? = null,
    val presence: PresenceRequest? = null,
    @SerialName("presenceStats")
    val presenceStats: PresenceStatsRequest? = null,
    val history: HistoryRequest? = null,
    val ping: PingRequest? = null,
    val send: SendRequest? = null,
    val rpc: RpcRequest? = null,
    val refresh: RefreshRequest? = null,
    @SerialName("subRefresh")
    val subRefresh: SubRefreshRequest? = null,
)

@Serializable
data class RawReply(
    val id: Int = 0,
    val error: Error? = null,
    val push: RawPush? = null,
    val connect: ConnectResult? = null,
    val subscribe: SubscribeResult? = null,
    val unsubscribe: UnsubscribeResult? = null,
    val publish: PublishResult? = null,
    val presence: PresenceResult? = null,
    @SerialName("presenceStats")
    val presenceStats: PresenceStatsResult? = null,
    val history: HistoryResult? = null,
    val ping: PingResult? = null,
    val rpc: RpcResult? = null,
    val refresh: RefreshResult? = null,
    @SerialName("subRefresh")
    val subRefresh: SubRefreshResult? = null,
)

@Serializable
data class RawPush(
    val channel: String = "",
    @SerialName("pub")
    val publication: Publication? = null,
    val join: Join? = null,
    val leave: Leave? = null,
    val unsubscribe: Unsubscribe? = null,
    val message: Message? = null,
    val subscribe: Subscribe? = null,
    val connect: Connect? = null,
    val disconnect: Disconnect? = null,
    val refresh: Refresh? = null,
)

@Serializable
data class ClientInfo(
    val user: String = "",
    val client: String = "",
    @SerialName("connInfo")
    val connInfo: JsonElement? = null,
    @SerialName("chanInfo")
    val chanInfo: JsonElement? = null,
)

@Serializable
data class Publication(
    val data: JsonElement? = null,
    val info: ClientInfo? = null,
    val offset: Long = 0,
    val tags: Map<String, String> = emptyMap(),
    val delta: Boolean = false,
    val time: Long = 0,
    val channel: String = "",
)

@Serializable
data class Join(
    val info: ClientInfo? = null,
)

@Serializable
data class Leave(
    val info: ClientInfo? = null,
)

@Serializable
data class Unsubscribe(
    val code: Int = 0,
    val reason: String = "",
)

@Serializable
data class Subscribe(
    val recoverable: Boolean = false,
    val epoch: String = "",
    val offset: Long = 0,
    val positioned: Boolean = false,
    val data: JsonElement? = null,
)

@Serializable
data class Message(
    val data: JsonElement? = null,
)

@Serializable
data class Connect(
    val client: String = "",
    val version: String = "",
    val data: JsonElement? = null,
    val subs: Map<String, SubscribeResult> = emptyMap(),
    val expires: Boolean = false,
    val ttl: Int = 0,
    val ping: Int = 0,
    val pong: Boolean = false,
    val session: String = "",
    val node: String = "",
    val time: Long = 0,
)

@Serializable
data class Disconnect(
    val code: Int = 0,
    val reason: String = "",
    val reconnect: Boolean = false,
)

@Serializable
data class Refresh(
    val expires: Boolean = false,
    val ttl: Int = 0,
)

@Serializable
data class ConnectRequest(
    val token: String = "",
    val data: JsonElement? = null,
    val subs: Map<String, SubscribeRequest> = emptyMap(),
    val name: String = "",
    val version: String = "",
    val headers: Map<String, String> = emptyMap(),
)

@Serializable
data class ConnectResult(
    val client: String = "",
    val version: String = "",
    val expires: Boolean = false,
    val ttl: Int = 0,
    val data: JsonElement? = null,
    val subs: Map<String, SubscribeResult> = emptyMap(),
    val ping: Int = 0,
    val pong: Boolean = false,
    val session: String = "",
    val node: String = "",
    val time: Long = 0,
)

@Serializable
data class RefreshRequest(
    val token: String = "",
)

@Serializable
data class RefreshResult(
    val client: String = "",
    val version: String = "",
    val expires: Boolean = false,
    val ttl: Int = 0,
)

@Serializable
data class SubscribeRequest(
    val channel: String = "",
    val token: String = "",
    val recover: Boolean = false,
    val epoch: String = "",
    val offset: Long = 0,
    val data: JsonElement? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
    @SerialName("joinLeave")
    val joinLeave: Boolean = false,
    val delta: String = "",
    val tf: FilterNode? = null,
)

@Serializable
data class SubscribeResult(
    val expires: Boolean = false,
    val ttl: Int = 0,
    val recoverable: Boolean = false,
    val epoch: String = "",
    val publications: List<Publication> = emptyList(),
    val recovered: Boolean = false,
    val offset: Long = 0,
    val positioned: Boolean = false,
    val data: JsonElement? = null,
    @SerialName("wasRecovering")
    val wasRecovering: Boolean = false,
    val delta: Boolean = false,
)

@Serializable
data class SubRefreshRequest(
    val channel: String = "",
    val token: String = "",
)

@Serializable
data class SubRefreshResult(
    val expires: Boolean = false,
    val ttl: Int = 0,
)

@Serializable
data class UnsubscribeRequest(
    val channel: String = "",
)

@Serializable
class UnsubscribeResult

@Serializable
data class PublishRequest(
    val channel: String = "",
    val data: JsonElement? = null,
)

@Serializable
class PublishResult

@Serializable
data class PresenceRequest(
    val channel: String = "",
)

@Serializable
data class PresenceResult(
    val presence: Map<String, ClientInfo> = emptyMap(),
)

@Serializable
data class PresenceStatsRequest(
    val channel: String = "",
)

@Serializable
data class PresenceStatsResult(
    @SerialName("numClients")
    val numClients: Int = 0,
    @SerialName("numUsers")
    val numUsers: Int = 0,
)

@Serializable
data class StreamPosition(
    val offset: Long = 0,
    val epoch: String = "",
)

@Serializable
data class HistoryRequest(
    val channel: String = "",
    val limit: Int = 0,
    val since: StreamPosition? = null,
    val reverse: Boolean = false,
)

@Serializable
data class HistoryResult(
    val publications: List<Publication> = emptyList(),
    val epoch: String = "",
    val offset: Long = 0,
)

@Serializable
class PingRequest

@Serializable
class PingResult

@Serializable
data class RpcRequest(
    val data: JsonElement? = null,
    val method: String = "",
)

@Serializable
data class RpcResult(
    val data: JsonElement? = null,
)

@Serializable
data class SendRequest(
    val data: JsonElement? = null,
)

@Serializable
data class FilterNode(
    val op: String = "",
    val key: String = "",
    val cmp: String = "",
    val `val`: String = "",
    val vals: List<String> = emptyList(),
    val nodes: List<FilterNode> = emptyList(),
)

@Serializable
data class EmulationRequest(
    val session: String = "",
    val node: String = "",
    val data: String = "",
)
