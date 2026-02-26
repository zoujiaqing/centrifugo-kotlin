# centrifugo-kotlin

Kotlin Multiplatform client SDK for [Centrifugo](https://centrifugal.dev/) and [Centrifuge](https://github.com/centrifugal/centrifuge) real-time messaging.

[中文文档](README.zh-CN.md)

## Features

- **Kotlin Multiplatform** - Android, iOS (arm64, x64, simulator), JS (Browser, Node.js)
- **Dual Protocol** - JSON and Protobuf (default), auto-negotiated via WebSocket subprotocol
- **Full Client API** - Connect, subscribe, publish, RPC, history, presence, presence stats, send, batching
- **Server-side Subscriptions** - Handle subscriptions managed by the server
- **Automatic Reconnection** - Exponential backoff with configurable delays
- **Coroutines-first** - Fully async API using Kotlin Coroutines
- **Type-safe** - Sealed classes for protocol commands/replies, kotlinx.serialization
- **Publication Tags Filter** - Server-side message filtering by tags using FilterNode expressions
- **Multiple Transport Support** - WebSocket, HTTP Streaming, and SSE with automatic fallback
- **Centrifugo Protocol Support** - v6, v5, v4 (bidirectional protocol)

> **Note**: This SDK supports both **Centrifugo** (standalone server) and **Centrifuge** (Go library for building custom servers). Both use the same client protocol, so the SDK works with either.

## Protocol Version Support

| Feature | v6 | v5 | v4 |
|---------|----|----|-----|
| WebSocket Transport | ✅ | ✅ | ✅ |
| HTTP Streaming | ✅ | ✅ | ✅ |
| SSE Transport | ✅ | ✅ | ✅ |
| Automatic Transport Fallback | ✅ | ✅ | ❌ |
| Publication Tags Filter | ✅ | ✅ | ❌ |
| Delta Compression | ✅ | ✅ | ✅ |
| Connection Batching | ✅ | ✅ | ❌ |
| Server-side Subscriptions | ✅ | ✅ | ✅ |

## API Comparison

Below is a comparison of client SDK features across different Centrifugo official SDKs:

| Feature | Official API | centrifugo-kotlin | Java | Swift | JS | Go |
|---------|-------------|-------------------|------|-------|----|----|
| **Connection** |
| connect/disconnect | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| setToken/setData/setHeaders | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| automatic reconnect | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| client state changes | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| command-reply | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| command timeouts | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| async pushes | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| ping-pong | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| token refresh | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| handle disconnect | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| server-side subs | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| batching API | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| headers (emulation) | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| **Subscription** |
| subscribe/unsubscribe | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| subscription options | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| auto resubscribe | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| subscription states | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| subscription token refresh | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| handle unsubscribe advice | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| subscription registry | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| delta compression | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| publication tags filter | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| **Messaging** |
| publish (channel) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| rpc | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| send | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| history | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| presence | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| presenceStats | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| **Transports** |
| WebSocket | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| HTTP Stream | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| SSE | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| Auto fallback | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ |

> ✅ = Supported | ❌ = Not supported |空白 = Not applicable

For detailed API documentation, see [docs/api.md](docs/api.md).

## Tech Stack

| Component | Version |
|-----------|---------|
| Kotlin | 2.1.21 |
| Ktor Client (WebSocket) | 3.1.1 |
| kotlinx-coroutines | 1.10.1 |
| kotlinx-serialization-json | 1.8.0 |
| pbandk (Protobuf runtime) | 0.16.0 |

## Platform Support

| Platform | WebSocket Engine |
|----------|-----------------|
| Android | Ktor + OkHttp |
| iOS arm64 | Ktor + Darwin |
| iOS x64 | Ktor + Darwin |
| iOS Simulator arm64 | Ktor + Darwin |
| JS (Browser / Node.js) | Ktor + JS WebSocket |

## Quick Start

```kotlin
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        token = "your-jwt-token",
    )
)

client.onConnected { println("Connected: ${it.clientId}") }

val sub = client.newSubscription("chat:room1")
sub.onPublication { println("Message: ${it.data}") }
sub.subscribe()

client.connect()
```

By default, the SDK uses Protobuf protocol. To use JSON:

```kotlin
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        protocol = Protocol.JSON,
        token = "your-jwt-token",
    )
)
```

## Installation

### Composite Build

In `settings.gradle.kts`:

```kotlin
includeBuild("../centrifugo-kotlin")
```

In module `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation("com.centrifugo:centrifugo-kotlin")
}
```

## Project Structure

```
src/
├── commonMain/kotlin/com/centrifugo/client/
│   ├── CentrifugoClient.kt          # Client main class
│   ├── Subscription.kt               # Subscription management
│   ├── Config.kt                     # Configuration + reconnect strategy
│   ├── Events.kt                     # Event data classes
│   ├── Errors.kt                     # Error codes + exception types
│   ├── WebSocketHandler.kt           # WebSocket read/write coroutines
│   ├── EmulationHandler.kt           # HTTP Stream / SSE handler
│   ├── MessageStore.kt               # Command buffer queue
│   └── protocol/
│       ├── Protocol.kt               # Protocol data types + FilterNode
│       ├── Command.kt                # Command sealed class
│       ├── Reply.kt                  # Reply sealed class + Push parsing
│       ├── Codec.kt                  # Codec interface + JSON implementation
│       ├── ProtobufCodec.kt          # Protobuf codec implementation
│       └── protobuf/
│           └── client.kt             # Generated Protobuf message classes (pbandk)
├── androidMain/                       # Android platform (OkHttp engine)
├── iosMain/                           # iOS platform (Darwin engine)
└── jsMain/                            # JS platform (JS WebSocket engine)
```

## Architecture

The SDK follows a layered architecture:

```
Application    CentrifugoClient
Session        WebSocketHandler
Protocol       Command / Reply / PushMessage (sealed classes)
Codec          Codec interface → JsonCodec / ProtobufCodec
Transport      Ktor WebSocket (OkHttp / Darwin / JS)
```

Client state machine: `DISCONNECTED → CONNECTING → CONNECTED`

Subscription state machine: `UNSUBSCRIBED → SUBSCRIBING → SUBSCRIBED`

Automatic reconnection with exponential backoff (500ms → 20s cap).

## Documentation

- [Quick Start Guide](docs/quickstart.md) - Usage examples and configuration reference
- [API Reference](docs/api.md) - Complete API documentation
- [Architecture Spec](docs/architecture.md) - Detailed design and implementation notes

## License

TBD
