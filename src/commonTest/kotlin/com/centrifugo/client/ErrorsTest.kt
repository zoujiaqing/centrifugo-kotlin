package com.centrifugo.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ErrorsTest {

    // --- ClientErrorCode.isTemporary ---

    @Test
    fun internalIsTemporary() {
        assertTrue(ClientErrorCode.isTemporary(ClientErrorCode.INTERNAL))
    }

    @Test
    fun tooManyRequestsIsTemporary() {
        assertTrue(ClientErrorCode.isTemporary(ClientErrorCode.TOO_MANY_REQUESTS))
    }

    @Test
    fun unauthorizedIsNotTemporary() {
        assertFalse(ClientErrorCode.isTemporary(ClientErrorCode.UNAUTHORIZED))
    }

    @Test
    fun permissionDeniedIsNotTemporary() {
        assertFalse(ClientErrorCode.isTemporary(ClientErrorCode.PERMISSION_DENIED))
    }

    @Test
    fun badRequestIsNotTemporary() {
        assertFalse(ClientErrorCode.isTemporary(ClientErrorCode.BAD_REQUEST))
    }

    // --- ClientErrorCode.description ---

    @Test
    fun clientErrorDescriptions() {
        assertEquals("internal server error", ClientErrorCode.description(ClientErrorCode.INTERNAL))
        assertEquals("unauthorized", ClientErrorCode.description(ClientErrorCode.UNAUTHORIZED))
        assertEquals("unknown channel", ClientErrorCode.description(ClientErrorCode.UNKNOWN_CHANNEL))
        assertEquals("permission denied", ClientErrorCode.description(ClientErrorCode.PERMISSION_DENIED))
        assertEquals("method not found", ClientErrorCode.description(ClientErrorCode.METHOD_NOT_FOUND))
        assertEquals("already subscribed", ClientErrorCode.description(ClientErrorCode.ALREADY_SUBSCRIBED))
        assertEquals("limit exceeded", ClientErrorCode.description(ClientErrorCode.LIMIT_EXCEEDED))
        assertEquals("bad request", ClientErrorCode.description(ClientErrorCode.BAD_REQUEST))
        assertEquals("not available", ClientErrorCode.description(ClientErrorCode.NOT_AVAILABLE))
        assertEquals("token expired", ClientErrorCode.description(ClientErrorCode.TOKEN_EXPIRED))
        assertEquals("expired", ClientErrorCode.description(ClientErrorCode.EXPIRED))
        assertEquals("too many requests", ClientErrorCode.description(ClientErrorCode.TOO_MANY_REQUESTS))
        assertEquals("unrecoverable position", ClientErrorCode.description(ClientErrorCode.UNRECOVERABLE_POSITION))
    }

    @Test
    fun clientErrorUnknownCode() {
        assertEquals("unknown code 999", ClientErrorCode.description(999))
    }

    // --- DisconnectErrorCode.shouldReconnect ---

    @Test
    fun shutdownShouldReconnect() {
        assertTrue(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.SHUTDOWN))
    }

    @Test
    fun forceReconnectShouldReconnect() {
        assertTrue(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.FORCE_RECONNECT))
    }

    @Test
    fun connectionClosedShouldReconnect() {
        assertTrue(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.CONNECTION_CLOSED))
    }

    @Test
    fun invalidTokenShouldNotReconnect() {
        assertFalse(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.INVALID_TOKEN))
    }

    @Test
    fun badRequestShouldNotReconnect() {
        assertFalse(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.BAD_REQUEST))
    }

    @Test
    fun forceNoReconnectShouldNotReconnect() {
        assertFalse(DisconnectErrorCode.shouldReconnect(DisconnectErrorCode.FORCE_NO_RECONNECT))
    }

    @Test
    fun range3500To3999ShouldNotReconnect() {
        assertFalse(DisconnectErrorCode.shouldReconnect(3500))
        assertFalse(DisconnectErrorCode.shouldReconnect(3750))
        assertFalse(DisconnectErrorCode.shouldReconnect(3999))
    }

    @Test
    fun range4500To4999ShouldNotReconnect() {
        assertFalse(DisconnectErrorCode.shouldReconnect(4500))
        assertFalse(DisconnectErrorCode.shouldReconnect(4750))
        assertFalse(DisconnectErrorCode.shouldReconnect(4999))
    }

    @Test
    fun outsideNoReconnectRangesShouldReconnect() {
        assertTrue(DisconnectErrorCode.shouldReconnect(3000))
        assertTrue(DisconnectErrorCode.shouldReconnect(3499))
        assertTrue(DisconnectErrorCode.shouldReconnect(4000))
        assertTrue(DisconnectErrorCode.shouldReconnect(4499))
        assertTrue(DisconnectErrorCode.shouldReconnect(5000))
    }

    // --- DisconnectErrorCode.description ---

    @Test
    fun disconnectErrorDescriptions() {
        assertEquals("connection closed", DisconnectErrorCode.description(DisconnectErrorCode.CONNECTION_CLOSED))
        assertEquals("shutdown", DisconnectErrorCode.description(DisconnectErrorCode.SHUTDOWN))
        assertEquals("internal server error", DisconnectErrorCode.description(DisconnectErrorCode.SERVER_ERROR))
        assertEquals("connection expired", DisconnectErrorCode.description(DisconnectErrorCode.EXPIRED))
        assertEquals("force reconnect", DisconnectErrorCode.description(DisconnectErrorCode.FORCE_RECONNECT))
        assertEquals("invalid token", DisconnectErrorCode.description(DisconnectErrorCode.INVALID_TOKEN))
        assertEquals("force disconnect", DisconnectErrorCode.description(DisconnectErrorCode.FORCE_NO_RECONNECT))
    }

    @Test
    fun disconnectErrorUnknownCode() {
        assertEquals("unknown code 9999", DisconnectErrorCode.description(9999))
    }

    // --- RequestError ---

    @Test
    fun timeoutMessage() {
        assertEquals("request timed out", RequestError.Timeout.message)
    }

    @Test
    fun cancelledMessage() {
        assertEquals("operation cancelled", RequestError.Cancelled.message)
    }

    @Test
    fun connectionClosedMessage() {
        assertEquals("connection closed", RequestError.ConnectionClosed.message)
    }

    @Test
    fun errorResponseCarriesCodeAndMessage() {
        val err = RequestError.ErrorResponse(code = 101, message = "unauthorized")
        assertEquals(101, err.code)
        assertEquals("unauthorized", err.message)
    }

    // --- RemoveSubscriptionException ---

    @Test
    fun removeSubscriptionNotUnsubscribed() {
        val ex = RemoveSubscriptionException.notUnsubscribed()
        assertEquals("subscription must be unsubscribed to be removed", ex.message)
    }
}
