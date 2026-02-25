package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class JsonCodecTest {

    private val codec = JsonCodec()

    @Test
    fun encodeSingleCommand() {
        val cmd = RawCommand(id = 1, connect = ConnectRequest(token = "abc"))
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Text)
        val text = (frame as FrameData.Text).text
        assertTrue(text.contains("\"id\":1"))
        assertTrue(text.contains("\"token\":\"abc\""))
    }

    @Test
    fun encodeMultipleCommandsNewlineSeparated() {
        val cmd1 = RawCommand(id = 1, connect = ConnectRequest(token = "a"))
        val cmd2 = RawCommand(id = 2, subscribe = SubscribeRequest(channel = "ch1"))
        val frame = codec.encodeCommands(listOf(cmd1, cmd2))
        val text = (frame as FrameData.Text).text
        val lines = text.split("\n")
        assertEquals(2, lines.size)
        assertTrue(lines[0].contains("\"token\":\"a\""))
        assertTrue(lines[1].contains("\"channel\":\"ch1\""))
    }

    @Test
    fun decodeSingleReply() {
        val json = """{"id":1,"connect":{"client":"abc123","version":"1.0"}}"""
        val replies = codec.decodeReplies(FrameData.Text(json))
        assertEquals(1, replies.size)
        assertEquals(1, replies[0].id)
        assertNotNull(replies[0].connect)
        assertEquals("abc123", replies[0].connect!!.client)
    }

    @Test
    fun decodeMultipleReplies() {
        val json = """{"id":1,"connect":{"client":"a"}}
{"id":2,"subscribe":{"epoch":"e1"}}"""
        val replies = codec.decodeReplies(FrameData.Text(json))
        assertEquals(2, replies.size)
        assertNotNull(replies[0].connect)
        assertNotNull(replies[1].subscribe)
    }

    @Test
    fun decodeEmptyText() {
        val replies = codec.decodeReplies(FrameData.Text(""))
        assertTrue(replies.isEmpty())
    }

    @Test
    fun decodeBlankLines() {
        val replies = codec.decodeReplies(FrameData.Text("\n\n  \n"))
        assertTrue(replies.isEmpty())
    }

    @Test
    fun decodeReplyWithError() {
        val json = """{"id":1,"error":{"code":101,"message":"unauthorized","temporary":false}}"""
        val replies = codec.decodeReplies(FrameData.Text(json))
        assertEquals(1, replies.size)
        assertNotNull(replies[0].error)
        assertEquals(101, replies[0].error!!.code)
        assertEquals("unauthorized", replies[0].error!!.message)
    }

    @Test
    fun decodeReplyWithPush() {
        val json = """{"id":0,"push":{"channel":"chat","pub":{"data":{"text":"hello"}}}}"""
        val replies = codec.decodeReplies(FrameData.Text(json))
        assertEquals(1, replies.size)
        assertNotNull(replies[0].push)
        assertEquals("chat", replies[0].push!!.channel)
        assertNotNull(replies[0].push!!.publication)
    }

    @Test
    fun encodeEmptyCommand() {
        val cmd = RawCommand(id = 0)
        val frame = codec.encodeCommands(listOf(cmd))
        val text = (frame as FrameData.Text).text
        // Should produce valid JSON
        val replies = codec.decodeReplies(FrameData.Text(text))
        assertEquals(1, replies.size)
        assertEquals(0, replies[0].id)
    }

    @Test
    fun decodeMalformedJsonSkipped() {
        val json = """{"id":1,"connect":{"client":"ok"}}
not-valid-json
{"id":2,"subscribe":{"epoch":"e1"}}"""
        val replies = codec.decodeReplies(FrameData.Text(json))
        assertEquals(2, replies.size)
    }

    @Test
    fun encodedCommandContainsData() {
        val data = JsonPrimitive("test-data")
        val cmd = RawCommand(id = 5, publish = PublishRequest(channel = "ch", data = data))
        val frame = codec.encodeCommands(listOf(cmd))
        val text = (frame as FrameData.Text).text
        assertTrue(text.contains("\"channel\":\"ch\""))
        assertTrue(text.contains("\"test-data\""))
    }

    @Test
    fun decodeBinaryFrameAsText() {
        val json = """{"id":3,"connect":{"client":"bin"}}"""
        val frame = FrameData.Binary(json.encodeToByteArray())
        val replies = codec.decodeReplies(frame)
        assertEquals(1, replies.size)
        assertEquals("bin", replies[0].connect!!.client)
    }
}
