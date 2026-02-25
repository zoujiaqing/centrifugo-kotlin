package com.centrifugo.client

import kotlinx.serialization.json.JsonElement
import kotlin.math.pow
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

enum class Protocol {
    JSON,
    PROTOBUF,
}

enum class TransportType {
    WEBSOCKET,
    HTTP_STREAM,
    SSE,
}

data class TransportConfig(
    val transport: TransportType,
    val endpoint: String,
)

data class CentrifugoConfig(
    val protocol: Protocol = Protocol.PROTOBUF,
    val token: String = "",
    val name: String = "centrifugo-kotlin",
    val version: String = "",
    val data: JsonElement? = null,
    val headers: Map<String, String> = emptyMap(),
    val timeout: Duration = 10.seconds,
    val minReconnectDelay: Duration = 500.milliseconds,
    val maxReconnectDelay: Duration = 20.seconds,
    val maxServerPingDelay: Duration = 10.seconds,
    val getToken: (suspend () -> String)? = null,
    val emulationEndpoint: String = "",
    val transports: List<TransportConfig> = emptyList(),
)

interface ReconnectStrategy {
    fun timeBeforeNextAttempt(attempt: Int): Duration
}

data class BackoffReconnect(
    val factor: Double = 2.0,
    val minDelay: Duration = 500.milliseconds,
    val maxDelay: Duration = 20.seconds,
) : ReconnectStrategy {
    override fun timeBeforeNextAttempt(attempt: Int): Duration {
        if (minDelay > maxDelay) return maxDelay
        val timeMs = minDelay.inWholeMilliseconds * factor.pow(attempt)
        val clampedMs = timeMs.coerceIn(
            minDelay.inWholeMilliseconds.toDouble(),
            maxDelay.inWholeMilliseconds.toDouble()
        )
        return clampedMs.toLong().milliseconds
    }
}
