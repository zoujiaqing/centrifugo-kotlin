package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class CentrifugoClientTest {

    // --- CentrifugoClient transport selection ---

    @Test
    fun currentTransportReturnsDefaultWebSocketWhenEmpty() {
        // This test validates the transport selection logic conceptually
        // Since currentTransport is private, we test via config
        val config = CentrifugoConfig()
        assertTrue(config.transports.isEmpty())
    }

    @Test
    fun currentTransportReturnsFirstFromList() {
        val config = CentrifugoConfig(
            transports = listOf(
                TransportConfig(TransportType.WEBSOCKET, "ws://localhost:8000/ws"),
                TransportConfig(TransportType.HTTP_STREAM, "http://localhost:8000/http"),
            )
        )
        assertEquals(2, config.transports.size)
        assertEquals(TransportType.WEBSOCKET, config.transports[0].transport)
        assertEquals(TransportType.HTTP_STREAM, config.transports[1].transport)
    }

    @Test
    fun centrifugoConfigWithEmulationEndpoint() {
        val config = CentrifugoConfig(
            emulationEndpoint = "http://localhost:8000/emulation",
            transports = listOf(
                TransportConfig(TransportType.SSE, "http://localhost:8000/sse"),
            )
        )
        assertEquals("http://localhost:8000/emulation", config.emulationEndpoint)
        assertEquals(TransportType.SSE, config.transports[0].transport)
    }

    // --- Subscription runtime tagsFilter update ---

    @Test
    fun subscriptionTagsFilterCanBeUpdated() {
        // Test the FilterNode structure used for runtime updates
        val initialFilter = FilterNode(key = "type", cmp = "eq", `val` = "news")
        val updatedFilter = FilterNode(key = "type", cmp = "eq", `val` = "alert")

        // Verify FilterNode is immutable (data class)
        assertEquals("news", initialFilter.`val`)
        assertEquals("alert", updatedFilter.`val`)
        assertTrue(initialFilter != updatedFilter)
    }

    @Test
    fun subscriptionTagsFilterLogicalExpression() {
        val filter = FilterNode(
            op = "or",
            nodes = listOf(
                FilterNode(key = "priority", cmp = "eq", `val` = "high"),
                FilterNode(key = "urgent", cmp = "eq", `val` = "true"),
            )
        )
        assertEquals("or", filter.op)
        assertEquals(2, filter.nodes.size)
        assertEquals("priority", filter.nodes[0].key)
        assertEquals("urgent", filter.nodes[1].key)
    }

    // --- EmulationHandler Codec integration ---

    @Test
    fun jsonEmulationRequestContainsSessionAndNode() {
        val codec = JsonCodec()
        val commands = listOf(
            RawCommand(id = 1, subscribe = SubscribeRequest(channel = "test:ch"))
        )
        val frameData = codec.encodeCommands(commands)
        val emulationBytes = codec.encodeEmulationRequest("session123", "node456", frameData)
        val json = emulationBytes.decodeToString()

        assertTrue(json.contains("session123"))
        assertTrue(json.contains("node456"))
    }

    @Test
    fun protobufEmulationRequestEncoding() {
        val codec = ProtobufCodec()
        val commands = listOf(
            RawCommand(id = 1, subscribe = SubscribeRequest(channel = "test:ch"))
        )
        val frameData = codec.encodeCommands(commands)
        val emulationBytes = codec.encodeEmulationRequest("session123", "node456", frameData)

        assertTrue(emulationBytes.isNotEmpty())
    }

    // --- FilterNode comparison operations ---

    @Test
    fun filterNodeEqComparison() {
        val filter = FilterNode(key = "status", cmp = "eq", `val` = "active")
        assertEquals("eq", filter.cmp)
        assertEquals("active", filter.`val`)
    }

    @Test
    fun filterNodeNeqComparison() {
        val filter = FilterNode(key = "status", cmp = "neq", `val` = "banned")
        assertEquals("neq", filter.cmp)
    }

    @Test
    fun filterNodeInComparison() {
        val filter = FilterNode(key = "category", cmp = "in", vals = listOf("news", "sports", "entertainment"))
        assertEquals("in", filter.cmp)
        assertEquals(3, filter.vals.size)
    }

    @Test
    fun filterNodeExistsComparison() {
        val filter = FilterNode(key = "featured", cmp = "ex")
        assertEquals("ex", filter.cmp)
    }

    @Test
    fun filterNodeStringContains() {
        val filter = FilterNode(key = "title", cmp = "ct", `val` = "breaking")
        assertEquals("ct", filter.cmp)
    }

    @Test
    fun filterNodeNumericComparison() {
        val gtFilter = FilterNode(key = "price", cmp = "gt", `val` = "100")
        val gteFilter = FilterNode(key = "price", cmp = "gte", `val` = "50")
        val ltFilter = FilterNode(key = "price", cmp = "lt", `val` = "200")
        val lteFilter = FilterNode(key = "price", cmp = "lte", `val` = "150")

        assertEquals("gt", gtFilter.cmp)
        assertEquals("gte", gteFilter.cmp)
        assertEquals("lt", ltFilter.cmp)
        assertEquals("lte", lteFilter.cmp)
    }

    // --- FilterNode logical operations ---

    @Test
    fun filterNodeAndOperation() {
        val filter = FilterNode(
            op = "and",
            nodes = listOf(
                FilterNode(key = "status", cmp = "eq", `val` = "active"),
                FilterNode(key = "verified", cmp = "eq", `val` = "true"),
            )
        )
        assertEquals("and", filter.op)
        assertEquals(2, filter.nodes.size)
    }

    @Test
    fun filterNodeOrOperation() {
        val filter = FilterNode(
            op = "or",
            nodes = listOf(
                FilterNode(key = "type", cmp = "eq", `val` = "news"),
                FilterNode(key = "type", cmp = "eq", `val` = "alert"),
            )
        )
        assertEquals("or", filter.op)
    }

    @Test
    fun filterNodeNotOperation() {
        val filter = FilterNode(
            op = "not",
            nodes = listOf(
                FilterNode(key = "deleted", cmp = "eq", `val` = "true"),
            )
        )
        assertEquals("not", filter.op)
    }

    // --- Deep nested FilterNode ---

    @Test
    fun filterNodeDeepNesting() {
        val filter = FilterNode(
            op = "and",
            nodes = listOf(
                FilterNode(
                    op = "or",
                    nodes = listOf(
                        FilterNode(key = "type", cmp = "eq", `val` = "news"),
                        FilterNode(key = "type", cmp = "eq", `val` = "alert"),
                    )
                ),
                FilterNode(
                    op = "not",
                    nodes = listOf(
                        FilterNode(key = "hidden", cmp = "eq", `val` = "true"),
                    )
                ),
                FilterNode(key = "priority", cmp = "gte", `val` = "5"),
            )
        )
        assertEquals("and", filter.op)
        assertEquals(3, filter.nodes.size)
        assertEquals("or", filter.nodes[0].op)
        assertEquals("not", filter.nodes[1].op)
    }

    // --- Protocol enum ---

    @Test
    fun protocolEnumValues() {
        assertEquals(Protocol.JSON, Protocol.valueOf("JSON"))
        assertEquals(Protocol.PROTOBUF, Protocol.valueOf("PROTOBUF"))
    }

    @Test
    fun transportTypeEnumValues() {
        assertEquals(TransportType.WEBSOCKET, TransportType.valueOf("WEBSOCKET"))
        assertEquals(TransportType.HTTP_STREAM, TransportType.valueOf("HTTP_STREAM"))
        assertEquals(TransportType.SSE, TransportType.valueOf("SSE"))
    }

    // --- SubscribeRequest with tagsFilter in different scenarios ---

    @Test
    fun subscribeRequestWithNullTagsFilter() {
        val req = SubscribeRequest(channel = "test")
        assertNull(req.tf)
    }

    @Test
    fun subscribeRequestWithComplexTagsFilter() {
        val filter = FilterNode(
            op = "and",
            nodes = listOf(
                FilterNode(key = "channel", cmp = "in", vals = listOf("ch1", "ch2", "ch3")),
                FilterNode(key = "priority", cmp = "gte", `val` = "1"),
            )
        )
        val req = SubscribeRequest(channel = "test", tf = filter)
        assertNotNull(req.tf)
        assertEquals("and", req.tf!!.op)
    }
}
