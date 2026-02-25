package com.centrifugo.client.protocol

import com.centrifugo.client.protocol.protobuf.Command as ProtoCommand
import com.centrifugo.client.protocol.protobuf.Reply as ProtoReply
import com.centrifugo.client.protocol.protobuf.Push as ProtoPush
import com.centrifugo.client.protocol.protobuf.ConnectRequest as ProtoConnectRequest
import com.centrifugo.client.protocol.protobuf.ConnectResult as ProtoConnectResult
import com.centrifugo.client.protocol.protobuf.SubscribeRequest as ProtoSubscribeRequest
import com.centrifugo.client.protocol.protobuf.SubscribeResult as ProtoSubscribeResult
import com.centrifugo.client.protocol.protobuf.UnsubscribeRequest as ProtoUnsubscribeRequest
import com.centrifugo.client.protocol.protobuf.UnsubscribeResult as ProtoUnsubscribeResult
import com.centrifugo.client.protocol.protobuf.PublishRequest as ProtoPublishRequest
import com.centrifugo.client.protocol.protobuf.PublishResult as ProtoPublishResult
import com.centrifugo.client.protocol.protobuf.PresenceRequest as ProtoPresenceRequest
import com.centrifugo.client.protocol.protobuf.PresenceResult as ProtoPresenceResult
import com.centrifugo.client.protocol.protobuf.PresenceStatsRequest as ProtoPresenceStatsRequest
import com.centrifugo.client.protocol.protobuf.PresenceStatsResult as ProtoPresenceStatsResult
import com.centrifugo.client.protocol.protobuf.HistoryRequest as ProtoHistoryRequest
import com.centrifugo.client.protocol.protobuf.HistoryResult as ProtoHistoryResult
import com.centrifugo.client.protocol.protobuf.PingRequest as ProtoPingRequest
import com.centrifugo.client.protocol.protobuf.PingResult as ProtoPingResult
import com.centrifugo.client.protocol.protobuf.RPCRequest as ProtoRPCRequest
import com.centrifugo.client.protocol.protobuf.RPCResult as ProtoRPCResult
import com.centrifugo.client.protocol.protobuf.RefreshRequest as ProtoRefreshRequest
import com.centrifugo.client.protocol.protobuf.RefreshResult as ProtoRefreshResult
import com.centrifugo.client.protocol.protobuf.SubRefreshRequest as ProtoSubRefreshRequest
import com.centrifugo.client.protocol.protobuf.SubRefreshResult as ProtoSubRefreshResult
import com.centrifugo.client.protocol.protobuf.SendRequest as ProtoSendRequest
import com.centrifugo.client.protocol.protobuf.StreamPosition as ProtoStreamPosition
import com.centrifugo.client.protocol.protobuf.Publication as ProtoPublication
import com.centrifugo.client.protocol.protobuf.ClientInfo as ProtoClientInfo
import com.centrifugo.client.protocol.protobuf.Join as ProtoJoin
import com.centrifugo.client.protocol.protobuf.Leave as ProtoLeave
import com.centrifugo.client.protocol.protobuf.Unsubscribe as ProtoUnsubscribe
import com.centrifugo.client.protocol.protobuf.Subscribe as ProtoSubscribe
import com.centrifugo.client.protocol.protobuf.Message as ProtoMessage
import com.centrifugo.client.protocol.protobuf.Connect as ProtoConnect
import com.centrifugo.client.protocol.protobuf.Disconnect as ProtoDisconnect
import com.centrifugo.client.protocol.protobuf.Refresh as ProtoRefresh
import com.centrifugo.client.protocol.protobuf.Error as ProtoError
import com.centrifugo.client.protocol.protobuf.FilterNode as ProtoFilterNode
import com.centrifugo.client.protocol.protobuf.EmulationRequest as ProtoEmulationRequest
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import pbandk.ByteArr
import pbandk.decodeFromByteArray
import pbandk.encodeToByteArray

class ProtobufCodec : Codec {

    override fun encodeEmulationRequest(session: String, node: String, data: FrameData): ByteArray {
        val dataBytes = when (data) {
            is FrameData.Text -> data.text.encodeToByteArray()
            is FrameData.Binary -> data.data
        }
        val emulationReq = ProtoEmulationRequest(
            session = session,
            node = node,
            data = ByteArr(dataBytes),
        )
        return emulationReq.encodeToByteArray()
    }

    override fun encodeCommands(commands: List<RawCommand>): FrameData {
        val output = mutableListOf<Byte>()
        for (cmd in commands) {
            val protoCmd = cmd.toProto()
            val bytes = protoCmd.encodeToByteArray()
            writeVarint(output, bytes.size)
            output.addAll(bytes.toList())
        }
        return FrameData.Binary(output.toByteArray())
    }

    override fun decodeReplies(data: FrameData): List<RawReply> {
        val bytes = when (data) {
            is FrameData.Binary -> data.data
            is FrameData.Text -> {
                val str: String = data.text
                str.encodeToByteArray()
            }
        }
        if (bytes.isEmpty()) return emptyList()

        val replies = mutableListOf<RawReply>()
        var offset = 0
        while (offset < bytes.size) {
            val (msgLen, newOffset) = readVarint(bytes, offset)
            offset = newOffset
            if (offset + msgLen > bytes.size) break
            val msgBytes = bytes.copyOfRange(offset, offset + msgLen)
            offset += msgLen
            try {
                val protoReply = ProtoReply.decodeFromByteArray(msgBytes)
                replies.add(protoReply.toRawReply())
            } catch (_: Exception) {
                // skip malformed messages
            }
        }
        return replies
    }
}

// --- Varint encoding/decoding ---

private fun writeVarint(output: MutableList<Byte>, value: Int) {
    var v = value
    while (v > 0x7F) {
        output.add(((v and 0x7F) or 0x80).toByte())
        v = v ushr 7
    }
    output.add((v and 0x7F).toByte())
}

private fun readVarint(bytes: ByteArray, startOffset: Int): Pair<Int, Int> {
    var result = 0
    var shift = 0
    var offset = startOffset
    while (offset < bytes.size) {
        val b = bytes[offset].toInt() and 0xFF
        offset++
        result = result or ((b and 0x7F) shl shift)
        if (b and 0x80 == 0) break
        shift += 7
    }
    return Pair(result, offset)
}

// --- JsonElement <-> ByteArr conversion ---

private fun jsonToBytes(json: JsonElement?): ByteArr {
    if (json == null || json is JsonNull) return ByteArr.empty
    val str = Json.encodeToString(JsonElement.serializer(), json)
    return ByteArr(str.encodeToByteArray())
}

private fun bytesToJson(bytes: ByteArr): JsonElement? {
    if (bytes.array.isEmpty()) return null
    return try {
        Json.decodeFromString(JsonElement.serializer(), bytes.array.decodeToString())
    } catch (_: Exception) {
        null
    }
}

// --- RawCommand -> Proto Command ---

private fun RawCommand.toProto(): ProtoCommand {
    return ProtoCommand(
        id = id,
        connect = connect?.toProto(),
        subscribe = subscribe?.toProto(),
        unsubscribe = unsubscribe?.toProto(),
        publish = publish?.toProto(),
        presence = presence?.toProto(),
        presenceStats = presenceStats?.toProto(),
        history = history?.toProto(),
        ping = ping?.toProto(),
        send = send?.toProto(),
        rpc = rpc?.toProto(),
        refresh = refresh?.toProto(),
        subRefresh = subRefresh?.toProto(),
    )
}

private fun ConnectRequest.toProto(): ProtoConnectRequest {
    return ProtoConnectRequest(
        token = token,
        data = jsonToBytes(data),
        subs = subs.mapValues { (_, v) -> v.toProto() },
        name = name,
        version = version,
        headers = headers,
    )
}

private fun SubscribeRequest.toProto(): ProtoSubscribeRequest {
    return ProtoSubscribeRequest(
        channel = channel,
        token = token,
        recover = recover,
        epoch = epoch,
        offset = offset,
        data = jsonToBytes(data),
        positioned = positioned,
        recoverable = recoverable,
        joinLeave = joinLeave,
        delta = delta,
        tf = tf?.toProto(),
    )
}

private fun FilterNode.toProto(): ProtoFilterNode {
    return ProtoFilterNode(
        op = op,
        key = key,
        cmp = cmp,
        `val` = `val`,
        vals = vals,
        nodes = nodes.map { it.toProto() },
    )
}

private fun UnsubscribeRequest.toProto(): ProtoUnsubscribeRequest {
    return ProtoUnsubscribeRequest(channel = channel)
}

private fun PublishRequest.toProto(): ProtoPublishRequest {
    return ProtoPublishRequest(
        channel = channel,
        data = jsonToBytes(data),
    )
}

private fun PresenceRequest.toProto(): ProtoPresenceRequest {
    return ProtoPresenceRequest(channel = channel)
}

private fun PresenceStatsRequest.toProto(): ProtoPresenceStatsRequest {
    return ProtoPresenceStatsRequest(channel = channel)
}

private fun HistoryRequest.toProto(): ProtoHistoryRequest {
    return ProtoHistoryRequest(
        channel = channel,
        limit = limit,
        since = since?.toProto(),
        reverse = reverse,
    )
}

private fun StreamPosition.toProto(): ProtoStreamPosition {
    return ProtoStreamPosition(offset = offset, epoch = epoch)
}

private fun PingRequest.toProto(): ProtoPingRequest {
    return ProtoPingRequest()
}

private fun SendRequest.toProto(): ProtoSendRequest {
    return ProtoSendRequest(data = jsonToBytes(data))
}

private fun RpcRequest.toProto(): ProtoRPCRequest {
    return ProtoRPCRequest(
        data = jsonToBytes(data),
        method = method,
    )
}

private fun RefreshRequest.toProto(): ProtoRefreshRequest {
    return ProtoRefreshRequest(token = token)
}

private fun SubRefreshRequest.toProto(): ProtoSubRefreshRequest {
    return ProtoSubRefreshRequest(
        channel = channel,
        token = token,
    )
}

// --- Proto Reply -> RawReply ---

private fun ProtoReply.toRawReply(): RawReply {
    return RawReply(
        id = id,
        error = error?.toRaw(),
        push = push?.toRaw(),
        connect = connect?.toRaw(),
        subscribe = subscribe?.toRaw(),
        unsubscribe = unsubscribe?.toRaw(),
        publish = publish?.toRaw(),
        presence = presence?.toRaw(),
        presenceStats = presenceStats?.toRaw(),
        history = history?.toRaw(),
        ping = ping?.toRaw(),
        rpc = rpc?.toRaw(),
        refresh = refresh?.toRaw(),
        subRefresh = subRefresh?.toRaw(),
    )
}

private fun ProtoError.toRaw(): Error {
    return Error(
        code = code,
        message = message,
        temporary = temporary,
    )
}

private fun ProtoPush.toRaw(): RawPush {
    return RawPush(
        channel = channel,
        publication = pub?.toRaw(),
        join = join?.toRaw(),
        leave = leave?.toRaw(),
        unsubscribe = unsubscribe?.toRaw(),
        message = message?.toRaw(),
        subscribe = subscribe?.toRaw(),
        connect = connect?.toRaw(),
        disconnect = disconnect?.toRaw(),
        refresh = refresh?.toRaw(),
    )
}

private fun ProtoPublication.toRaw(): Publication {
    return Publication(
        data = bytesToJson(data),
        info = info?.toRaw(),
        offset = offset,
        tags = tags,
        delta = delta,
        time = time,
        channel = channel,
    )
}

private fun ProtoClientInfo.toRaw(): ClientInfo {
    return ClientInfo(
        user = user,
        client = client,
        connInfo = bytesToJson(connInfo),
        chanInfo = bytesToJson(chanInfo),
    )
}

private fun ProtoJoin.toRaw(): Join {
    return Join(info = info?.toRaw())
}

private fun ProtoLeave.toRaw(): Leave {
    return Leave(info = info?.toRaw())
}

private fun ProtoUnsubscribe.toRaw(): Unsubscribe {
    return Unsubscribe(code = code, reason = reason)
}

private fun ProtoSubscribe.toRaw(): Subscribe {
    return Subscribe(
        recoverable = recoverable,
        epoch = epoch,
        offset = offset,
        positioned = positioned,
        data = bytesToJson(data),
    )
}

private fun ProtoMessage.toRaw(): Message {
    return Message(data = bytesToJson(data))
}

private fun ProtoConnect.toRaw(): Connect {
    return Connect(
        client = client,
        version = version,
        data = bytesToJson(data),
        subs = subs.mapValues { (_, v) -> v?.toRaw() ?: SubscribeResult() },
        expires = expires,
        ttl = ttl,
        ping = ping,
        pong = pong,
        session = session,
        node = node,
        time = time,
    )
}

private fun ProtoDisconnect.toRaw(): Disconnect {
    return Disconnect(
        code = code,
        reason = reason,
        reconnect = reconnect,
    )
}

private fun ProtoRefresh.toRaw(): Refresh {
    return Refresh(expires = expires, ttl = ttl)
}

private fun ProtoConnectResult.toRaw(): ConnectResult {
    return ConnectResult(
        client = client,
        version = version,
        expires = expires,
        ttl = ttl,
        data = bytesToJson(data),
        subs = subs.mapValues { (_, v) -> v?.toRaw() ?: SubscribeResult() },
        ping = ping,
        pong = pong,
        session = session,
        node = node,
        time = time,
    )
}

private fun ProtoSubscribeResult.toRaw(): SubscribeResult {
    return SubscribeResult(
        expires = expires,
        ttl = ttl,
        recoverable = recoverable,
        epoch = epoch,
        publications = publications.map { it.toRaw() },
        recovered = recovered,
        offset = offset,
        positioned = positioned,
        data = bytesToJson(data),
        wasRecovering = wasRecovering,
        delta = delta,
    )
}

private fun ProtoUnsubscribeResult.toRaw(): UnsubscribeResult {
    return UnsubscribeResult()
}

private fun ProtoPublishResult.toRaw(): PublishResult {
    return PublishResult()
}

private fun ProtoPresenceResult.toRaw(): PresenceResult {
    return PresenceResult(
        presence = presence.mapValues { (_, v) -> v?.toRaw() ?: ClientInfo() },
    )
}

private fun ProtoPresenceStatsResult.toRaw(): PresenceStatsResult {
    return PresenceStatsResult(
        numClients = numClients,
        numUsers = numUsers,
    )
}

private fun ProtoHistoryResult.toRaw(): HistoryResult {
    return HistoryResult(
        publications = publications.map { it.toRaw() },
        epoch = epoch,
        offset = offset,
    )
}

private fun ProtoPingResult.toRaw(): PingResult {
    return PingResult()
}

private fun ProtoRPCResult.toRaw(): RpcResult {
    return RpcResult(data = bytesToJson(data))
}

private fun ProtoRefreshResult.toRaw(): RefreshResult {
    return RefreshResult(
        client = client,
        version = version,
        expires = expires,
        ttl = ttl,
    )
}

private fun ProtoSubRefreshResult.toRaw(): SubRefreshResult {
    return SubRefreshResult(expires = expires, ttl = ttl)
}
