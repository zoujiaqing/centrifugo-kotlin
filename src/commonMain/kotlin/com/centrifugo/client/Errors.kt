package com.centrifugo.client

object ClientErrorCode {
    const val INTERNAL = 100
    const val UNAUTHORIZED = 101
    const val UNKNOWN_CHANNEL = 102
    const val PERMISSION_DENIED = 103
    const val METHOD_NOT_FOUND = 104
    const val ALREADY_SUBSCRIBED = 105
    const val LIMIT_EXCEEDED = 106
    const val BAD_REQUEST = 107
    const val NOT_AVAILABLE = 108
    const val TOKEN_EXPIRED = 109
    const val EXPIRED = 110
    const val TOO_MANY_REQUESTS = 111
    const val UNRECOVERABLE_POSITION = 112

    fun isTemporary(code: Int): Boolean {
        return code == INTERNAL || code == TOO_MANY_REQUESTS
    }

    fun description(code: Int): String = when (code) {
        INTERNAL -> "internal server error"
        UNAUTHORIZED -> "unauthorized"
        UNKNOWN_CHANNEL -> "unknown channel"
        PERMISSION_DENIED -> "permission denied"
        METHOD_NOT_FOUND -> "method not found"
        ALREADY_SUBSCRIBED -> "already subscribed"
        LIMIT_EXCEEDED -> "limit exceeded"
        BAD_REQUEST -> "bad request"
        NOT_AVAILABLE -> "not available"
        TOKEN_EXPIRED -> "token expired"
        EXPIRED -> "expired"
        TOO_MANY_REQUESTS -> "too many requests"
        UNRECOVERABLE_POSITION -> "unrecoverable position"
        else -> "unknown code $code"
    }
}

object DisconnectErrorCode {
    const val CONNECTION_CLOSED = 3000
    const val SHUTDOWN = 3001
    const val SERVER_ERROR = 3004
    const val EXPIRED = 3005
    const val SUB_EXPIRED = 3006
    const val SLOW = 3008
    const val WRITE_ERROR = 3009
    const val INSUFFICIENT_STATE = 3010
    const val FORCE_RECONNECT = 3011
    const val NO_PONG = 3012
    const val TOO_MANY_REQUESTS = 3013
    const val INVALID_TOKEN = 3500
    const val BAD_REQUEST = 3501
    const val STALE = 3502
    const val FORCE_NO_RECONNECT = 3503
    const val CONNECTION_LIMIT = 3504
    const val CHANNEL_LIMIT = 3505
    const val INAPPROPRIATE_PROTOCOL = 3506
    const val PERMISSION_DENIED = 3507
    const val NOT_AVAILABLE = 3508
    const val TOO_MANY_ERRORS = 3509

    fun shouldReconnect(code: Int): Boolean {
        return code !in 3500..3999 && code !in 4500..4999
    }

    fun description(code: Int): String = when (code) {
        CONNECTION_CLOSED -> "connection closed"
        SHUTDOWN -> "shutdown"
        SERVER_ERROR -> "internal server error"
        EXPIRED -> "connection expired"
        SUB_EXPIRED -> "subscription expired"
        SLOW -> "slow"
        WRITE_ERROR -> "write error"
        INSUFFICIENT_STATE -> "insufficient state"
        FORCE_RECONNECT -> "force reconnect"
        NO_PONG -> "no pong"
        TOO_MANY_REQUESTS -> "too many requests"
        INVALID_TOKEN -> "invalid token"
        BAD_REQUEST -> "bad request"
        STALE -> "stale"
        FORCE_NO_RECONNECT -> "force disconnect"
        CONNECTION_LIMIT -> "connection limit"
        CHANNEL_LIMIT -> "channel limit"
        INAPPROPRIATE_PROTOCOL -> "inappropriate protocol"
        PERMISSION_DENIED -> "permission denied"
        NOT_AVAILABLE -> "not available"
        TOO_MANY_ERRORS -> "too many errors"
        else -> "unknown code $code"
    }
}

sealed class RequestError : Exception() {
    data class ErrorResponse(val code: Int, override val message: String) : RequestError()
    data class UnexpectedReply(val reply: com.centrifugo.client.protocol.Reply) : RequestError()
    data object Timeout : RequestError() {
        override val message: String get() = "request timed out"
    }
    data object Cancelled : RequestError() {
        override val message: String get() = "operation cancelled"
    }
    data object ConnectionClosed : RequestError() {
        override val message: String get() = "connection closed"
    }
}

class RemoveSubscriptionException(message: String) : Exception(message) {
    companion object {
        fun notUnsubscribed() = RemoveSubscriptionException(
            "subscription must be unsubscribed to be removed"
        )
    }
}
