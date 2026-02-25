package com.centrifugo.client

import com.centrifugo.client.protocol.ClientInfo
import com.centrifugo.client.protocol.Publication
import com.centrifugo.client.protocol.StreamPosition
import kotlinx.serialization.json.JsonElement

data class ConnectedEvent(
    val clientId: String,
    val version: String,
    val data: JsonElement?,
)

data class ConnectingEvent(
    val code: Int,
    val reason: String,
)

data class DisconnectedEvent(
    val code: Int,
    val reason: String,
)

data class ErrorEvent(
    val error: Exception,
)

data class MessageEvent(
    val data: JsonElement?,
)

data class SubscribedEvent(
    val channel: String,
    val wasRecovering: Boolean = false,
    val recovered: Boolean = false,
    val data: JsonElement? = null,
    val streamPosition: StreamPosition? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
)

data class SubscribingEvent(
    val channel: String,
    val code: Int,
    val reason: String,
)

data class UnsubscribedEvent(
    val channel: String,
    val code: Int,
    val reason: String,
)

data class PublicationEvent(
    val data: JsonElement?,
    val offset: Long,
    val info: ClientInfo?,
    val tags: Map<String, String>,
    val channel: String,
) {
    companion object {
        fun fromPublication(publication: Publication): PublicationEvent {
            return PublicationEvent(
                data = publication.data,
                offset = publication.offset,
                info = publication.info,
                tags = publication.tags,
                channel = publication.channel,
            )
        }
    }
}

data class JoinEvent(
    val channel: String,
    val info: ClientInfo,
)

data class LeaveEvent(
    val channel: String,
    val info: ClientInfo,
)

data class ServerSubscribedEvent(
    val channel: String,
    val wasRecovering: Boolean = false,
    val recovered: Boolean = false,
    val data: JsonElement? = null,
    val streamPosition: StreamPosition? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
)

data class ServerSubscribingEvent(
    val channel: String,
)

data class ServerUnsubscribedEvent(
    val channel: String,
)

data class ServerPublicationEvent(
    val channel: String,
    val data: JsonElement?,
    val offset: Long,
    val info: ClientInfo?,
    val tags: Map<String, String>,
)

data class ServerJoinEvent(
    val channel: String,
    val info: ClientInfo,
)

data class ServerLeaveEvent(
    val channel: String,
    val info: ClientInfo,
)

data class SubscriptionErrorEvent(
    val error: Exception,
)

// Result types for client operations
data class HistoryResult(
    val publications: List<PublicationEvent>,
    val offset: Long,
    val epoch: String,
)

data class PresenceResult(
    val clients: Map<String, ClientInfo>,
)

data class PresenceStatsResult(
    val numClients: Int,
    val numUsers: Int,
)
