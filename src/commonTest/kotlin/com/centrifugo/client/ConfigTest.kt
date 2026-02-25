package com.centrifugo.client

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class ConfigTest {

    // --- CentrifugoConfig defaults ---

    @Test
    fun defaultProtocolIsProtobuf() {
        val config = CentrifugoConfig()
        assertEquals(Protocol.PROTOBUF, config.protocol)
    }

    @Test
    fun defaultTimeout() {
        val config = CentrifugoConfig()
        assertEquals(10.seconds, config.timeout)
    }

    @Test
    fun defaultReconnectDelays() {
        val config = CentrifugoConfig()
        assertEquals(500.milliseconds, config.minReconnectDelay)
        assertEquals(20.seconds, config.maxReconnectDelay)
    }

    @Test
    fun defaultName() {
        val config = CentrifugoConfig()
        assertEquals("centrifugo-kotlin", config.name)
    }

    @Test
    fun defaultTokenEmpty() {
        val config = CentrifugoConfig()
        assertEquals("", config.token)
    }

    @Test
    fun defaultDataNull() {
        val config = CentrifugoConfig()
        assertNull(config.data)
    }

    @Test
    fun defaultHeadersEmpty() {
        val config = CentrifugoConfig()
        assertTrue(config.headers.isEmpty())
    }

    @Test
    fun defaultGetTokenNull() {
        val config = CentrifugoConfig()
        assertNull(config.getToken)
    }

    @Test
    fun defaultMaxServerPingDelay() {
        val config = CentrifugoConfig()
        assertEquals(10.seconds, config.maxServerPingDelay)
    }

    // --- BackoffReconnect ---

    @Test
    fun backoffAttemptZeroReturnsMinDelay() {
        val backoff = BackoffReconnect()
        assertEquals(500.milliseconds, backoff.timeBeforeNextAttempt(0))
    }

    @Test
    fun backoffAttemptOneReturnsDoubleMin() {
        val backoff = BackoffReconnect()
        assertEquals(1000.milliseconds, backoff.timeBeforeNextAttempt(1))
    }

    @Test
    fun backoffAttemptTwoReturnsFourTimesMin() {
        val backoff = BackoffReconnect()
        assertEquals(2000.milliseconds, backoff.timeBeforeNextAttempt(2))
    }

    @Test
    fun backoffCapsAtMaxDelay() {
        val backoff = BackoffReconnect()
        val delay = backoff.timeBeforeNextAttempt(100)
        assertEquals(20.seconds, delay)
    }

    @Test
    fun backoffMinGreaterThanMaxReturnsMax() {
        val backoff = BackoffReconnect(
            minDelay = 30.seconds,
            maxDelay = 10.seconds,
        )
        assertEquals(10.seconds, backoff.timeBeforeNextAttempt(0))
    }

    @Test
    fun backoffCustomValues() {
        val backoff = BackoffReconnect(
            factor = 3.0,
            minDelay = 100.milliseconds,
            maxDelay = 5.seconds,
        )
        assertEquals(100.milliseconds, backoff.timeBeforeNextAttempt(0))
        assertEquals(300.milliseconds, backoff.timeBeforeNextAttempt(1))
        assertEquals(900.milliseconds, backoff.timeBeforeNextAttempt(2))
    }

    // --- SubscriptionConfig defaults ---

    @Test
    fun subscriptionConfigDefaults() {
        val config = SubscriptionConfig()
        assertEquals("", config.token)
        assertNull(config.data)
        assertNull(config.getToken)
        assertFalse(config.positioned)
        assertFalse(config.recoverable)
        assertFalse(config.joinLeave)
        assertNull(config.since)
        assertEquals("", config.delta)
        assertEquals(500.milliseconds, config.minResubscribeDelay)
        assertEquals(20.seconds, config.maxResubscribeDelay)
    }
}
