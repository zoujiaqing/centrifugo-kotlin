# centrifugo-kotlin

[Centrifugo](https://centrifugal.dev/) 和 [Centrifuge](https://github.com/centrifugal/centrifuge) 实时消息的 Kotlin Multiplatform 客户端 SDK。

[English](README.md)

## 特性

- **Kotlin Multiplatform** - Android、iOS (arm64, x64, simulator)、JS (Browser, Node.js)
- **双协议支持** - JSON 和 Protobuf（默认），通过 WebSocket 子协议自动协商
- **完整客户端 API** - 连接、订阅、发布、RPC、历史消息、在线状态、在线统计、发送、批处理
- **服务端订阅** - 支持处理服务端管理的订阅
- **自动重连** - 可配置延迟的指数退避策略
- **协程优先** - 基于 Kotlin Coroutines 的全异步 API
- **类型安全** - 使用密封类抽象协议命令/回复，kotlinx.serialization 序列化
- **消息标签过滤** - 使用 FilterNode 表达式在服务端按标签过滤消息
- **多传输支持** - WebSocket、HTTP Streaming、SSE，支持自动回退
- **Centrifugo 协议支持** - v6, v5, v4 (双向协议)

> **注意**: 本 SDK 同时支持 **Centrifugo**（独立服务器）和 **Centrifuge**（用于构建自定义服务器的 Go 语言库）。两者使用相同的客户端协议，因此本 SDK 可以与两者配合使用。

## 协议版本支持

| 功能 | v6 | v5 | v4 |
|------|----|----|-----|
| WebSocket 传输 | ✅ | ✅ | ✅ |
| HTTP Streaming | ✅ | ✅ | ✅ |
| SSE 传输 | ✅ | ✅ | ✅ |
| 自动传输回退 | ✅ | ✅ | ❌ |
| 消息标签过滤 | ✅ | ✅ | ❌ |
| 增量压缩 | ✅ | ✅ | ✅ |
| 连接批处理 | ✅ | ✅ | ❌ |
| 服务端订阅 | ✅ | ✅ | ✅ |

## API 对比

下面是各 Centrifugo 官方客户端 SDK 的功能对比：

| 功能 | 官方 API | centrifugo-kotlin | Java | Swift | JS | Go |
|------|---------|-------------------|------|-------|----|----|
| **连接** |
| connect/disconnect | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| setToken/setData/setHeaders | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 自动重连 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 连接状态变更 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 命令-回复 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 命令超时 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 异步推送 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| ping-pong | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| token 刷新 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 处理断开通知 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 服务端订阅 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 批处理 API | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| 请求头 (模拟) | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| **订阅** |
| subscribe/unsubscribe | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 订阅选项 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 自动重新订阅 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 订阅状态 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 订阅 token 刷新 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 处理取消订阅通知 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 订阅注册表 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 增量压缩 | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 消息标签过滤 | ✅ | ✅ | ❌ | ❌ | ✅ | ❌ |
| **消息** |
| publish (频道) | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| rpc | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| send | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| history | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| presence | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| presenceStats | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| **传输** |
| WebSocket | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| HTTP Stream | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| SSE | ✅ | ✅ | ✅ | ✅ | ✅ | ✅ |
| 自动回退 | ✅ | ✅ | ❌ | ❌ | ❌ | ❌ |

> ✅ = 支持 | ❌ = 不支持

详细 API 文档见 [docs/api.md](docs/api.md)。

## 技术栈

| 组件 | 版本 |
|------|------|
| Kotlin | 2.1.21 |
| Ktor Client (WebSocket) | 3.1.1 |
| kotlinx-coroutines | 1.10.1 |
| kotlinx-serialization-json | 1.8.0 |
| pbandk (Protobuf 运行时) | 0.16.0 |

## 平台支持

| 平台 | WebSocket 引擎 |
|------|---------------|
| Android | Ktor + OkHttp |
| iOS arm64 | Ktor + Darwin |
| iOS x64 | Ktor + Darwin |
| iOS Simulator arm64 | Ktor + Darwin |
| JS (Browser / Node.js) | Ktor + JS WebSocket |

## 快速开始

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

SDK 默认使用 Protobuf 协议。如需使用 JSON：

```kotlin
val client = CentrifugoClient(
    url = "ws://localhost:8000/connection/websocket",
    config = CentrifugoConfig(
        protocol = Protocol.JSON,
        token = "your-jwt-token",
    )
)
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

## 项目结构

```
src/
├── commonMain/kotlin/com/centrifugo/client/
│   ├── CentrifugoClient.kt          # 客户端主类
│   ├── Subscription.kt               # 订阅管理
│   ├── Config.kt                     # 配置 + 重连策略
│   ├── Events.kt                     # 事件数据类
│   ├── Errors.kt                     # 错误码 + 异常类型
│   ├── WebSocketHandler.kt           # WebSocket 读写协程
│   ├── EmulationHandler.kt           # HTTP Stream / SSE 处理器
│   ├── MessageStore.kt               # 命令缓冲队列
│   └── protocol/
│       ├── Protocol.kt               # 协议数据类型 + FilterNode
│       ├── Command.kt                # 命令密封类
│       ├── Reply.kt                  # 回复密封类 + Push 解析
│       ├── Codec.kt                  # 编解码接口 + JSON 实现
│       ├── ProtobufCodec.kt          # Protobuf 编解码实现
│       └── protobuf/
│           └── client.kt             # 生成的 Protobuf 消息类 (pbandk)
├── androidMain/                       # Android 平台 (OkHttp 引擎)
├── iosMain/                           # iOS 平台 (Darwin 引擎)
└── jsMain/                            # JS 平台 (JS WebSocket 引擎)
```

## 架构

SDK 采用分层架构：

```
应用层         CentrifugoClient
会话层         WebSocketHandler
协议层         Command / Reply / PushMessage (密封类)
编解码层       Codec 接口 → JsonCodec / ProtobufCodec
传输层         Ktor WebSocket (OkHttp / Darwin / JS)
```

客户端状态机：`DISCONNECTED → CONNECTING → CONNECTED`

订阅状态机：`UNSUBSCRIBED → SUBSCRIBING → SUBSCRIBED`

自动重连，采用指数退避策略（500ms → 20s 上限）。

## 文档

- [快速开始](docs/quickstart.md) - 使用示例和配置参考
- [API 参考](docs/api.md) - 完整 API 文档
- [架构规范](docs/architecture.md) - 详细设计和实现说明

## License

TBD
