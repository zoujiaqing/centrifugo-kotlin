package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlinx.serialization.json.JsonPrimitive
import kotlin.test.Test
import kotlin.test.assertTrue

class CodecInteropTest {

    private val jsonCodec = JsonCodec()
    private val protobufCodec = ProtobufCodec()

    @Test
    fun bothCodecsEncodeConnectWithoutError() {
        val cmd = RawCommand(id = 1, connect = ConnectRequest(
            token = "test-token",
            name = "sdk",
            version = "1.0",
        ))
        val jsonFrame = jsonCodec.encodeCommands(listOf(cmd))
        val protoFrame = protobufCodec.encodeCommands(listOf(cmd))

        assertTrue(jsonFrame is FrameData.Text)
        assertTrue(protoFrame is FrameData.Binary)
    }

    @Test
    fun bothCodecsEncodeSubscribeWithoutError() {
        val cmd = RawCommand(id = 2, subscribe = SubscribeRequest(
            channel = "test:channel",
            token = "sub-token",
            positioned = true,
            recoverable = true,
        ))
        jsonCodec.encodeCommands(listOf(cmd))
        protobufCodec.encodeCommands(listOf(cmd))
    }

    @Test
    fun bothCodecsEncodePublishWithData() {
        val data = JsonPrimitive("hello world")
        val cmd = RawCommand(id = 3, publish = PublishRequest(
            channel = "news",
            data = data,
        ))
        jsonCodec.encodeCommands(listOf(cmd))
        protobufCodec.encodeCommands(listOf(cmd))
    }

    @Test
    fun bothCodecsEncodeRpc() {
        val cmd = RawCommand(id = 4, rpc = RpcRequest(method = "calculate", data = JsonPrimitive(42)))
        jsonCodec.encodeCommands(listOf(cmd))
        protobufCodec.encodeCommands(listOf(cmd))
    }

    @Test
    fun bothCodecsEncodeHistory() {
        val cmd = RawCommand(id = 5, history = HistoryRequest(
            channel = "logs",
            limit = 50,
            since = StreamPosition(offset = 100, epoch = "e1"),
            reverse = true,
        ))
        jsonCodec.encodeCommands(listOf(cmd))
        protobufCodec.encodeCommands(listOf(cmd))
    }

    @Test
    fun bothCodecsHandleEmptyList() {
        val jsonFrame = jsonCodec.encodeCommands(emptyList())
        val protoFrame = protobufCodec.encodeCommands(emptyList())

        // JSON encodes empty list as empty string
        assertTrue(jsonFrame is FrameData.Text)
        // Protobuf encodes empty list as empty bytes
        assertTrue(protoFrame is FrameData.Binary)
    }

    @Test
    fun bothCodecsEncodeBatchOfMixedCommands() {
        val commands = listOf(
            RawCommand(id = 1, connect = ConnectRequest(token = "t")),
            RawCommand(id = 2, subscribe = SubscribeRequest(channel = "ch")),
            RawCommand(id = 3, publish = PublishRequest(channel = "ch", data = JsonPrimitive("msg"))),
            RawCommand(id = 4, presence = PresenceRequest(channel = "ch")),
            RawCommand(id = 5, history = HistoryRequest(channel = "ch", limit = 10)),
        )

        val jsonFrame = jsonCodec.encodeCommands(commands)
        val protoFrame = protobufCodec.encodeCommands(commands)

        assertTrue(jsonFrame is FrameData.Text)
        assertTrue(protoFrame is FrameData.Binary)
        assertTrue((protoFrame as FrameData.Binary).data.isNotEmpty())
    }

    @Test
    fun jsonRoundtripConnectReply() {
        val replyJson = """{"id":1,"connect":{"client":"abc","version":"2.0","ping":25,"pong":true}}"""
        val replies = jsonCodec.decodeReplies(FrameData.Text(replyJson))
        assertTrue(replies.size == 1)
        val reply = Reply.fromRawReply(replies[0])
        assertTrue(reply is Reply.Connect)
        val result = (reply as Reply.Connect).result
        assertTrue(result.client == "abc")
        assertTrue(result.ping == 25)
        assertTrue(result.pong)
    }

    @Test
    fun jsonRoundtripErrorReply() {
        val replyJson = """{"id":1,"error":{"code":103,"message":"permission denied","temporary":false}}"""
        val replies = jsonCodec.decodeReplies(FrameData.Text(replyJson))
        val reply = Reply.fromRawReply(replies[0])
        assertTrue(reply is Reply.Error)
        assertTrue((reply as Reply.Error).error.code == 103)
    }

    @Test
    fun jsonRoundtripPushWithPublication() {
        val replyJson = """{"id":0,"push":{"channel":"news","pub":{"data":{"text":"breaking"},"offset":42}}}"""
        val replies = jsonCodec.decodeReplies(FrameData.Text(replyJson))
        val reply = Reply.fromRawReply(replies[0])
        assertTrue(reply is Reply.Push)
        val push = (reply as Reply.Push).push
        assertTrue(push.channel == "news")
        assertTrue(push.data is PushData.Publication)
        val pub = (push.data as PushData.Publication).publication
        assertTrue(pub.offset == 42L)
    }
}
