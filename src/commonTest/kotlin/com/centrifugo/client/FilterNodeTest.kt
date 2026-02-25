package com.centrifugo.client

import com.centrifugo.client.protocol.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class FilterNodeTest {

    // --- FilterNode data class ---

    @Test
    fun defaultFilterNode() {
        val node = FilterNode()
        assertEquals("", node.op)
        assertEquals("", node.key)
        assertEquals("", node.cmp)
        assertEquals("", node.`val`)
        assertTrue(node.vals.isEmpty())
        assertTrue(node.nodes.isEmpty())
    }

    @Test
    fun leafFilterNode() {
        val node = FilterNode(key = "ticker", cmp = "eq", `val` = "BTC")
        assertEquals("ticker", node.key)
        assertEquals("eq", node.cmp)
        assertEquals("BTC", node.`val`)
        assertEquals("", node.op)
    }

    @Test
    fun inFilterNode() {
        val node = FilterNode(key = "ticker", cmp = "in", vals = listOf("BTC", "ETH", "SOL"))
        assertEquals("in", node.cmp)
        assertEquals(3, node.vals.size)
        assertEquals("BTC", node.vals[0])
        assertEquals("SOL", node.vals[2])
    }

    @Test
    fun logicalAndFilterNode() {
        val node = FilterNode(
            op = "and",
            nodes = listOf(
                FilterNode(key = "ticker", cmp = "eq", `val` = "BTC"),
                FilterNode(key = "price", cmp = "gt", `val` = "50000"),
            )
        )
        assertEquals("and", node.op)
        assertEquals(2, node.nodes.size)
        assertEquals("ticker", node.nodes[0].key)
        assertEquals("price", node.nodes[1].key)
    }

    @Test
    fun nestedFilterNodes() {
        val node = FilterNode(
            op = "or",
            nodes = listOf(
                FilterNode(
                    op = "and",
                    nodes = listOf(
                        FilterNode(key = "a", cmp = "eq", `val` = "1"),
                        FilterNode(key = "b", cmp = "eq", `val` = "2"),
                    )
                ),
                FilterNode(key = "c", cmp = "ex"),
            )
        )
        assertEquals("or", node.op)
        assertEquals(2, node.nodes.size)
        assertEquals("and", node.nodes[0].op)
        assertEquals(2, node.nodes[0].nodes.size)
    }

    @Test
    fun notFilterNode() {
        val node = FilterNode(
            op = "not",
            nodes = listOf(
                FilterNode(key = "status", cmp = "eq", `val` = "hidden"),
            )
        )
        assertEquals("not", node.op)
        assertEquals(1, node.nodes.size)
    }

    // --- SubscribeRequest with tf ---

    @Test
    fun subscribeRequestWithTagsFilter() {
        val filter = FilterNode(key = "ticker", cmp = "eq", `val` = "BTC")
        val req = SubscribeRequest(channel = "prices", tf = filter)
        assertNotNull(req.tf)
        assertEquals("ticker", req.tf!!.key)
    }

    @Test
    fun subscribeRequestWithoutTagsFilter() {
        val req = SubscribeRequest(channel = "chat")
        assertNull(req.tf)
    }

    // --- JSON encode/decode FilterNode ---

    @Test
    fun jsonCodecEncodesSubscribeWithFilter() {
        val codec = JsonCodec()
        val filter = FilterNode(key = "type", cmp = "eq", `val` = "trade")
        val cmd = RawCommand(
            id = 1,
            subscribe = SubscribeRequest(channel = "market", tf = filter),
        )
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Text)
        val text = (frame as FrameData.Text).text
        assertTrue(text.contains("\"tf\""))
        assertTrue(text.contains("\"type\""))
        assertTrue(text.contains("\"trade\""))
    }

    @Test
    fun jsonCodecEncodesSubscribeWithoutFilter() {
        val codec = JsonCodec()
        val cmd = RawCommand(
            id = 1,
            subscribe = SubscribeRequest(channel = "chat"),
        )
        val frame = codec.encodeCommands(listOf(cmd))
        val text = (frame as FrameData.Text).text
        // tf should not appear because encodeDefaults=false and tf is null
        assertTrue(!text.contains("\"tf\""))
    }

    // --- Protobuf encode FilterNode ---

    @Test
    fun protobufCodecEncodesSubscribeWithFilter() {
        val codec = ProtobufCodec()
        val filter = FilterNode(key = "ticker", cmp = "in", vals = listOf("BTC", "ETH"))
        val cmd = RawCommand(
            id = 1,
            subscribe = SubscribeRequest(channel = "prices", tf = filter),
        )
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Binary)
        assertTrue((frame as FrameData.Binary).data.isNotEmpty())
    }

    @Test
    fun protobufCodecEncodesNestedFilter() {
        val codec = ProtobufCodec()
        val filter = FilterNode(
            op = "and",
            nodes = listOf(
                FilterNode(key = "a", cmp = "eq", `val` = "1"),
                FilterNode(op = "not", nodes = listOf(
                    FilterNode(key = "b", cmp = "ex"),
                )),
            ),
        )
        val cmd = RawCommand(
            id = 1,
            subscribe = SubscribeRequest(channel = "ch", tf = filter),
        )
        val frame = codec.encodeCommands(listOf(cmd))
        assertTrue(frame is FrameData.Binary)
    }

    // --- Command with tf ---

    @Test
    fun commandSubscribeWithFilter() {
        val filter = FilterNode(key = "k", cmp = "eq", `val` = "v")
        val cmd = Command.Subscribe(SubscribeRequest(channel = "ch", tf = filter))
        val raw = cmd.toRawCommand(1)
        assertNotNull(raw.subscribe)
        assertNotNull(raw.subscribe!!.tf)
        assertEquals("k", raw.subscribe!!.tf!!.key)
    }
}
