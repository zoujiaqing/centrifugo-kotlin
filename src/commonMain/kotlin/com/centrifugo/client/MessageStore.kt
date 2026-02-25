package com.centrifugo.client

import com.centrifugo.client.protocol.Command
import com.centrifugo.client.protocol.Reply
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.channels.Channel
import kotlin.time.Duration
import kotlin.time.TimeSource

internal class MessageStoreItem(
    val command: Command,
    val reply: CompletableDeferred<Result<Reply>>,
    val deadline: TimeSource.Monotonic.ValueTimeMark,
) {
    fun isExpired(now: TimeSource.Monotonic.ValueTimeMark): Boolean {
        return now >= deadline
    }
}

internal class MessageStore(
    private val timeout: Duration,
) {
    val activity = Channel<Unit>(Channel.CONFLATED)
    private val messages = ArrayDeque<MessageStoreItem>()

    fun send(command: Command): CompletableDeferred<Result<Reply>> {
        val deferred = CompletableDeferred<Result<Reply>>()
        val now = TimeSource.Monotonic.markNow()
        val deadline = now + timeout
        messages.addLast(MessageStoreItem(command, deferred, deadline))

        // Expire old messages
        while (messages.isNotEmpty()) {
            val item = messages.first()
            if (item.isExpired(now)) {
                messages.removeFirst()
                item.reply.complete(Result.failure(RequestError.Timeout))
            } else {
                break
            }
        }

        activity.trySend(Unit)
        return deferred
    }

    fun getNext(now: TimeSource.Monotonic.ValueTimeMark): MessageStoreItem? {
        while (messages.isNotEmpty()) {
            val item = messages.removeFirst()
            if (!item.isExpired(now)) {
                return item
            }
            item.reply.complete(Result.failure(RequestError.Timeout))
        }
        return null
    }

    fun resetActivity(): Channel<Unit> {
        val newChannel = Channel<Unit>(Channel.CONFLATED)
        // We don't reassign activity field; instead the caller uses the returned channel
        return newChannel
    }
}
