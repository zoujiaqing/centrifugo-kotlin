# centrifugo-kotlin Architecture Specification

## 1. Overview

centrifugo-kotlin is a Kotlin Multiplatform (KMP) client SDK for [Centrifugo](https://centrifugal.dev/) real-time messaging. Built on the Centrifugo protocol v2, it supports both JSON and Protobuf wire formats, and uses Kotlin Coroutines as the async runtime.

### 1.1 Target Platforms

| Platform | Target | WebSocket Engine |
|----------|--------|-----------------|
| Android | androidTarget | Ktor + OkHttp |
| iOS x86_64 | iosX64 | Ktor + Darwin |
| iOS ARM64 | iosArm64 | Ktor + Darwin |
| iOS Simulator | iosSimulatorArm64 | Ktor + Darwin |
| JS (Browser / Node.js) | js(IR) | Ktor + JS WebSocket |

### 1.2 Protocol Support

Supports two wire protocols:

- **Protobuf** (default) — Binary protocol using varint length-prefixed messages. More compact and efficient. WebSocket subprotocol: `centrifuge-protobuf`.
- **JSON** — Text protocol using newline-delimited JSON messages. Easier to debug.

### 1.3 Tech Stack

| Component | Choice | Version |
|-----------|--------|---------|
| Language | Kotlin | 2.1.21 |
| Async | kotlinx-coroutines | 1.10.1 |
| Network | Ktor Client + WebSockets | 3.1.1 |
| Serialization | kotlinx-serialization-json | 1.8.0 |
| Protobuf | pbandk runtime | 0.16.0 |
| Build | Gradle + KMP Plugin | 8.11.1 |

---

## 2. Project Structure

```
centrifugo-kotlin/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/libs.versions.toml
├── docs/
│   ├── architecture.md                         ← This document
│   ├── api.md                                  # API Reference
│   └── quickstart.md                           # Quick Start
└── src/
    ├── commonMain/kotlin/com/centrifugo/client/
    │   ├── CentrifugoClient.kt                 # Client main class
    │   ├── Subscription.kt                     # Subscription management
    │   ├── Config.kt                           # Client config + reconnect strategy
    │   ├── Events.kt                           # Event data classes
    │   ├── Errors.kt                           # Error codes + exception types
    │   ├── WebSocketHandler.kt                 # WebSocket read/write coroutines
    │   ├── MessageStore.kt                     # Command batch queue
    │   └── protocol/
    │       ├── Protocol.kt                     # Centrifugo protocol data classes
    │       ├── Command.kt                      # Command sealed class
    │       ├── Reply.kt                        # Reply sealed class + Push parsing
    │       ├── Codec.kt                        # Codec interface + JsonCodec
    │       ├── ProtobufCodec.kt                # ProtobufCodec implementation
    │       └── protobuf/
    │           └── client.kt                   # Generated pbandk message classes
    ├── androidMain/
    │   └── kotlin/.../HttpEngine.kt            # Android HTTP engine
    ├── iosMain/
    │   └── kotlin/.../HttpEngine.kt            # iOS HTTP engine
    └── jsMain/
        └── kotlin/.../HttpEngine.kt            # JS HTTP engine
```

---

## 3. Layered Architecture

```
┌─────────────────────────────────────────────────────────┐
│                 Application Layer                        │
│            CentrifugoClient — User-facing API            │
├─────────────────────────────────────────────────────────┤
│                  Session Layer                           │
│       WebSocketHandler — Connection lifecycle            │
├─────────────────────────────────────────────────────────┤
│                 Protocol Layer                           │
│  Command / Reply / PushMessage — Type-safe abstractions │
├─────────────────────────────────────────────────────────┤
│                  Codec Layer                             │
│  Codec interface → JsonCodec / ProtobufCodec            │
├─────────────────────────────────────────────────────────┤
│                Transport Layer                           │
│         Ktor WebSocket (OkHttp / Darwin / JS)           │
└─────────────────────────────────────────────────────────┘
```

---

## 4. Protocol Layer

### 4.1 Centrifugo Protocol v2

Centrifugo uses a request-response model over WebSocket. Each message carries an incrementing `id` field.

**Command**: Client → Server, each request carries an incrementing `id` field.

**Reply**: Server → Client, `id > 0` is a command reply, `id == 0` is a server-initiated push.

**Empty Reply**: `id == 0` with all fields empty, used as a Ping signal. The client should respond with an empty command as Pong.

### 4.2 Wire Format

The SDK supports two wire formats, selected via `CentrifugoConfig.protocol`:

**JSON** (`Protocol.JSON`):
- Messages are JSON objects serialized via `kotlinx.serialization`
- Multiple messages are separated by newline `\n`
- Transported as WebSocket text frames

**Protobuf** (`Protocol.PROTOBUF`, default):
- Messages are serialized using pbandk (Protocol Buffers for Kotlin Multiplatform)
- Multiple messages are prefixed with varint-encoded length
- Transported as WebSocket binary frames
- WebSocket subprotocol: `centrifuge-protobuf`

### 4.3 Codec Interface

```kotlin
sealed class FrameData {
    data class Text(val text: String) : FrameData()
    data class Binary(val data: ByteArray) : FrameData()
}

interface Codec {
    fun encodeCommands(commands: List<RawCommand>): FrameData
    fun decodeReplies(data: FrameData): List<RawReply>
}
```

- **JsonCodec**: Serializes `RawCommand` to newline-delimited JSON text, returns `FrameData.Text`
- **ProtobufCodec**: Converts `RawCommand` → pbandk `Command` → varint-prefixed binary, returns `FrameData.Binary`

### 4.4 Protobuf Data Conversion

The public API uses `JsonElement` for flexible data fields (e.g., `data`, `connInfo`, `chanInfo`). In the Protobuf wire format, these are `bytes` fields.

ProtobufCodec handles the conversion internally:
- **Encode**: `JsonElement` → JSON string → UTF-8 bytes → `pbandk.ByteArr`
- **Decode**: `pbandk.ByteArr` → UTF-8 string → JSON parse → `JsonElement`

This approach is consistent with other official Centrifugo client SDKs (e.g., centrifuge-dart).

### 4.5 Type-safe Abstractions

The protocol layer provides two levels of abstraction:

**Raw Layer** — Direct wire structure mapping for serialization/deserialization:
```kotlin
@Serializable
data class RawCommand(val id: Int, val connect: ConnectRequest?, val subscribe: SubscribeRequest?, ...)
@Serializable
data class RawReply(val id: Int, val error: Error?, val push: RawPush?, val connect: ConnectResult?, ...)
```

**Sealed Layer** — Type-safe ADTs (Algebraic Data Types) for business logic:
```kotlin
sealed class Command {
    data class Connect(val request: ConnectRequest) : Command()
    data class Subscribe(val request: SubscribeRequest) : Command()
    ...
    data object Empty : Command()
}

sealed class Reply {
    data class Push(val push: PushMessage) : Reply()
    data class Connect(val result: ConnectResult) : Reply()
    ...
    data object Empty : Reply()
}

sealed class PushData {
    data class Publication(...) : PushData()
    data class Join(...) : PushData()
    ...
}
```

Conversion methods:
- `Command.toRawCommand(id)` — Sealed → Raw (before sending)
- `Reply.fromRawReply(raw)` — Raw → Sealed (after receiving)
- `PushMessage.fromRawPush(raw)` — RawPush → PushMessage

---

## 5. Client Architecture

### 5.1 State Machine

The client maintains a three-state finite state machine:

```
                  connect()
DISCONNECTED ──────────────→ CONNECTING
      ↑                          │
      │                          │ handshake success
      │   disconnect()           ↓
      ←──────────────────── CONNECTED
      ←── disconnect (no reconnect) ←
                                 │
               disconnect (reconnect) → CONNECTING
```

| State | Description |
|-------|-------------|
| `DISCONNECTED` | Initial state / disconnected, no active connection |
| `CONNECTING` | Establishing WebSocket connection or waiting to reconnect |
| `CONNECTED` | WebSocket connected and handshake completed |

### 5.2 Connection Lifecycle

```
connect() called
  │
  ↓
moveToConnecting() → triggers onConnecting callback
  │
  ↓
startConnectionCycle() → launches coroutine loop
  │
  ├── [first attempt] connect immediately
  ├── [reconnect] delay per BackoffReconnect strategy
  │
  ↓
doSingleConnection()
  │
  ├── Refresh token via getToken callback (if configured)
  ├── Create WebSocketHandler (with codec)
  ├── Ktor httpClient.webSocket(url) establishes connection
  │     └── Sets Sec-WebSocket-Protocol header for Protobuf
  ├── Send ConnectRequest (handshake)
  ├── Await ConnectResult
  │     │
  │     ├── Success → moveToConnected() → subscribeAll() → wait for disconnect
  │     ├── Temporary error → reconnect = true → retry
  │     └── Permanent error → reconnect = false → terminate
  │
  ↓
Connection closed
  │
  ├── shouldReconnect = true → back to CONNECTING, reconnect
  └── shouldReconnect = false → moveToDisconnected()
```

### 5.3 WebSocketHandler

`WebSocketHandler` manages concurrent read/write on the WebSocket connection using a `Codec` instance for encoding/decoding:

```
                ┌─────────────────────┐
                │   WebSocketHandler  │
                ├─────────────────────┤
   sendCommand()│                     │
  ─────────────→│  controlChannel     │
                │  (Channel<Pending>) │
                │       │             │
                │       ↓             │
                │  ┌─────────┐        │
                │  │ Writer  │        │    WebSocket
                │  │ Coroutine│───────│──→ session.send()
                │  └─────────┘        │    (Text or Binary frame)
                │                     │
                │  ┌─────────┐        │    WebSocket
                │  │ Reader  │←───────│──← session.incoming
                │  │ Coroutine│       │    (Text or Binary frame)
                │  └────┬────┘        │
                │       │             │
                │       ├── id > 0 → replyMap match → CompletableDeferred.complete()
                │       ├── id = 0 → onPush() callback
                │       └── Empty  → pingChannel → Writer sends Pong
                └─────────────────────┘
```

**Key mechanisms:**

- **Codec abstraction**: Writer calls `codec.encodeCommands()` → sends `FrameData.Text` or `FrameData.Binary`; Reader calls `codec.decodeReplies()` on received frames
- **Binary + Text frame support**: Reader handles both `Frame.Text` and `Frame.Binary`
- **Command ID management**: AtomicInt auto-increment, ensuring unique ID per command (skipping 0)
- **Batch sending**: Writer coroutine batches up to 32 commands per send
- **Timeout management**: Each request launches an independent timeout coroutine; on expiry, removes from replyMap and returns Timeout error
- **Ping/Pong**: Reader receives Empty Reply → notifies Writer via pingChannel → Writer sends Empty Command

### 5.4 Command Batching

The client supports explicit command batching via `startBatching()` / `stopBatching()`:

```
startBatching()
  │
  ├── batching = true
  ├── Subsequent sendCommand() calls buffer commands locally
  │   (stored in batchedCommands list with their CompletableDeferreds)
  │
  ↓
stopBatching()
  │
  ├── batching = false
  ├── All buffered commands sent to WebSocketHandler.commandChannel at once
  └── Writer coroutine receives them and encodes into a single frame
```

This allows users to collect multiple operations (subscribe, publish, RPC) and send them in one WebSocket frame, reducing round-trips.

### 5.5 MessageStore

`MessageStore` is a command buffer queue that holds pending commands during connection establishment:

```kotlin
internal class MessageStore(timeout: Duration) {
    fun send(command: Command): CompletableDeferred<Result<Reply>>  // Enqueue + expire cleanup
    fun getNext(now: ValueTimeMark): MessageStoreItem?              // Dequeue (skip expired)
}
```

- FIFO queue based on `ArrayDeque`
- Each message has a deadline (current time + timeout)
- Expired messages are cleaned up on enqueue
- Expiry is re-checked on dequeue

### 5.5 Reconnection Strategy

```kotlin
interface ReconnectStrategy {
    fun timeBeforeNextAttempt(attempt: Int): Duration
}

data class BackoffReconnect(
    val factor: Double = 2.0,        // Exponential factor
    val minDelay: Duration = 500ms,  // Minimum delay
    val maxDelay: Duration = 20s,    // Maximum delay
) : ReconnectStrategy
```

Delay calculation: `delay = clamp(minDelay * factor^attempt, minDelay, maxDelay)`

| attempt | delay |
|---------|-------|
| 0 | 0 (no delay on first attempt) |
| 1 | 500ms |
| 2 | 1s |
| 3 | 2s |
| 4 | 4s |
| 5 | 8s |
| 6 | 16s |
| 7+ | 20s (cap) |

---

## 6. Subscription Architecture

### 6.1 Subscription State Machine

```
                subscribe()
UNSUBSCRIBED ──────────────→ SUBSCRIBING
      ↑                           │
      │                           │ SubscribeResult success
      │  unsubscribe()            ↓
      ←───────────────────── SUBSCRIBED
      ←── Server Unsubscribe ────←
```

### 6.2 Subscription Class

`Subscription` is a lightweight handle that references `SubscriptionInner` inside `CentrifugoClient` via `id`:

```kotlin
class Subscription(val channel: String, client: CentrifugoClient, id: Int) {
    val state: SubscriptionState          // Delegates to client.getSubscriptionState(id)
    suspend fun subscribe()               // → client.doSubscribe(id)
    suspend fun unsubscribe()             // → client.doUnsubscribe(id)
    suspend fun publish(data: JsonElement) // → client.doSubscriptionPublish(id, data)
    suspend fun history(...)              // → client.doSubscriptionHistory(id, ...)
    suspend fun presence()                // → client.doSubscriptionPresence(id)
    suspend fun presenceStats()           // → client.doSubscriptionPresenceStats(id)
    suspend fun ready()                   // Wait until subscribed
    fun onPublication(callback)
    fun onJoin(callback)
    fun onLeave(callback)
    // ...
}
```

### 6.3 Automatic Resubscription

When a connection is established (or reconnected), `CentrifugoClient.subscribeAll()` iterates all subscriptions not in `UNSUBSCRIBED` state and automatically sends SubscribeRequest.

### 6.4 Push Dispatch

Server-pushed messages are dispatched by channel to the corresponding handler:

```
WebSocketHandler.onPush(Reply.Push)
  → CentrifugoClient.handlePush()
    → subNameToId[channel] → subscriptions[id]
      → SubscriptionInner.handlePublication()
        → onPublication callback

    (if no client-side subscription found)
      → Server subscription event callbacks
        → onServerPublication / onServerJoin / onServerLeave / etc.
```

### 6.5 Server-side Subscriptions

The client supports server-side subscriptions — subscriptions managed by the Centrifugo server. These are delivered in the `ConnectResult.subs` map and via Push messages with channels that have no client-side `Subscription` object.

Events: `onServerSubscribed`, `onServerSubscribing`, `onServerUnsubscribed`, `onServerPublication`, `onServerJoin`, `onServerLeave`.

---

## 7. Error System

### 7.1 Client Error Codes (ClientErrorCode)

Application-layer errors returned by the server in Reply.Error:

| Code | Name | Description |
|------|------|-------------|
| 100 | INTERNAL | Internal server error |
| 101 | UNAUTHORIZED | Unauthorized |
| 102 | UNKNOWN_CHANNEL | Unknown channel |
| 103 | PERMISSION_DENIED | Permission denied |
| 104 | METHOD_NOT_FOUND | RPC method not found |
| 105 | ALREADY_SUBSCRIBED | Already subscribed |
| 106 | LIMIT_EXCEEDED | Limit exceeded |
| 107 | BAD_REQUEST | Bad request |
| 108 | NOT_AVAILABLE | Not available |
| 109 | TOKEN_EXPIRED | Token expired |
| 110 | EXPIRED | Expired |
| 111 | TOO_MANY_REQUESTS | Too many requests |
| 112 | UNRECOVERABLE_POSITION | Unrecoverable position |

### 7.2 Disconnect Error Codes (DisconnectErrorCode)

Status codes in WebSocket close frames:

| Range | Behavior |
|-------|----------|
| 3000-3499 | Should reconnect |
| 3500-3999 | Should not reconnect (fatal error) |
| 4500-4999 | Should not reconnect |
| Other | Should reconnect |

### 7.3 RequestError

Exception types for client operations:

```kotlin
sealed class RequestError : Exception() {
    data class ErrorResponse(val code: Int, message: String)  // Server-returned error
    data class UnexpectedReply(val reply: Reply)               // Unexpected reply type
    data object Timeout                                        // Request timed out
    data object Cancelled                                      // Operation cancelled
    data object ConnectionClosed                               // Connection closed
}
```

---

## 8. Event System

### 8.1 Client Events

| Event | Trigger | Data |
|-------|---------|------|
| `onConnecting` | State changes to CONNECTING | code, reason |
| `onConnected` | Handshake successful | clientId, version, data |
| `onDisconnected` | State changes to DISCONNECTED | code, reason |
| `onError` | WebSocket error / decode error | ErrorEvent (Exception) |
| `onMessage` | Server-sent message (not channel-bound) | MessageEvent (data) |

### 8.2 Subscription Events

| Event | Trigger | Data |
|-------|---------|------|
| `onSubscribing` | State changes to SUBSCRIBING | channel, code, reason |
| `onSubscribed` | Subscription successful | channel, data, streamPosition, ... |
| `onUnsubscribed` | Unsubscribed / kicked | channel, code, reason |
| `onPublication` | Received channel message | data, offset, info, tags, channel |
| `onJoin` | Client joined channel | channel, info |
| `onLeave` | Client left channel | channel, info |
| `onError` | Subscription-related error | SubscriptionErrorEvent |

### 8.3 Server Subscription Events

| Event | Trigger | Data |
|-------|---------|------|
| `onServerSubscribed` | Server subscribes client | channel, data, streamPosition, ... |
| `onServerSubscribing` | Server subscription pending | channel |
| `onServerUnsubscribed` | Server unsubscribes client | channel |
| `onServerPublication` | Server push on channel | channel, data, offset, info, tags |
| `onServerJoin` | Join on server-subscribed channel | channel, info |
| `onServerLeave` | Leave on server-subscribed channel | channel, info |

---

## 9. Usage Example

```kotlin
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        token = "your-jwt-token",
        name = "my-app",
        version = "1.0.0",
    )
)

client.onConnected { event ->
    println("Connected: clientId=${event.clientId}")
}

client.onDisconnected { event ->
    println("Disconnected: code=${event.code}, reason=${event.reason}")
}

// Subscribe to a channel
val sub = client.newSubscription("chat:room1")
sub.onPublication { event ->
    println("Message: ${event.data}")
}
sub.onJoin { event ->
    println("Joined: ${event.info.user}")
}
sub.subscribe()

// Connect
client.connect()

// Wait for connection
client.ready()

// Publish a message
client.publish("chat:room1", buildJsonObject {
    put("text", "Hello!")
})

// RPC call
val result = client.rpc("getUserProfile", buildJsonObject {
    put("userId", 123)
})

// History
val history = client.history("chat:room1", limit = 10)

// Presence stats
val stats = client.presenceStats("chat:room1")

// Cleanup
sub.unsubscribe()
client.removeSubscription(sub)
client.disconnect()
client.close()
```
