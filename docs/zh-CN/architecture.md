# centrifugo-kotlin 架构规范

## 1. 概述

centrifugo-kotlin 是一个 Kotlin Multiplatform (KMP) 实现的 [Centrifugo](https://centrifugal.dev/) 实时消息 SDK，同时提供**客户端**和**服务端**能力。基于 Centrifugo 官方协议 v2 实现，使用 Kotlin Coroutines 作为异步运行时。

### 1.1 目标平台

| 平台 | Target | WebSocket 引擎 |
|------|--------|---------------|
| Android | androidTarget | Ktor + OkHttp |
| iOS x86_64 | iosX64 | Ktor + Darwin |
| iOS ARM64 | iosArm64 | Ktor + Darwin |
| iOS Simulator | iosSimulatorArm64 | Ktor + Darwin |
| JS (Browser / Node.js) | js(IR) | Ktor + JS WebSocket |

### 1.2 协议支持

仅支持 **JSON 协议**。Centrifugo 协议 v2 的 JSON 编码使用换行符分隔多条消息（newline-delimited JSON）。

### 1.3 技术栈

| 组件 | 选型 | 版本 |
|------|------|------|
| 语言 | Kotlin | 2.1.21 |
| 异步 | kotlinx-coroutines | 1.10.1 |
| 网络 | Ktor Client + WebSockets | 3.1.1 |
| 序列化 | kotlinx-serialization-json | 1.8.0 |
| 构建 | Gradle + KMP Plugin | 8.11.1 |

---

## 2. 项目结构

```
centrifugo-kotlin/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── gradle/libs.versions.toml
├── docs/
│   ├── architecture.md
│   ├── api.md
│   └── quickstart.md
└── src/
    ├── commonMain/kotlin/com/centrifugo/client/
    │   ├── CentrifugoClient.kt                 # 客户端主类
    │   ├── Subscription.kt                     # 订阅管理
    │   ├── Config.kt                           # 客户端配置 + 重连策略
    │   ├── Events.kt                           # 事件数据类
    │   ├── Errors.kt                           # 错误码 + 异常类型
    │   ├── WebSocketHandler.kt                 # WebSocket 读写协程
    │   ├── MessageStore.kt                     # 命令批量队列
    │   ├── protocol/
    │   │   ├── Protocol.kt                     # Centrifugo 协议数据类
    │   │   ├── Command.kt                      # 命令密封类
    │   │   ├── Reply.kt                        # 回复密封类 + Push 解析
    │   │   └── Codec.kt                        # JSON 编解码
    │   └── server/
    │       ├── Server.kt                       # 服务端主类 + 会话管理
    │       └── ServerTypes.kt                  # 服务端类型定义
    ├── androidMain/
    │   ├── AndroidManifest.xml
    │   └── kotlin/.../server/TimeUtils.kt      # Android 时间实现
    ├── iosMain/kotlin/.../server/TimeUtils.kt  # iOS 时间实现
    └── jsMain/kotlin/.../server/TimeUtils.kt   # JS 时间实现
```

---

## 3. 分层架构

```
┌─────────────────────────────────────────────────────────┐
│                    应用层 (Application)                   │
│         CentrifugoClient / Server — 面向用户的 API         │
├─────────────────────────────────────────────────────────┤
│                   会话层 (Session)                        │
│    WebSocketHandler / ServerSession — 连接生命周期管理      │
├─────────────────────────────────────────────────────────┤
│                   协议层 (Protocol)                       │
│  Command / Reply / PushMessage — 类型安全的协议抽象         │
├─────────────────────────────────────────────────────────┤
│                   编解码层 (Codec)                        │
│  RawCommand / RawReply / CentrifugoJson — 换行分隔 JSON   │
├─────────────────────────────────────────────────────────┤
│                   传输层 (Transport)                      │
│          Ktor WebSocket (OkHttp / Darwin / JS)           │
└─────────────────────────────────────────────────────────┘
```

---

## 4. 协议层

### 4.1 Centrifugo 协议 v2

Centrifugo 使用请求-响应模型，通过 WebSocket 传输。每条消息是一个 JSON 对象，多条消息以换行符 `\n` 分隔。

**请求 (Command)**：客户端 → 服务端，每个请求携带递增的 `id` 字段。

**回复 (Reply)**：服务端 → 客户端，`id > 0` 为命令回复，`id == 0` 为服务端主动推送 (Push)。

**空回复**：`id == 0` 且所有字段为空，作为 Ping 信号。客户端收到后应回复一个空命令作为 Pong。

### 4.2 线上 JSON 格式

所有数据类使用 `@Serializable` 注解，通过 `kotlinx.serialization` 序列化：

```kotlin
val CentrifugoJson = Json {
    ignoreUnknownKeys = true
    encodeDefaults = false
}
```

字段命名映射规则：
- 默认使用原始字段名（与 Centrifugo 服务端 camelCase 一致）
- 特殊映射：`publication` → `"pub"` (避免 Kotlin 关键字)

### 4.3 类型安全抽象

协议层提供两级抽象：

**Raw 层** — 直接映射 JSON 结构，用于序列化/反序列化：
```kotlin
@Serializable
data class RawCommand(val id: Int, val connect: ConnectRequest?, val subscribe: SubscribeRequest?, ...)
@Serializable
data class RawReply(val id: Int, val error: Error?, val push: RawPush?, val connect: ConnectResult?, ...)
```

**Sealed 层** — 类型安全的 ADT (代数数据类型)，用于业务逻辑：
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

转换方法：
- `Command.toRawCommand(id)` — Sealed → Raw (发送前)
- `Reply.fromRawReply(raw)` — Raw → Sealed (接收后)
- `PushMessage.fromRawPush(raw)` — RawPush → PushMessage

---

## 5. 客户端架构

### 5.1 状态机

客户端维护一个三状态有限状态机：

```
                  connect()
DISCONNECTED ──────────────→ CONNECTING
      ↑                          │
      │                          │ handshake 成功
      │   disconnect()           ↓
      ←──────────────────── CONNECTED
      ←──────── 断线(不重连) ────←
                                 │
                    断线(重连) ───→ CONNECTING
```

| 状态 | 描述 |
|------|------|
| `DISCONNECTED` | 初始状态 / 已断开，无活跃连接 |
| `CONNECTING` | 正在建立 WebSocket 连接或等待重连 |
| `CONNECTED` | WebSocket 已连接且握手完成 |

### 5.2 连接生命周期

```
connect() 调用
  │
  ↓
moveToConnecting() → 触发 onConnecting 回调
  │
  ↓
startConnectionCycle() → 启动协程循环
  │
  ├── [首次] 直接尝试连接
  ├── [重连] 按 BackoffReconnect 策略延迟
  │
  ↓
doSingleConnection()
  │
  ├── 创建 WebSocketHandler
  ├── Ktor httpClient.webSocket(url) 建立连接
  ├── 发送 ConnectRequest (握手)
  ├── 等待 ConnectResult
  │     │
  │     ├── 成功 → moveToConnected() → subscribeAll() → 等待断线
  │     ├── 临时错误 → reconnect = true → 重试
  │     └── 永久错误 → reconnect = false → 终止
  │
  ↓
连接断开
  │
  ├── shouldReconnect = true → 回到 CONNECTING 状态，重连
  └── shouldReconnect = false → moveToDisconnected()
```

### 5.3 WebSocketHandler

`WebSocketHandler` 负责 WebSocket 连接的并发读写，是客户端的核心引擎：

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
                │  └─────────┘        │
                │                     │
                │  ┌─────────┐        │    WebSocket
                │  │ Reader  │←───────│──← session.incoming
                │  │ Coroutine│       │
                │  └────┬────┘        │
                │       │             │
                │       ├── id > 0 → replyMap 匹配 → CompletableDeferred.complete()
                │       ├── id = 0 → onPush() 回调
                │       └── Empty  → pingChannel → Writer 发送 Pong
                └─────────────────────┘
```

**关键机制：**

- **命令 ID 管理**：AtomicInt 自增，确保每个命令有唯一 ID（跳过 0）
- **批量发送**：Writer 协程每次最多批量处理 32 条命令
- **超时管理**：每个请求启动独立超时协程，到期后从 replyMap 移除并返回 Timeout 错误
- **Ping/Pong**：Reader 收到 Empty Reply → 通过 pingChannel 通知 Writer 发送 Empty Command

### 5.4 MessageStore

`MessageStore` 是命令缓冲队列，在连接建立过程中暂存待发送的命令：

```kotlin
internal class MessageStore(timeout: Duration) {
    fun send(command: Command): CompletableDeferred<Result<Reply>>  // 入队 + 过期清理
    fun getNext(now: ValueTimeMark): MessageStoreItem?              // 出队（跳过过期项）
}
```

- 基于 `ArrayDeque` 实现 FIFO 队列
- 每条消息有 deadline（当前时间 + timeout）
- 入队时自动清理队头过期消息
- 出队时再次检查过期

### 5.5 重连策略

```kotlin
interface ReconnectStrategy {
    fun timeBeforeNextAttempt(attempt: Int): Duration
}

data class BackoffReconnect(
    val factor: Double = 2.0,
    val minDelay: Duration = 200ms,
    val maxDelay: Duration = 20s,
) : ReconnectStrategy
```

延迟计算：`delay = clamp(minDelay * factor^attempt, minDelay, maxDelay)`

| attempt | delay |
|---------|-------|
| 0 | 0 (首次不延迟) |
| 1 | 400ms |
| 2 | 800ms |
| 3 | 1.6s |
| 4 | 3.2s |
| 5 | 6.4s |
| 6 | 12.8s |
| 7+ | 20s (上限) |

---

## 6. 订阅架构

### 6.1 订阅状态机

```
                subscribe()
UNSUBSCRIBED ──────────────→ SUBSCRIBING
      ↑                           │
      │                           │ SubscribeResult 成功
      │  unsubscribe()            ↓
      ←───────────────────── SUBSCRIBED
      ←──── 服务端 Unsubscribe ──←
```

### 6.2 Subscription 类

`Subscription` 是轻量级句柄，通过 `id` 引用 `CentrifugoClient` 内部的 `SubscriptionInner`：

```kotlin
class Subscription(val channel: String, client: CentrifugoClient, id: Int) {
    val state: SubscriptionState
    suspend fun subscribe()
    suspend fun unsubscribe()
    suspend fun publish(data: JsonElement)
    fun onPublication(callback)
}
```

### 6.3 自动重新订阅

当连接建立（或重连成功）后，`CentrifugoClient.subscribeAll()` 遍历所有状态非 `UNSUBSCRIBED` 的订阅，自动发送 SubscribeRequest。

### 6.4 Push 分发

服务端推送的 Publication 按 channel 分发到对应的 `SubscriptionInner.handlePublication()`：

```
WebSocketHandler.onPush(Reply.Push)
  → CentrifugoClient.handlePush()
    → subNameToId[channel] → subscriptions[id]
      → SubscriptionInner.handlePublication()
        → onPublication 回调
```

---

## 7. 服务端架构

### 7.1 Server 类

`Server` 是服务端入口，注册 handler 后对每个 WebSocket 连接调用 `serve()`：

```kotlin
class Server {
    fun onConnect(handler: suspend (ConnectContext) -> Unit)
    fun onDisconnect(handler: (DisconnectContext) -> Unit)
    fun addRpcMethod(name: String, handler: suspend (RpcContext, JsonElement?) -> JsonElement?)
    fun addChannel(name: String, handler: suspend (SubContext) -> Flow<JsonElement?>)
    fun onPublication(name: String, handler: suspend (PublishContext, JsonElement?) -> Unit)
    suspend fun serve(session: DefaultWebSocketSession, params: ServeParams)
}
```

### 7.2 会话生命周期

```
serve(session, params)
  │
  ├── 创建 ServerSession (state = INITIAL)
  │
  ├── 启动 Writer 协程：replyChannel → encode → session.send()
  ├── 启动 Ping 协程：定时发送 Empty Reply, 检测 Pong 超时
  ├── 启动 Reader 协程：session.incoming → decode → processCommand()
  │
  ↓ 等待 Disconnect 信号
  │
  ├── cleanup()：取消所有订阅，触发 onDisconnect
  └── session.close(CloseReason)
```

### 7.3 ServerSession 状态机

```
         ConnectRequest
INITIAL ──────────────→ CONNECTED
   │                       │
   │ 非Connect命令          │ 再次Connect
   ↓                       ↓
 Disconnect              Disconnect
```

### 7.4 命令处理

| 命令 | 处理方式 |
|------|---------|
| Connect | 调用 onConnect handler 验证 → 返回 ConnectResult |
| Rpc | 路由匹配 → 异步执行 handler → 返回 RpcResult |
| Publish | 路由匹配 → 异步执行 handler → 返回 PublishResult |
| Subscribe | 路由匹配 → 启动 Flow 收集协程 → 推送 Publication → Flow 结束时发送 Unsubscribe Push |
| Unsubscribe | 取消订阅 Job → 返回 UnsubscribeResult |
| Empty | 客户端 Pong，忽略 |

### 7.5 路由匹配

支持 `{param}` 路径参数占位符，精确匹配优先：

```kotlin
server.addRpcMethod("user/{id}/profile") { ctx, data ->
    val userId = ctx.params["id"]
    // ...
}
```

匹配算法：按 `/` 分割模式和输入值，逐段比较。`{...}` 段匹配任意值并提取为参数。

### 7.6 并发控制

| 限制 | 值 | 说明 |
|------|----|------|
| 最大并发 RPC/Publish | 10 | Semaphore 控制 |
| 最大订阅数 | 128 | 每连接上限 |
| Ping 间隔 | 25s | 可配置，可禁用 |
| Pong 超时 | 10s | 可配置 |

---

## 8. 错误体系

### 8.1 客户端错误码 (ClientErrorCode)

应用层错误，由服务端在 Reply.Error 中返回：

| 码 | 名称 | 说明 |
|---|------|------|
| 100 | INTERNAL | 服务器内部错误 |
| 101 | UNAUTHORIZED | 未授权 |
| 102 | UNKNOWN_CHANNEL | 未知频道 |
| 103 | PERMISSION_DENIED | 权限不足 |
| 104 | METHOD_NOT_FOUND | RPC 方法不存在 |
| 105 | ALREADY_SUBSCRIBED | 重复订阅 |
| 106 | LIMIT_EXCEEDED | 超出限制 |
| 107 | BAD_REQUEST | 请求格式错误 |
| 108 | NOT_AVAILABLE | 功能不可用 |
| 109 | TOKEN_EXPIRED | Token 已过期 |
| 110 | EXPIRED | 已过期 |
| 111 | TOO_MANY_REQUESTS | 请求过于频繁 |
| 112 | UNRECOVERABLE_POSITION | 无法恢复的位置 |

### 8.2 断连错误码 (DisconnectErrorCode)

WebSocket 关闭帧中的状态码：

| 范围 | 行为 |
|------|------|
| 3000-3499 | 应该重连 |
| 3500-3999 | 不应重连（致命错误） |
| 4500-4999 | 不应重连 |
| 其他 | 应该重连 |

### 8.3 RequestError

客户端操作的异常类型：

```kotlin
sealed class RequestError : Exception() {
    data class ErrorResponse(val code: Int, message: String)
    data class UnexpectedReply(val reply: Reply)
    data object Timeout
    data object Cancelled
    data object ConnectionClosed
}
```

---

## 9. 事件系统

### 9.1 客户端事件

| 事件 | 触发时机 | 数据 |
|------|---------|------|
| `onConnecting` | 状态转为 CONNECTING | code, reason |
| `onConnected` | 握手成功 | clientId, version, data |
| `onDisconnected` | 状态转为 DISCONNECTED | code, reason |
| `onError` | WebSocket 错误 / 解码错误 | Exception |

### 9.2 订阅事件

| 事件 | 触发时机 | 数据 |
|------|---------|------|
| `onSubscribing` | 状态转为 SUBSCRIBING | channel, code, reason |
| `onSubscribed` | 订阅成功 | channel, data |
| `onUnsubscribed` | 取消订阅 / 被踢 | channel, code, reason |
| `onPublication` | 收到频道消息 | data, offset, info, tags, channel |
| `onError` | 订阅相关错误 | Exception |

---

## 10. 使用示例

### 10.1 客户端

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

val sub = client.newSubscription("chat:room1")
sub.onPublication { event ->
    println("Message: ${event.data}")
}
sub.subscribe()

client.connect()

client.publish("chat:room1", buildJsonObject {
    put("text", "Hello!")
})

val result = client.rpc("getUserProfile", buildJsonObject {
    put("userId", 123)
})

sub.unsubscribe()
client.removeSubscription(sub)
client.disconnect()
client.close()
```

### 10.2 服务端

```kotlin
val server = Server()

server.onConnect { ctx ->
    if (ctx.token != "valid-token") {
        throw ServerError(DisconnectErrorCode.INVALID_TOKEN, "invalid token")
    }
    println("Client connected: ${ctx.clientId}")
}

server.onDisconnect { ctx ->
    println("Client disconnected: ${ctx.clientId}")
}

server.addRpcMethod("echo") { ctx, data ->
    data
}

server.addRpcMethod("user/{id}/profile") { ctx, data ->
    val userId = ctx.params["id"]
    buildJsonObject { put("name", "User $userId") }
}

server.addChannel("ticker") { ctx ->
    flow {
        var i = 0
        while (true) {
            emit(buildJsonObject { put("tick", i++) })
            delay(1.seconds)
        }
    }
}
```
