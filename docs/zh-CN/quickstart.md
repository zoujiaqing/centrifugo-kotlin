# 快速开始

## 依赖配置

### Gradle (composite build)

在你的 `settings.gradle.kts` 中添加：

```kotlin
includeBuild("../centrifugo-kotlin")
```

在模块的 `build.gradle.kts` 中添加依赖：

```kotlin
commonMain.dependencies {
    implementation("com.centrifugo:centrifugo-kotlin")
}
```

## 客户端

### 基本连接

```kotlin
import com.centrifugo.client.*

val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        token = "your-jwt-token",
    )
)

// 监听连接事件
client.onConnected { event ->
    println("Connected: clientId=${event.clientId}, version=${event.version}")
}

client.onDisconnected { event ->
    println("Disconnected: code=${event.code}, reason=${event.reason}")
}

client.onError { error ->
    println("Error: ${error.message}")
}

// 发起连接
client.connect()
```

### 订阅频道

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

// 开始订阅
sub.subscribe()
```

### 发布消息

```kotlin
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

client.publish("chat:room1", buildJsonObject {
    put("text", "Hello, world!")
    put("sender", "user123")
})
```

### RPC 调用

```kotlin
val result = client.rpc("getUserProfile", buildJsonObject {
    put("userId", 123)
})
println("RPC result: $result")
```

### 断开连接

```kotlin
// 取消订阅
sub.unsubscribe()
client.removeSubscription(sub)

// 断开并释放资源
client.disconnect()
client.close()
```

### 完整客户端示例

```kotlin
import com.centrifugo.client.*
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

    // 创建并订阅频道
    val sub = client.newSubscription("chat:general")
    sub.onPublication { event ->
        println("[Message] channel=${event.channel}, data=${event.data}")
    }
    sub.subscribe()

    // 连接
    client.connect()

    // 等待连接建立后发消息
    delay(1000)
    client.publish("chat:general", buildJsonObject {
        put("text", "Hello from centrifugo-kotlin!")
    })

    // 保持运行
    delay(Long.MAX_VALUE)
}
```

## 服务端

### 基本服务端

```kotlin
import com.centrifugo.client.server.*
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

val server = Server()

// 连接认证
server.onConnect { ctx ->
    println("Client connecting: ${ctx.clientId}, token=${ctx.token}")
    // 抛出 ServerError 拒绝连接
    // throw ServerError(3500, "invalid token")
}

// 断开回调
server.onDisconnect { ctx ->
    println("Client disconnected: ${ctx.clientId}")
}
```

### 注册 RPC 方法

```kotlin
// 简单 RPC
server.addRpcMethod("echo") { ctx, data ->
    data  // 原样返回
}

// 带路径参数的 RPC
server.addRpcMethod("user/{id}/profile") { ctx, data ->
    val userId = ctx.params["id"]
    buildJsonObject {
        put("name", "User $userId")
        put("status", "online")
    }
}
```

### 注册订阅频道

```kotlin
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

// 静态频道
server.addChannel("notifications") { ctx ->
    flow {
        emit(buildJsonObject { put("message", "Welcome!") })
    }
}

// 带参数的流式频道
server.addChannel("ticker/{symbol}") { ctx ->
    val symbol = ctx.params["symbol"]
    flow {
        var price = 100.0
        while (true) {
            price += (-1..1).random()
            emit(buildJsonObject {
                put("symbol", symbol)
                put("price", price)
            })
            delay(1.seconds)
        }
    }
}
```

### 注册发布处理

```kotlin
server.onPublication("chat/{room}") { ctx, data ->
    val room = ctx.params["room"]
    println("Publish to $room: $data")
    // 处理消息，例如存储到数据库
}
```

### 在 Ktor 中使用

```kotlin
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*

fun main() {
    embeddedServer(Netty, port = 8000) {
        install(WebSockets)

        routing {
            webSocket("/connection/websocket") {
                server.serve(this)
            }

            // 自定义参数
            webSocket("/ws") {
                server.serve(this, ServeParams(
                    pingInterval = 25.seconds,
                    pongTimeout = 10.seconds,
                ))
            }

            // 禁用 ping
            webSocket("/ws-no-ping") {
                server.serve(this, ServeParams().withoutPing())
            }
        }
    }.start(wait = true)
}
```

## 配置参考

### CentrifugoConfig

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `token` | `String` | `""` | JWT 认证 token |
| `name` | `String` | `"centrifugo-kotlin"` | 客户端名称 |
| `version` | `String` | `""` | 客户端版本 |
| `data` | `ByteArray?` | `null` | 连接时携带的自定义数据 |
| `readTimeout` | `Duration` | `5s` | 请求超时时间 |
| `reconnectStrategy` | `ReconnectStrategy` | `BackoffReconnect()` | 重连策略 |

### BackoffReconnect

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `factor` | `Double` | `2.0` | 指数退避因子 |
| `minDelay` | `Duration` | `200ms` | 最小重连延迟 |
| `maxDelay` | `Duration` | `20s` | 最大重连延迟 |

### SubscriptionConfig

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `token` | `String` | `""` | 订阅 token（私有频道） |
| `data` | `JsonElement?` | `null` | 订阅时携带的自定义数据 |

### ServeParams

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `clientId` | `ClientId?` | 自动生成 | 客户端唯一标识 |
| `pingInterval` | `Duration?` | `25s` | Ping 间隔（`null` 禁用） |
| `pongTimeout` | `Duration?` | `10s` | Pong 超时 |

## 错误处理

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

## Token 动态更新

```kotlin
// 初始连接时不需要 token
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
)

// 获取到 token 后设置
val token = fetchTokenFromServer()
client.setToken(token)

// 然后连接
client.connect()
```
