package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class CommandTest {

    @Test
    fun connectCommandMapsCorrectly() {
        val cmd = Command.Connect(ConnectRequest(token = "jwt"))
        val raw = cmd.toRawCommand(1)
        assertEquals(1, raw.id)
        assertNotNull(raw.connect)
        assertEquals("jwt", raw.connect!!.token)
        assertNull(raw.subscribe)
        assertNull(raw.publish)
    }

    @Test
    fun subscribeCommandMapsCorrectly() {
        val cmd = Command.Subscribe(SubscribeRequest(channel = "chat"))
        val raw = cmd.toRawCommand(2)
        assertEquals(2, raw.id)
        assertNotNull(raw.subscribe)
        assertEquals("chat", raw.subscribe!!.channel)
        assertNull(raw.connect)
    }

    @Test
    fun unsubscribeCommandMapsCorrectly() {
        val cmd = Command.Unsubscribe(UnsubscribeRequest(channel = "ch"))
        val raw = cmd.toRawCommand(3)
        assertNotNull(raw.unsubscribe)
        assertEquals("ch", raw.unsubscribe!!.channel)
    }

    @Test
    fun publishCommandMapsCorrectly() {
        val cmd = Command.Publish(PublishRequest(channel = "news"))
        val raw = cmd.toRawCommand(4)
        assertNotNull(raw.publish)
        assertEquals("news", raw.publish!!.channel)
    }

    @Test
    fun presenceCommandMapsCorrectly() {
        val cmd = Command.Presence(PresenceRequest(channel = "room"))
        val raw = cmd.toRawCommand(5)
        assertNotNull(raw.presence)
        assertEquals("room", raw.presence!!.channel)
    }

    @Test
    fun presenceStatsCommandMapsCorrectly() {
        val cmd = Command.PresenceStats(PresenceStatsRequest(channel = "room"))
        val raw = cmd.toRawCommand(6)
        assertNotNull(raw.presenceStats)
        assertEquals("room", raw.presenceStats!!.channel)
    }

    @Test
    fun historyCommandMapsCorrectly() {
        val cmd = Command.History(HistoryRequest(channel = "log", limit = 10, reverse = true))
        val raw = cmd.toRawCommand(7)
        assertNotNull(raw.history)
        assertEquals("log", raw.history!!.channel)
        assertEquals(10, raw.history!!.limit)
        assertEquals(true, raw.history!!.reverse)
    }

    @Test
    fun pingCommandMapsCorrectly() {
        val cmd = Command.Ping(PingRequest())
        val raw = cmd.toRawCommand(8)
        assertNotNull(raw.ping)
    }

    @Test
    fun sendCommandMapsCorrectly() {
        val cmd = Command.Send(SendRequest())
        val raw = cmd.toRawCommand(9)
        assertNotNull(raw.send)
    }

    @Test
    fun rpcCommandMapsCorrectly() {
        val cmd = Command.Rpc(RpcRequest(method = "doSomething"))
        val raw = cmd.toRawCommand(10)
        assertNotNull(raw.rpc)
        assertEquals("doSomething", raw.rpc!!.method)
    }

    @Test
    fun refreshCommandMapsCorrectly() {
        val cmd = Command.Refresh(RefreshRequest(token = "newToken"))
        val raw = cmd.toRawCommand(11)
        assertNotNull(raw.refresh)
        assertEquals("newToken", raw.refresh!!.token)
    }

    @Test
    fun subRefreshCommandMapsCorrectly() {
        val cmd = Command.SubRefresh(SubRefreshRequest(channel = "ch", token = "t"))
        val raw = cmd.toRawCommand(12)
        assertNotNull(raw.subRefresh)
        assertEquals("ch", raw.subRefresh!!.channel)
        assertEquals("t", raw.subRefresh!!.token)
    }

    @Test
    fun emptyCommandAllFieldsNull() {
        val cmd = Command.Empty
        val raw = cmd.toRawCommand(0)
        assertEquals(0, raw.id)
        assertNull(raw.connect)
        assertNull(raw.subscribe)
        assertNull(raw.unsubscribe)
        assertNull(raw.publish)
        assertNull(raw.presence)
        assertNull(raw.presenceStats)
        assertNull(raw.history)
        assertNull(raw.ping)
        assertNull(raw.send)
        assertNull(raw.rpc)
        assertNull(raw.refresh)
        assertNull(raw.subRefresh)
    }

    @Test
    fun idIsSetCorrectly() {
        val cmd = Command.Connect(ConnectRequest())
        assertEquals(42, cmd.toRawCommand(42).id)
        assertEquals(0, cmd.toRawCommand(0).id)
        assertEquals(999, cmd.toRawCommand(999).id)
    }
}
