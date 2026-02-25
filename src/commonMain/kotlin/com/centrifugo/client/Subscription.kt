package com.centrifugo.client

import com.centrifugo.client.protocol.ClientInfo
import com.centrifugo.client.protocol.FilterNode
import com.centrifugo.client.protocol.Publication
import com.centrifugo.client.protocol.StreamPosition
import kotlinx.coroutines.CompletableDeferred
import kotlinx.serialization.json.JsonElement
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

enum class SubscriptionState {
    UNSUBSCRIBED,
    SUBSCRIBING,
    SUBSCRIBED,
}

data class SubscriptionConfig(
    val token: String = "",
    val data: JsonElement? = null,
    val getToken: (suspend (SubscriptionTokenEvent) -> String)? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
    val joinLeave: Boolean = false,
    val since: StreamPosition? = null,
    val delta: String = "",
    val tagsFilter: FilterNode? = null,
    val minResubscribeDelay: Duration = 500.milliseconds,
    val maxResubscribeDelay: Duration = 20.seconds,
)

data class SubscriptionTokenEvent(
    val channel: String,
)

internal class SubscriptionInner(
    val channel: String,
    val config: SubscriptionConfig,
    private val readTimeout: kotlin.time.Duration,
) {
    var state: SubscriptionState = SubscriptionState.UNSUBSCRIBED
        private set

    var onSubscribing: ((SubscribingEvent) -> Unit)? = null
    var onSubscribed: ((SubscribedEvent) -> Unit)? = null
    var onUnsubscribed: ((UnsubscribedEvent) -> Unit)? = null
    var onPublication: ((PublicationEvent) -> Unit)? = null
    var onJoin: ((JoinEvent) -> Unit)? = null
    var onLeave: ((LeaveEvent) -> Unit)? = null
    var onError: ((SubscriptionErrorEvent) -> Unit)? = null

    var tagsFilter: FilterNode? = config.tagsFilter

    var pubMessageStore: MessageStore? = null

    private val readyDeferred = CompletableDeferred<Unit>()

    fun moveToSubscribing(code: Int, reason: String) {
        if (pubMessageStore == null) {
            pubMessageStore = MessageStore(readTimeout)
        }
        setState(SubscriptionState.SUBSCRIBING)
        onSubscribing?.invoke(SubscribingEvent(channel, code, reason))
    }

    fun moveToSubscribed(result: com.centrifugo.client.protocol.SubscribeResult) {
        setState(SubscriptionState.SUBSCRIBED)
        if (!readyDeferred.isCompleted) {
            readyDeferred.complete(Unit)
        }
        onSubscribed?.invoke(SubscribedEvent(
            channel = channel,
            wasRecovering = result.wasRecovering,
            recovered = result.recovered,
            data = result.data,
            streamPosition = if (result.offset > 0 || result.epoch.isNotEmpty()) {
                StreamPosition(offset = result.offset, epoch = result.epoch)
            } else null,
            positioned = result.positioned,
            recoverable = result.recoverable,
        ))
    }

    fun moveToUnsubscribed(code: Int, reason: String) {
        pubMessageStore = null
        setState(SubscriptionState.UNSUBSCRIBED)
        onUnsubscribed?.invoke(UnsubscribedEvent(channel, code, reason))
    }

    fun handlePublication(publication: Publication) {
        if (state == SubscriptionState.SUBSCRIBED) {
            onPublication?.invoke(PublicationEvent.fromPublication(publication))
        }
    }

    fun handleJoin(info: ClientInfo) {
        if (state == SubscriptionState.SUBSCRIBED) {
            onJoin?.invoke(JoinEvent(channel = channel, info = info))
        }
    }

    fun handleLeave(info: ClientInfo) {
        if (state == SubscriptionState.SUBSCRIBED) {
            onLeave?.invoke(LeaveEvent(channel = channel, info = info))
        }
    }

    suspend fun waitReady() {
        readyDeferred.await()
    }

    private fun setState(newState: SubscriptionState) {
        state = newState
    }
}

class Subscription internal constructor(
    val channel: String,
    private val client: CentrifugoClient,
    internal val id: Int,
) {
    val state: SubscriptionState
        get() = client.getSubscriptionState(id)

    suspend fun subscribe() {
        client.doSubscribe(id)
    }

    suspend fun unsubscribe() {
        client.doUnsubscribe(id)
    }

    suspend fun ready() {
        client.waitSubscriptionReady(id)
    }

    suspend fun publish(data: JsonElement) {
        client.doSubscriptionPublish(id, data)
    }

    suspend fun history(limit: Int = 0, since: StreamPosition? = null, reverse: Boolean = false): HistoryResult {
        return client.doSubscriptionHistory(id, limit, since, reverse)
    }

    suspend fun presence(): PresenceResult {
        return client.doSubscriptionPresence(id)
    }

    suspend fun presenceStats(): PresenceStatsResult {
        return client.doSubscriptionPresenceStats(id)
    }

    fun setTagsFilter(tagsFilter: FilterNode?) {
        client.setSubscriptionTagsFilter(id, tagsFilter)
    }

    fun onSubscribing(callback: (SubscribingEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onSubscribing = callback }
    }

    fun onSubscribed(callback: (SubscribedEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onSubscribed = callback }
    }

    fun onUnsubscribed(callback: (UnsubscribedEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onUnsubscribed = callback }
    }

    fun onPublication(callback: (PublicationEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onPublication = callback }
    }

    fun onJoin(callback: (JoinEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onJoin = callback }
    }

    fun onLeave(callback: (LeaveEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onLeave = callback }
    }

    fun onError(callback: (SubscriptionErrorEvent) -> Unit) {
        client.setSubscriptionCallback(id) { it.onError = callback }
    }
}
