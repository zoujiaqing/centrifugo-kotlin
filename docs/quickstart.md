# Quick Start

## Dependencies

### Gradle (composite build)

Add to your `settings.gradle.kts`:

```kotlin
includeBuild("../centrifugo-kotlin")
```

Add the dependency in your module's `build.gradle.kts`:

```kotlin
commonMain.dependencies {
    implementation("com.centrifugo:centrifugo-kotlin")
}
```

## Protocol Selection

The SDK supports two protocols: **Protobuf** (default) and **JSON**.

```kotlin
// Protobuf (default) — binary protocol, more efficient
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(token = "your-jwt-token")
)

// JSON — text protocol, easier to debug
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        protocol = Protocol.JSON,
        token = "your-jwt-token",
    )
)
```

When using Protobuf, the SDK automatically sets the `Sec-WebSocket-Protocol: centrifuge-protobuf` header.

## Basic Connection

```kotlin
import com.centrifugo.client.*

val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        token = "your-jwt-token",
    )
)

// Listen for connection events
client.onConnecting { event ->
    println("Connecting: code=${event.code}, reason=${event.reason}")
}

client.onConnected { event ->
    println("Connected: clientId=${event.clientId}, version=${event.version}")
}

client.onDisconnected { event ->
    println("Disconnected: code=${event.code}, reason=${event.reason}")
}

client.onError { error ->
    println("Error: ${error.error.message}")
}

// Initiate connection
client.connect()
```

## Subscribe to a Channel

```kotlin
val sub = client.newSubscription("chat:room1")

sub.onSubscribed { event ->
    println("Subscribed to ${event.channel}")
}

sub.onPublication { event ->
    println("Received: ${event.data}")
}

sub.onUnsubscribed { event ->
    println("Unsubscribed from ${event.channel}: ${event.reason}")
}

sub.onJoin { event ->
    println("User joined: ${event.info.user}")
}

sub.onLeave { event ->
    println("User left: ${event.info.user}")
}

// Start subscription
sub.subscribe()
```

## Publish Messages

```kotlin
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

client.publish("chat:room1", buildJsonObject {
    put("text", "Hello, world!")
    put("sender", "user123")
})
```

## RPC Calls

```kotlin
val result = client.rpc("getUserProfile", buildJsonObject {
    put("userId", 123)
})
println("RPC result: $result")
```

## History

```kotlin
val history = client.history("chat:room1", limit = 10)
for (pub in history.publications) {
    println("Message: ${pub.data}")
}
println("Epoch: ${history.epoch}, Offset: ${history.offset}")

// History with stream position (for pagination)
val more = client.history(
    "chat:room1",
    limit = 10,
    since = StreamPosition(offset = history.offset, epoch = history.epoch),
)
```

## Presence

```kotlin
// Get list of currently connected clients
val presence = client.presence("chat:room1")
for ((clientId, info) in presence.clients) {
    println("Client: $clientId, user: ${info.user}")
}

// Get presence statistics (count only)
val stats = client.presenceStats("chat:room1")
println("Clients: ${stats.numClients}, Users: ${stats.numUsers}")
```

## Send (fire-and-forget)

```kotlin
client.send(buildJsonObject {
    put("type", "typing")
    put("channel", "chat:room1")
})
```

## Wait for Connection / Subscription Ready

```kotlin
// Wait until client is connected
client.ready()

// Wait until subscription is subscribed
sub.ready()
```

## Server-side Subscriptions

Handle subscriptions that are managed by the Centrifugo server:

```kotlin
client.onServerSubscribed { event ->
    println("Server subscribed to ${event.channel}")
}

client.onServerPublication { event ->
    println("Server push on ${event.channel}: ${event.data}")
}

client.onServerUnsubscribed { event ->
    println("Server unsubscribed from ${event.channel}")
}

client.onServerJoin { event ->
    println("Join on ${event.channel}: ${event.info.user}")
}

client.onServerLeave { event ->
    println("Leave on ${event.channel}: ${event.info.user}")
}
```

## Message Events

Handle server-sent messages (not bound to a channel):

```kotlin
client.onMessage { event ->
    println("Message from server: ${event.data}")
}
```

## Disconnect

```kotlin
// Unsubscribe
sub.unsubscribe()
client.removeSubscription(sub)

// Disconnect and release resources
client.disconnect()
client.close()
```

## Command Batching

Batch multiple commands into a single WebSocket frame for better performance:

```kotlin
client.startBatching()

// These commands are collected but not sent yet
client.publish("chat:room1", buildJsonObject { put("text", "msg1") })
client.publish("chat:room2", buildJsonObject { put("text", "msg2") })
client.rpc("method1", buildJsonObject { put("key", "value") })

// All collected commands are sent in one batch
client.stopBatching()
```

## Update Connection Data / Headers

```kotlin
// Update connection data (affects next connection attempt)
client.setData(buildJsonObject {
    put("role", "admin")
})

// Update connection headers
client.setHeaders(mapOf("X-Custom-Header" to "value"))
```

## Dynamic Token Update

```kotlin
// Option 1: Set token before connecting
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
)
client.setToken(fetchTokenFromServer())
client.connect()

// Option 2: Use getToken callback for automatic token refresh
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        getToken = { fetchTokenFromServer() },
    )
)
client.connect()
```

## Full Example

```kotlin
import com.centrifugo.client.*
import com.centrifugo.client.protocol.StreamPosition
import kotlinx.coroutines.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

fun main() = runBlocking {
    val client = CentrifugoClient(
        url = "ws://localhost:8000/connection/websocket",
        config = CentrifugoConfig(
            token = "your-jwt-token",
            name = "my-app",
            version = "1.0.0",
        )
    )

    client.onConnecting { event ->
        println("[Connecting] code=${event.code}, reason=${event.reason}")
    }

    client.onConnected { event ->
        println("[Connected] clientId=${event.clientId}")
    }

    client.onDisconnected { event ->
        println("[Disconnected] code=${event.code}, reason=${event.reason}")
    }

    // Create and subscribe to a channel
    val sub = client.newSubscription("chat:general")
    sub.onPublication { event ->
        println("[Message] channel=${event.channel}, data=${event.data}")
    }
    sub.onJoin { event ->
        println("[Join] ${event.info.user}")
    }
    sub.subscribe()

    // Connect
    client.connect()

    // Wait for connection, then publish
    client.ready()
    client.publish("chat:general", buildJsonObject {
        put("text", "Hello from centrifugo-kotlin!")
    })

    // Get presence
    val stats = client.presenceStats("chat:general")
    println("Online: ${stats.numClients} clients, ${stats.numUsers} users")

    // Keep running
    delay(Long.MAX_VALUE)
}
```

## Configuration Reference

### CentrifugoConfig

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `protocol` | `Protocol` | `PROTOBUF` | Wire protocol (`PROTOBUF` or `JSON`) |
| `token` | `String` | `""` | JWT authentication token |
| `name` | `String` | `"centrifugo-kotlin"` | Client name |
| `version` | `String` | `""` | Client version |
| `data` | `JsonElement?` | `null` | Custom data sent on connect |
| `headers` | `Map<String, String>` | `emptyMap()` | Custom headers sent on connect |
| `timeout` | `Duration` | `10s` | Request timeout |
| `minReconnectDelay` | `Duration` | `500ms` | Minimum reconnect delay |
| `maxReconnectDelay` | `Duration` | `20s` | Maximum reconnect delay |
| `maxServerPingDelay` | `Duration` | `10s` | Max server ping delay |
| `getToken` | `(suspend () -> String)?` | `null` | Callback for automatic token refresh |

### SubscriptionConfig

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
| `minResubscribeDelay` | `Duration` | `500ms` | Minimum resubscribe delay |
| `maxResubscribeDelay` | `Duration` | `20s` | Maximum resubscribe delay |

### BackoffReconnect

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `factor` | `Double` | `2.0` | Exponential backoff factor |
| `minDelay` | `Duration` | `500ms` | Minimum reconnect delay |
| `maxDelay` | `Duration` | `20s` | Maximum reconnect delay |

## Error Handling

```kotlin
import com.centrifugo.client.RequestError

try {
    client.publish("channel", buildJsonObject { put("key", "value") })
} catch (e: RequestError.ErrorResponse) {
    println("Server error: code=${e.code}, message=${e.message}")
} catch (e: RequestError.Timeout) {
    println("Request timed out")
} catch (e: RequestError.ConnectionClosed) {
    println("Connection is closed")
}
```
