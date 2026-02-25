# centrifugo-kotlin

[Centrifugo](https://centrifugal.dev/) 实时消息的 Kotlin Multiplatform SDK，同时提供**客户端**和**服务端**能力。

[English](../../README.md)

## 特性

- **Kotlin Multiplatform** - Android、iOS (arm64, x64, simulator)、JS (Browser, Node.js)
- **客户端 SDK** - 连接、订阅、发布、RPC，支持自动重连
- **服务端 SDK** - 构建兼容 Centrifugo 协议的 WebSocket 服务，支持路由匹配
- **JSON 协议** - Centrifugo 协议 v2，换行分隔 JSON
- **协程优先** - 基于 Kotlin Coroutines 和 Flow 的全异步 API
- **类型安全** - 使用密封类抽象协议命令/回复，kotlinx.serialization 序列化

## 技术栈

| 组件 | 版本 |
|------|------|
| Kotlin | 2.1.21 |
| Ktor Client (WebSocket) | 3.1.1 |
| kotlinx-coroutines | 1.10.1 |
| kotlinx-serialization-json | 1.8.0 |

## 平台支持

| 平台 | WebSocket 引擎 |
|------|---------------|
| Android | Ktor + OkHttp |
| iOS arm64 | Ktor + Darwin |
| iOS x64 | Ktor + Darwin |
| iOS Simulator arm64 | Ktor + Darwin |
| JS (Browser / Node.js) | Ktor + JS WebSocket |

## 快速开始

### 客户端

```kotlin
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(token = "your-jwt-token")
)

client.onConnected { println("Connected: ${it.clientId}") }

val sub = client.newSubscription("chat:room1")
sub.onPublication { println("Message: ${it.data}") }
sub.subscribe()

client.connect()
```

### 服务端

```kotlin
val server = Server()

server.onConnect { ctx ->
    println("Client connected: ${ctx.clientId}")
}

server.addRpcMethod("echo") { ctx, data -> data }

server.addChannel("ticker") { ctx ->
    flow {
        var i = 0
        while (true) {
            emit(buildJsonObject { put("tick", i++) })
            delay(1.seconds)
        }
    }
}

// 在 Ktor Server 中使用：
// webSocket("/connection/websocket") { server.serve(this) }
```

## 安装

### Composite Build

在 `settings.gradle.kts` 中：

```kotlin
includeBuild("../centrifugo-kotlin")
```

在模块 `build.gradle.kts` 中：

```kotlin
commonMain.dependencies {
    implementation("com.centrifugo:centrifugo-kotlin")
}
```

## 文档

- [快速开始](quickstart.md) - 使用示例和配置参考
- [API 参考](api.md) - 完整 API 文档
- [架构规范](architecture.md) - 详细设计和实现说明

## License

TBD
