package com.centrifugo.client.protocol

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

sealed class FrameData {
    data class Text(val text: String) : FrameData()
    data class Binary(val data: ByteArray) : FrameData() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (other !is Binary) return false
            return data.contentEquals(other.data)
        }
        override fun hashCode(): Int = data.contentHashCode()
    }
}

interface Codec {
    fun encodeCommands(commands: List<RawCommand>): FrameData
    fun decodeReplies(data: FrameData): List<RawReply>
    fun encodeEmulationRequest(session: String, node: String, data: FrameData): ByteArray
}

val CentrifugoJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
}

class JsonCodec : Codec {
    override fun encodeEmulationRequest(session: String, node: String, data: FrameData): ByteArray {
        val dataStr = when (data) {
            is FrameData.Text -> data.text
            is FrameData.Binary -> data.data.decodeToString()
        }
        val req = EmulationRequest(session = session, node = node, data = dataStr)
        return CentrifugoJson.encodeToString(req).encodeToByteArray()
    }

    override fun encodeCommands(commands: List<RawCommand>): FrameData {
        val text = commands.joinToString("\n") { command ->
            CentrifugoJson.encodeToString(command)
        }
        return FrameData.Text(text)
    }

    override fun decodeReplies(data: FrameData): List<RawReply> {
        val text = when (data) {
            is FrameData.Text -> data.text
            is FrameData.Binary -> data.data.decodeToString()
        }
        return text.split("\n")
            .filter { it.isNotBlank() }
            .mapNotNull { line ->
                try {
                    CentrifugoJson.decodeFromString<RawReply>(line)
                } catch (e: Exception) {
                    null
                }
            }
    }
}
