package com.centrifugo.client

import com.centrifugo.client.protocol.*
import com.centrifugo.client.protocol.PresenceResult as ProtoPresenceResult
import com.centrifugo.client.protocol.PresenceStatsResult as ProtoPresenceStatsResult
import com.centrifugo.client.protocol.HistoryResult as ProtoHistoryResult
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ReplyTest {

    @Test
    fun fromRawReplyWithError() {
        val raw = RawReply(id = 1, error = Error(code = 101, message = "unauthorized"))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Error)
        assertEquals(101, (reply as Reply.Error).error.code)
    }

    @Test
    fun fromRawReplyWithConnect() {
        val raw = RawReply(id = 1, connect = ConnectResult(client = "abc", version = "1.0"))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Connect)
        assertEquals("abc", (reply as Reply.Connect).result.client)
    }

    @Test
    fun fromRawReplyWithSubscribe() {
        val raw = RawReply(id = 2, subscribe = SubscribeResult(epoch = "e1", recoverable = true))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Subscribe)
        assertEquals("e1", (reply as Reply.Subscribe).result.epoch)
    }

    @Test
    fun fromRawReplyWithPush() {
        val push = RawPush(channel = "chat", publication = Publication())
        val raw = RawReply(id = 0, push = push)
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Push)
    }

    @Test
    fun fromRawReplyEmpty() {
        val raw = RawReply(id = 0)
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Empty)
    }

    @Test
    fun errorTakesPriorityOverConnect() {
        val raw = RawReply(
            id = 1,
            error = Error(code = 100, message = "internal"),
            connect = ConnectResult(client = "abc"),
        )
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Error)
    }

    @Test
    fun fromRawReplyWithUnsubscribe() {
        val raw = RawReply(id = 3, unsubscribe = UnsubscribeResult())
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Unsubscribe)
    }

    @Test
    fun fromRawReplyWithPublish() {
        val raw = RawReply(id = 4, publish = PublishResult())
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Publish)
    }

    @Test
    fun fromRawReplyWithPresence() {
        val raw = RawReply(id = 5, presence = ProtoPresenceResult(presence = mapOf("u1" to ClientInfo(user = "u1"))))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Presence)
    }

    @Test
    fun fromRawReplyWithPresenceStats() {
        val raw = RawReply(id = 6, presenceStats = ProtoPresenceStatsResult(numClients = 5, numUsers = 3))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.PresenceStats)
        assertEquals(5, (reply as Reply.PresenceStats).result.numClients)
    }

    @Test
    fun fromRawReplyWithHistory() {
        val raw = RawReply(id = 7, history = ProtoHistoryResult(epoch = "e2", offset = 10))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.History)
        assertEquals("e2", (reply as Reply.History).result.epoch)
    }

    @Test
    fun fromRawReplyWithRpc() {
        val raw = RawReply(id = 8, rpc = RpcResult())
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Rpc)
    }

    @Test
    fun fromRawReplyWithRefresh() {
        val raw = RawReply(id = 9, refresh = RefreshResult(expires = true, ttl = 300))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.Refresh)
        assertEquals(300, (reply as Reply.Refresh).result.ttl)
    }

    @Test
    fun fromRawReplyWithSubRefresh() {
        val raw = RawReply(id = 10, subRefresh = SubRefreshResult(expires = true, ttl = 60))
        val reply = Reply.fromRawReply(raw)
        assertTrue(reply is Reply.SubRefresh)
    }

    // --- PushMessage tests ---

    @Test
    fun pushMessageWithPublication() {
        val raw = RawPush(channel = "chat", publication = Publication())
        val push = PushMessage.fromRawPush(raw)
        assertEquals("chat", push.channel)
        assertTrue(push.data is PushData.Publication)
    }

    @Test
    fun pushMessageWithJoin() {
        val raw = RawPush(channel = "room", join = Join(info = ClientInfo(user = "u1", client = "c1")))
        val push = PushMessage.fromRawPush(raw)
        assertEquals("room", push.channel)
        assertTrue(push.data is PushData.Join)
    }

    @Test
    fun pushMessageWithLeave() {
        val raw = RawPush(channel = "room", leave = Leave(info = ClientInfo(user = "u2")))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Leave)
    }

    @Test
    fun pushMessageWithUnsubscribe() {
        val raw = RawPush(channel = "ch", unsubscribe = Unsubscribe(code = 100, reason = "kicked"))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Unsubscribe)
    }

    @Test
    fun pushMessageWithMessage() {
        val raw = RawPush(channel = "ch", message = Message())
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Message)
    }

    @Test
    fun pushMessageWithSubscribe() {
        val raw = RawPush(channel = "ch", subscribe = Subscribe(recoverable = true))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Subscribe)
    }

    @Test
    fun pushMessageWithConnect() {
        val raw = RawPush(channel = "", connect = Connect(client = "c1"))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Connect)
    }

    @Test
    fun pushMessageWithDisconnect() {
        val raw = RawPush(channel = "", disconnect = Disconnect(code = 3001, reason = "shutdown"))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Disconnect)
    }

    @Test
    fun pushMessageWithRefresh() {
        val raw = RawPush(channel = "", refresh = Refresh(expires = true, ttl = 100))
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Refresh)
    }

    @Test
    fun pushMessageEmpty() {
        val raw = RawPush(channel = "ch")
        val push = PushMessage.fromRawPush(raw)
        assertTrue(push.data is PushData.Empty)
    }
}
