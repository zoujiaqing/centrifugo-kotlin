package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlin.test.Test
import kotlin.test.assertTrue

class EmulationCodecTest {

    // --- JsonCodec.encodeEmulationRequest ---

    @Test
    fun jsonEncodeEmulationRequest() {
        val codec = JsonCodec()
        val frameData = FrameData.Text("""{"id":1,"subscribe":{"channel":"ch"}}""")
        val result = codec.encodeEmulationRequest("sess1", "node1", frameData)
        val json = result.decodeToString()
        assertTrue(json.contains("\"session\":\"sess1\""))
        assertTrue(json.contains("\"node\":\"node1\""))
        assertTrue(json.contains("\"data\""))
    }

    @Test
    fun jsonEncodeEmulationRequestEmptyData() {
        val codec = JsonCodec()
        val frameData = FrameData.Text("")
        val result = codec.encodeEmulationRequest("s", "n", frameData)
        val json = result.decodeToString()
        assertTrue(json.contains("\"session\":\"s\""))
    }

    // --- ProtobufCodec.encodeEmulationRequest ---

    @Test
    fun protobufEncodeEmulationRequest() {
        val codec = ProtobufCodec()
        val cmd = RawCommand(id = 1, subscribe = SubscribeRequest(channel = "ch"))
        val frameData = codec.encodeCommands(listOf(cmd))
        val result = codec.encodeEmulationRequest("sess1", "node1", frameData)
        assertTrue(result.isNotEmpty())
    }

    @Test
    fun protobufEncodeEmulationRequestEmptyData() {
        val codec = ProtobufCodec()
        val frameData = FrameData.Binary(byteArrayOf())
        val result = codec.encodeEmulationRequest("s", "n", frameData)
        assertTrue(result.isNotEmpty())
    }

    // --- Config types ---

    @Test
    fun transportTypeEnum() {
        val ws = TransportType.WEBSOCKET
        val http = TransportType.HTTP_STREAM
        val sse = TransportType.SSE
        assertTrue(ws != http)
        assertTrue(http != sse)
    }

    @Test
    fun transportConfig() {
        val tc = TransportConfig(
            transport = TransportType.HTTP_STREAM,
            endpoint = "http://localhost:8000/connection/http_stream"
        )
        assertTrue(tc.transport == TransportType.HTTP_STREAM)
        assertTrue(tc.endpoint.contains("http_stream"))
    }

    @Test
    fun centrifugoConfigWithTransports() {
        val config = CentrifugoConfig(
            transports = listOf(
                TransportConfig(TransportType.WEBSOCKET, "ws://localhost:8000/connection/websocket"),
                TransportConfig(TransportType.HTTP_STREAM, "http://localhost:8000/connection/http_stream"),
                TransportConfig(TransportType.SSE, "http://localhost:8000/connection/sse"),
            ),
            emulationEndpoint = "http://localhost:8000/emulation",
        )
        assertTrue(config.transports.size == 3)
        assertTrue(config.emulationEndpoint.isNotEmpty())
    }

    @Test
    fun centrifugoConfigDefaultEmptyTransports() {
        val config = CentrifugoConfig()
        assertTrue(config.transports.isEmpty())
        assertTrue(config.emulationEndpoint.isEmpty())
    }

    // --- SubscriptionConfig with tagsFilter ---

    @Test
    fun subscriptionConfigDefaultTagsFilterNull() {
        val config = SubscriptionConfig()
        assertTrue(config.tagsFilter == null)
    }

    @Test
    fun subscriptionConfigWithTagsFilter() {
        val filter = FilterNode(key = "type", cmp = "eq", `val` = "news")
        val config = SubscriptionConfig(tagsFilter = filter)
        assertTrue(config.tagsFilter != null)
        assertTrue(config.tagsFilter!!.key == "type")
    }
}
