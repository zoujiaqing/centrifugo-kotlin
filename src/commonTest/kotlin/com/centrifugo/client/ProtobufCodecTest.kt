package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ProtobufCodecTest {

    private val codec = ProtobufCodec()

    @Test
    fun encodeSingleCommandReturnsBinary() {
        val cmd = RawCommand(id = 1, connect = ConnectRequest(token = "test"))
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Binary)
        val bytes = (frame as FrameData.Binary).data
        assertTrue(bytes.isNotEmpty())
    }

    @Test
    fun encodeEmptyListReturnsEmptyBinary() {
        val frame = codec.encodeCommands(emptyList())
        assertTrue(frame is FrameData.Binary)
        val bytes = (frame as FrameData.Binary).data
        assertTrue(bytes.isEmpty())
    }

    @Test
    fun decodeEmptyBytesReturnsEmptyList() {
        val replies = codec.decodeReplies(FrameData.Binary(byteArrayOf()))
        assertTrue(replies.isEmpty())
    }

    @Test
    fun decodeMalformedDataDoesNotThrow() {
        val garbage = byteArrayOf(0x05, 0xFF.toByte(), 0xAA.toByte(), 0x00, 0x01)
        val replies = codec.decodeReplies(FrameData.Binary(garbage))
        // Should not throw, may return empty or partial results
        assertTrue(replies.size >= 0)
    }

    @Test
    fun encodeMultipleCommands() {
        val cmd1 = RawCommand(id = 1, connect = ConnectRequest(token = "a"))
        val cmd2 = RawCommand(id = 2, subscribe = SubscribeRequest(channel = "ch"))
        val frame = codec.encodeCommands(listOf(cmd1, cmd2))
        assertTrue(frame is FrameData.Binary)
        val bytes = (frame as FrameData.Binary).data
        assertTrue(bytes.size > 2) // At least varint + data for each command
    }

    @Test
    fun encodeEmptyCommandProducesValidBytes() {
        val cmd = RawCommand(id = 0)
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Binary)
        val bytes = (frame as FrameData.Binary).data
        assertTrue(bytes.isNotEmpty())
    }

    @Test
    fun encodeAllCommandTypes() {
        val commands = listOf(
            RawCommand(id = 1, connect = ConnectRequest(token = "t")),
            RawCommand(id = 2, subscribe = SubscribeRequest(channel = "c")),
            RawCommand(id = 3, unsubscribe = UnsubscribeRequest(channel = "c")),
            RawCommand(id = 4, publish = PublishRequest(channel = "c")),
            RawCommand(id = 5, presence = PresenceRequest(channel = "c")),
            RawCommand(id = 6, presenceStats = PresenceStatsRequest(channel = "c")),
            RawCommand(id = 7, history = HistoryRequest(channel = "c")),
            RawCommand(id = 8, ping = PingRequest()),
            RawCommand(id = 9, send = SendRequest()),
            RawCommand(id = 10, rpc = RpcRequest(method = "m")),
            RawCommand(id = 11, refresh = RefreshRequest(token = "t")),
            RawCommand(id = 12, subRefresh = SubRefreshRequest(channel = "c", token = "t")),
        )
        val frame = codec.encodeCommands(commands)
        assertTrue(frame is FrameData.Binary)
        assertTrue((frame as FrameData.Binary).data.isNotEmpty())
    }
}
