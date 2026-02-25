# API Reference

## Client API

### CentrifugoClient

Main client class that manages WebSocket connections and subscriptions.

```kotlin
class CentrifugoClient(url: String, config: CentrifugoConfig = CentrifugoConfig())
```

#### Properties

| Property | Type | Description |
|----------|------|-------------|
| `state` | `ClientState` | Current connection state (`DISCONNECTED` / `CONNECTING` / `CONNECTED`) |
| `client` | `String?` | Client ID (available when connected, `null` otherwise) |

#### Connection Management

| Method | Signature | Description |
|--------|-----------|-------------|
| `connect` | `suspend fun connect()` | Initiate WebSocket connection, triggers connection lifecycle |
| `disconnect` | `suspend fun disconnect()` | Disconnect, stops automatic reconnection |
| `ready` | `suspend fun ready()` | Suspend until client is connected |
| `setToken` | `fun setToken(token: String)` | Dynamically update authentication token |
| `setData` | `fun setData(data: JsonElement?)` | Set/update connection data (affects next connection attempt) |
| `setHeaders` | `fun setHeaders(headers: Map<String, String>)` | Set/update connection headers |
| `serverInfo` | `fun serverInfo(): ServerInfo` | Get server info after connection |
| `close` | `fun close()` | Release all resources (HttpClient, CoroutineScope) |

#### Batching

| Method | Signature | Description |
|--------|-----------|-------------|
| `startBatching` | `fun startBatching()` | Start collecting commands without sending until `stopBatching()` is called |
| `stopBatching` | `fun stopBatching()` | Stop batching and flush all collected commands in one batch |

#### Messaging

| Method | Signature | Description |
|--------|-----------|-------------|
| `publish` | `suspend fun publish(channel: String, data: JsonElement)` | Publish a message to the specified channel |
| `rpc` | `suspend fun rpc(method: String, data: JsonElement? = null): JsonElement?` | Make an RPC call, returns the result |
| `send` | `suspend fun send(data: JsonElement)` | Fire-and-forget message to server |
| `history` | `suspend fun history(channel: String, limit: Int = 0, since: StreamPosition? = null, reverse: Boolean = false): HistoryResult` | Get channel history |
| `presence` | `suspend fun presence(channel: String): PresenceResult` | Get channel presence info |
| `presenceStats` | `suspend fun presenceStats(channel: String): PresenceStatsResult` | Get channel presence statistics |

#### Subscription Management

| Method | Signature | Description |
|--------|-----------|-------------|
| `newSubscription` | `fun newSubscription(channel: String, subConfig: SubscriptionConfig = SubscriptionConfig()): Subscription` | Create a new subscription (returns existing if channel already subscribed) |
| `getSubscription` | `fun getSubscription(channel: String): Subscription?` | Get existing subscription by channel name |
| `removeSubscription` | `fun removeSubscription(subscription: Subscription)` | Remove a subscription (must be unsubscribed first) |
| `subscriptions` | `fun subscriptions(): Map<String, Subscription>` | Get all current subscriptions |

#### Event Callbacks

| Method | Signature | Description |
|--------|-----------|-------------|
| `onConnecting` | `fun onConnecting(callback: (ConnectingEvent) -> Unit)` | Connecting event |
| `onConnected` | `fun onConnected(callback: (ConnectedEvent) -> Unit)` | Connected event |
| `onDisconnected` | `fun onDisconnected(callback: (DisconnectedEvent) -> Unit)` | Disconnected event |
| `onError` | `fun onError(callback: (ErrorEvent) -> Unit)` | Error event |
| `onMessage` | `fun onMessage(callback: (MessageEvent) -> Unit)` | Server message event |

#### Server Subscription Callbacks

| Method | Signature | Description |
|--------|-----------|-------------|
| `onServerSubscribed` | `fun onServerSubscribed(callback: (ServerSubscribedEvent) -> Unit)` | Server subscribed client to channel |
| `onServerSubscribing` | `fun onServerSubscribing(callback: (ServerSubscribingEvent) -> Unit)` | Server subscription pending |
| `onServerUnsubscribed` | `fun onServerUnsubscribed(callback: (ServerUnsubscribedEvent) -> Unit)` | Server unsubscribed client from channel |
| `onServerPublication` | `fun onServerPublication(callback: (ServerPublicationEvent) -> Unit)` | Publication on server-subscribed channel |
| `onServerJoin` | `fun onServerJoin(callback: (ServerJoinEvent) -> Unit)` | Join on server-subscribed channel |
| `onServerLeave` | `fun onServerLeave(callback: (ServerLeaveEvent) -> Unit)` | Leave on server-subscribed channel |

---

### Subscription

Channel subscription handle, created via `CentrifugoClient.newSubscription()`.

```kotlin
class Subscription(val channel: String, ...)
```

#### Properties

| Property | Type | Description |
|----------|------|-------------|
| `channel` | `String` | Channel name |
| `state` | `SubscriptionState` | Current subscription state (`UNSUBSCRIBED` / `SUBSCRIBING` / `SUBSCRIBED`) |

#### Operations

| Method | Signature | Description |
|--------|-----------|-------------|
| `subscribe` | `suspend fun subscribe()` | Initiate subscription |
| `unsubscribe` | `suspend fun unsubscribe()` | Cancel subscription |
| `ready` | `suspend fun ready()` | Suspend until subscription is active |
| `publish` | `suspend fun publish(data: JsonElement)` | Publish a message to this channel |
| `history` | `suspend fun history(limit: Int = 0, since: StreamPosition? = null, reverse: Boolean = false): HistoryResult` | Get channel history |
| `presence` | `suspend fun presence(): PresenceResult` | Get channel presence info |
| `presenceStats` | `suspend fun presenceStats(): PresenceStatsResult` | Get channel presence statistics |
| `setTagsFilter` | `fun setTagsFilter(tagsFilter: FilterNode?)` | Update tags filter at runtime |

#### Event Callbacks

| Method | Signature | Description |
|--------|-----------|-------------|
| `onSubscribing` | `fun onSubscribing(callback: (SubscribingEvent) -> Unit)` | Subscribing event |
| `onSubscribed` | `fun onSubscribed(callback: (SubscribedEvent) -> Unit)` | Subscribed event |
| `onUnsubscribed` | `fun onUnsubscribed(callback: (UnsubscribedEvent) -> Unit)` | Unsubscribed event |
| `onPublication` | `fun onPublication(callback: (PublicationEvent) -> Unit)` | Message received event |
| `onJoin` | `fun onJoin(callback: (JoinEvent) -> Unit)` | Client joined channel |
| `onLeave` | `fun onLeave(callback: (LeaveEvent) -> Unit)` | Client left channel |
| `onError` | `fun onError(callback: (SubscriptionErrorEvent) -> Unit)` | Error event |

---

## Configuration

### Protocol

```kotlin
enum class Protocol {
    JSON,       // Newline-delimited JSON over text frames
    PROTOBUF,   // Varint length-prefixed Protobuf over binary frames (default)
}
```

### CentrifugoConfig

```kotlin
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
```

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `protocol` | `Protocol` | `PROTOBUF` | Wire protocol (`PROTOBUF` or `JSON`) |
| `token` | `String` | `""` | JWT authentication token |
| `name` | `String` | `"centrifugo-kotlin"` | Client name (sent to server) |
| `version` | `String` | `""` | Client version (sent to server) |
| `data` | `JsonElement?` | `null` | Custom data sent on connect |
| `headers` | `Map<String, String>` | `emptyMap()` | Custom headers sent on connect |
| `timeout` | `Duration` | `10s` | Request timeout |
| `minReconnectDelay` | `Duration` | `500ms` | Minimum reconnect delay |
| `maxReconnectDelay` | `Duration` | `20s` | Maximum reconnect delay |
| `maxServerPingDelay` | `Duration` | `10s` | Maximum server ping delay |
| `getToken` | `(suspend () -> String)?` | `null` | Callback for automatic token refresh |
| `emulationEndpoint` | `String` | `""` | HTTP endpoint for emulation transport (HTTP Stream/SSE) |
| `transports` | `List<TransportConfig>` | `emptyList()` | Transport list for automatic fallback |

### BackoffReconnect

```kotlin
data class BackoffReconnect(
    val factor: Double = 2.0,
    val minDelay: Duration = 500.milliseconds,
    val maxDelay: Duration = 20.seconds,
) : ReconnectStrategy
```

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `factor` | `Double` | `2.0` | Exponential backoff factor |
| `minDelay` | `Duration` | `500ms` | Minimum reconnect delay |
| `maxDelay` | `Duration` | `20s` | Maximum reconnect delay |

### ReconnectStrategy

```kotlin
interface ReconnectStrategy {
    fun timeBeforeNextAttempt(attempt: Int): Duration
}
```

Can be custom-implemented to replace the default `BackoffReconnect`.

### TransportType

```kotlin
enum class TransportType {
    WEBSOCKET,     // WebSocket transport (default)
    HTTP_STREAM,   // HTTP Streaming transport
    SSE,           // Server-Sent Events transport
}
```

### TransportConfig

```kotlin
data class TransportConfig(
    val transport: TransportType,
    val endpoint: String,
)
```

| Parameter | Type | Description |
|-----------|------|-------------|
| `transport` | `TransportType` | Transport type (WEBSOCKET, HTTP_STREAM, or SSE) |
| `endpoint` | `String` | Connection endpoint URL |

Usage example with automatic fallback:

```kotlin
val config = CentrifugoConfig(
    transports = listOf(
        TransportConfig(TransportType.WEBSOCKET, "ws://localhost:8000/connection/websocket"),
        TransportConfig(TransportType.HTTP_STREAM, "http://localhost:8000/connection/http_stream"),
        TransportConfig(TransportType.SSE, "http://localhost:8000/connection/sse"),
    ),
    emulationEndpoint = "http://localhost:8000/emulation",
)
```

### SubscriptionConfig

```kotlin
data class SubscriptionConfig(
    val token: String = "",
    val data: JsonElement? = null,
    val getToken: (suspend (SubscriptionTokenEvent) -> String)? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
    val joinLeave: Boolean = false,
    val since: StreamPosition? = null,
    val delta: String = "",
    val tagsFilter: FilterNode? = null,
    val minResubscribeDelay: Duration = 500.milliseconds,
    val maxResubscribeDelay: Duration = 20.seconds,
)
```

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `token` | `String` | `""` | Subscription token (for private channels) |
| `data` | `JsonElement?` | `null` | Custom data sent on subscribe |
| `getToken` | `(suspend (SubscriptionTokenEvent) -> String)?` | `null` | Token refresh callback |
| `positioned` | `Boolean` | `false` | Enable position tracking |
| `recoverable` | `Boolean` | `false` | Enable recovery |
| `joinLeave` | `Boolean` | `false` | Enable join/leave events |
| `since` | `StreamPosition?` | `null` | Resume from stream position |
| `delta` | `String` | `""` | Delta compression mode |
| `tagsFilter` | `FilterNode?` | `null` | Publication tags filter for server-side filtering |
| `minResubscribeDelay` | `Duration` | `500ms` | Minimum resubscribe delay |
| `maxResubscribeDelay` | `Duration` | `20s` | Maximum resubscribe delay |

### SubscriptionTokenEvent

```kotlin
data class SubscriptionTokenEvent(val channel: String)
```

### FilterNode

FilterNode is used for server-side publication filtering via the `tagsFilter` option.

```kotlin
data class FilterNode(
    val op: String = "",        // Logical operator: "and", "or", "not"
    val key: String = "",       // Field name to compare
    val cmp: String = "",       // Comparison operator
    val `val`: String = "",     // Comparison value
    val vals: List<String> = emptyList(),  // Values for "in"/"nin" comparisons
    val nodes: List<FilterNode> = emptyList(),  // Child nodes for logical ops
)
```

#### Comparison Operators

| Operator | Description |
|----------|-------------|
| `eq` | Equals |
| `neq` | Not equals |
| `in` | In list |
| `nin` | Not in list |
| `ex` | Exists |
| `nex` | Not exists |
| `sw` | Starts with |
| `ew` | Ends with |
| `ct` | Contains |
| `lt` | Less than |
| `lte` | Less than or equal |
| `gt` | Greater than |
| `gte` | Greater than or equal |

#### Logical Operators

| Operator | Description |
|----------|-------------|
| `and` | AND all child nodes |
| `or` | OR all child nodes |
| `not` | NOT the child node |

#### Examples

```kotlin
// Simple equality filter
val filter1 = FilterNode(key = "type", cmp = "eq", `val` = "news")

// IN filter
val filter2 = FilterNode(key = "category", cmp = "in", vals = listOf("news", "sports"))

// AND filter
val filter3 = FilterNode(
    op = "and",
    nodes = listOf(
        FilterNode(key = "status", cmp = "eq", `val` = "active"),
        FilterNode(key = "verified", cmp = "eq", `val` = "true"),
    )
)

// OR filter with nested NOT
val filter4 = FilterNode(
    op = "or",
    nodes = listOf(
        FilterNode(key = "type", cmp = "eq", `val` = "news"),
        FilterNode(key = "type", cmp = "eq", `val` = "alert"),
    )
)
```

---

## Events

### Client Events

```kotlin
data class ConnectingEvent(val code: Int, val reason: String)
data class ConnectedEvent(val clientId: String, val version: String, val data: JsonElement?)
data class DisconnectedEvent(val code: Int, val reason: String)
data class ErrorEvent(val error: Exception)
data class MessageEvent(val data: JsonElement?)
```

### Subscription Events

```kotlin
data class SubscribingEvent(val channel: String, val code: Int, val reason: String)
data class SubscribedEvent(
    val channel: String,
    val wasRecovering: Boolean = false,
    val recovered: Boolean = false,
    val data: JsonElement? = null,
    val streamPosition: StreamPosition? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
)
data class UnsubscribedEvent(val channel: String, val code: Int, val reason: String)
data class PublicationEvent(
    val data: JsonElement?,
    val offset: Long,
    val info: ClientInfo?,
    val tags: Map<String, String>,
    val channel: String,
)
data class JoinEvent(val channel: String, val info: ClientInfo)
data class LeaveEvent(val channel: String, val info: ClientInfo)
data class SubscriptionErrorEvent(val error: Exception)
```

### Server Subscription Events

```kotlin
data class ServerSubscribedEvent(
    val channel: String,
    val wasRecovering: Boolean = false,
    val recovered: Boolean = false,
    val data: JsonElement? = null,
    val streamPosition: StreamPosition? = null,
    val positioned: Boolean = false,
    val recoverable: Boolean = false,
)
data class ServerSubscribingEvent(val channel: String)
data class ServerUnsubscribedEvent(val channel: String)
data class ServerPublicationEvent(
    val channel: String,
    val data: JsonElement?,
    val offset: Long,
    val info: ClientInfo?,
    val tags: Map<String, String>,
)
data class ServerJoinEvent(val channel: String, val info: ClientInfo)
data class ServerLeaveEvent(val channel: String, val info: ClientInfo)
```

---

## Result Types

### HistoryResult

```kotlin
data class HistoryResult(
    val publications: List<PublicationEvent>,
    val offset: Long,
    val epoch: String,
)
```

### PresenceResult

```kotlin
data class PresenceResult(
    val clients: Map<String, ClientInfo>,
)
```

### PresenceStatsResult

```kotlin
data class PresenceStatsResult(
    val numClients: Int,
    val numUsers: Int,
)
```

---

## Errors

### RequestError

Exceptions that client operations may throw:

```kotlin
sealed class RequestError : Exception() {
    data class ErrorResponse(val code: Int, override val message: String) : RequestError()
    data class UnexpectedReply(val reply: Reply) : RequestError()
    data object Timeout : RequestError()
    data object Cancelled : RequestError()
    data object ConnectionClosed : RequestError()
}
```

| Type | Description |
|------|-------------|
| `ErrorResponse` | Server-returned error (includes error code and message) |
| `UnexpectedReply` | Received an unexpected reply type |
| `Timeout` | Request timed out |
| `Cancelled` | Operation cancelled |
| `ConnectionClosed` | Connection is closed |

### ClientErrorCode

| Code | Constant | Description |
|------|----------|-------------|
| 100 | `INTERNAL` | Internal server error |
| 101 | `UNAUTHORIZED` | Unauthorized |
| 102 | `UNKNOWN_CHANNEL` | Unknown channel |
| 103 | `PERMISSION_DENIED` | Permission denied |
| 104 | `METHOD_NOT_FOUND` | RPC method not found |
| 105 | `ALREADY_SUBSCRIBED` | Already subscribed |
| 106 | `LIMIT_EXCEEDED` | Limit exceeded |
| 107 | `BAD_REQUEST` | Bad request |
| 108 | `NOT_AVAILABLE` | Not available |
| 109 | `TOKEN_EXPIRED` | Token expired |
| 110 | `EXPIRED` | Expired |
| 111 | `TOO_MANY_REQUESTS` | Too many requests |
| 112 | `UNRECOVERABLE_POSITION` | Unrecoverable position |

Helper methods:
- `ClientErrorCode.isTemporary(code)` — Whether it's a temporary error (retryable)
- `ClientErrorCode.description(code)` — Error description text

### DisconnectErrorCode

| Code | Constant | Description |
|------|----------|-------------|
| 3000 | `CONNECTION_CLOSED` | Connection closed |
| 3001 | `SHUTDOWN` | Server shutdown |
| 3004 | `SERVER_ERROR` | Internal server error |
| 3005 | `EXPIRED` | Connection expired |
| 3006 | `SUB_EXPIRED` | Subscription expired |
| 3008 | `SLOW` | Client too slow |
| 3009 | `WRITE_ERROR` | Write error |
| 3010 | `INSUFFICIENT_STATE` | Insufficient state |
| 3011 | `FORCE_RECONNECT` | Force reconnect |
| 3012 | `NO_PONG` | Pong timeout |
| 3013 | `TOO_MANY_REQUESTS` | Too many requests |
| 3500 | `INVALID_TOKEN` | Invalid token |
| 3501 | `BAD_REQUEST` | Bad request |
| 3502 | `STALE` | Stale connection |
| 3503 | `FORCE_NO_RECONNECT` | Force disconnect (no reconnect) |
| 3504 | `CONNECTION_LIMIT` | Connection limit exceeded |
| 3505 | `CHANNEL_LIMIT` | Channel limit exceeded |
| 3506 | `INAPPROPRIATE_PROTOCOL` | Inappropriate protocol |
| 3507 | `PERMISSION_DENIED` | Permission denied |
| 3508 | `NOT_AVAILABLE` | Not available |
| 3509 | `TOO_MANY_ERRORS` | Too many errors |

Helper methods:
- `DisconnectErrorCode.shouldReconnect(code)` — Whether to auto-reconnect
- `DisconnectErrorCode.description(code)` — Error description text

Reconnection rules:
- `3000-3499` → Reconnect
- `3500-3999` → Do not reconnect (fatal)
- `4500-4999` → Do not reconnect
- Other → Reconnect

---

## Data Types

### ServerInfo

Available after connection via `client.serverInfo()`:

```kotlin
data class ServerInfo(
    val clientId: String = "",
    val serverVersion: String = "",
    val tokenExpires: Boolean = false,
    val tokenTtl: Int = 0,
    val serverPing: Int = 0,
    val sendPong: Boolean = false,
)
```

### ClientInfo

```kotlin
data class ClientInfo(
    val user: String = "",
    val client: String = "",
    val connInfo: JsonElement? = null,
    val chanInfo: JsonElement? = null,
)
```

### StreamPosition

```kotlin
data class StreamPosition(
    val offset: Long = 0,
    val epoch: String = "",
)
```

### ClientState

```kotlin
enum class ClientState { DISCONNECTED, CONNECTING, CONNECTED }
```

### SubscriptionState

```kotlin
enum class SubscriptionState { UNSUBSCRIBED, SUBSCRIBING, SUBSCRIBED }
```

### RemoveSubscriptionException

Thrown when attempting to remove a subscription that hasn't been unsubscribed:

```kotlin
class RemoveSubscriptionException(message: String) : Exception(message)
```
