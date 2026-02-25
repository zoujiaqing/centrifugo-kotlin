# API 参考

## 客户端 API

### CentrifugoClient

主客户端类，管理 WebSocket 连接和订阅。

```kotlin
class CentrifugoClient(url: String, config: CentrifugoConfig = CentrifugoConfig())
```

#### 属性

| 属性 | 类型 | 说明 |
|------|------|------|
| `state` | `ClientState` | 当前连接状态 (`DISCONNECTED` / `CONNECTING` / `CONNECTED`) |

#### 连接管理

| 方法 | 签名 | 说明 |
|------|------|------|
| `connect` | `suspend fun connect()` | 发起 WebSocket 连接，触发连接生命周期 |
| `disconnect` | `suspend fun disconnect()` | 主动断开连接，不再自动重连 |
| `setToken` | `fun setToken(token: String)` | 动态更新认证 token |
| `serverInfo` | `fun serverInfo(): ServerInfo` | 获取连接后的服务端信息 |
| `close` | `fun close()` | 释放所有资源（HttpClient、CoroutineScope） |

#### 消息操作

| 方法 | 签名 | 说明 |
|------|------|------|
| `publish` | `suspend fun publish(channel: String, data: JsonElement)` | 向指定频道发布消息 |
| `rpc` | `suspend fun rpc(method: String, data: JsonElement? = null): JsonElement?` | 发起 RPC 调用，返回结果 |

#### 订阅管理

| 方法 | 签名 | 说明 |
|------|------|------|
| `newSubscription` | `fun newSubscription(channel: String, config: SubscriptionConfig = SubscriptionConfig()): Subscription` | 创建新订阅（频道已存在则返回已有订阅） |
| `getSubscription` | `fun getSubscription(channel: String): Subscription?` | 按频道名获取已有订阅 |
| `removeSubscription` | `fun removeSubscription(subscription: Subscription)` | 移除订阅（必须先 unsubscribe） |

#### 事件回调

| 方法 | 签名 | 说明 |
|------|------|------|
| `onConnecting` | `fun onConnecting(callback: (ConnectingEvent) -> Unit)` | 连接中事件 |
| `onConnected` | `fun onConnected(callback: (ConnectedEvent) -> Unit)` | 连接成功事件 |
| `onDisconnected` | `fun onDisconnected(callback: (DisconnectedEvent) -> Unit)` | 断开连接事件 |
| `onError` | `fun onError(callback: (Exception) -> Unit)` | 错误事件 |

---

### Subscription

频道订阅句柄，通过 `CentrifugoClient.newSubscription()` 创建。

```kotlin
class Subscription(val channel: String, ...)
```

#### 属性

| 属性 | 类型 | 说明 |
|------|------|------|
| `channel` | `String` | 频道名称 |
| `state` | `SubscriptionState` | 当前订阅状态 (`UNSUBSCRIBED` / `SUBSCRIBING` / `SUBSCRIBED`) |

#### 操作

| 方法 | 签名 | 说明 |
|------|------|------|
| `subscribe` | `suspend fun subscribe()` | 发起订阅 |
| `unsubscribe` | `suspend fun unsubscribe()` | 取消订阅 |
| `publish` | `suspend fun publish(data: JsonElement)` | 向本频道发布消息 |
| `setTagsFilter` | `fun setTagsFilter(tagsFilter: FilterNode?)` | 运行时更新标签过滤器 |

#### 事件回调

| 方法 | 签名 | 说明 |
|------|------|------|
| `onSubscribing` | `fun onSubscribing(callback: (SubscribingEvent) -> Unit)` | 订阅中事件 |
| `onSubscribed` | `fun onSubscribed(callback: (SubscribedEvent) -> Unit)` | 订阅成功事件 |
| `onUnsubscribed` | `fun onUnsubscribed(callback: (UnsubscribedEvent) -> Unit)` | 取消订阅事件 |
| `onPublication` | `fun onPublication(callback: (PublicationEvent) -> Unit)` | 收到消息事件 |
| `onError` | `fun onError(callback: (Exception) -> Unit)` | 错误事件 |

---

## 服务端 API

### Server

服务端主类，注册处理器后为每个 WebSocket 连接提供服务。

```kotlin
class Server()
```

#### 生命周期

| 方法 | 签名 | 说明 |
|------|------|------|
| `onConnect` | `fun onConnect(handler: suspend (ConnectContext) -> Unit)` | 注册连接认证处理器，抛出 `ServerError` 拒绝连接 |
| `onDisconnect` | `fun onDisconnect(handler: (DisconnectContext) -> Unit)` | 注册断开回调 |
| `serve` | `suspend fun serve(session: DefaultWebSocketSession, params: ServeParams = ServeParams())` | 为一个 WebSocket 连接提供服务（阻塞直到连接关闭） |

#### RPC

| 方法 | 签名 | 说明 |
|------|------|------|
| `addRpcMethod` | `fun addRpcMethod(name: String, handler: suspend (RpcContext, JsonElement?) -> JsonElement?)` | 注册 RPC 方法，`name` 支持 `{param}` 路径参数 |

#### 频道

| 方法 | 签名 | 说明 |
|------|------|------|
| `addChannel` | `fun addChannel(name: String, handler: suspend (SubContext) -> Flow<JsonElement?>)` | 注册订阅频道，返回 Flow 持续推送消息，Flow 结束时自动发送 Unsubscribe |
| `onPublication` | `fun onPublication(name: String, handler: suspend (PublishContext, JsonElement?) -> Unit)` | 注册频道发布处理器 |

---

## 配置

### Protocol

```kotlin
enum class Protocol {
    JSON,       // 文本帧上的换行分隔 JSON
    PROTOBUF,  // 二进制帧上的 varint 前缀 Protobuf（默认）
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

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `protocol` | `Protocol` | `PROTOBUF` | 传输协议 (`PROTOBUF` 或 `JSON`) |
| `token` | `String` | `""` | JWT 认证 token |
| `name` | `String` | `"centrifugo-kotlin"` | 客户端名称（传给服务端） |
| `version` | `String` | `""` | 客户端版本（传给服务端） |
| `data` | `JsonElement?` | `null` | 连接时携带的自定义数据 |
| `headers` | `Map<String, String>` | `emptyMap()` | 连接时携带的自定义请求头 |
| `timeout` | `Duration` | `10s` | 请求超时时间 |
| `minReconnectDelay` | `Duration` | `500ms` | 最小重连延迟 |
| `maxReconnectDelay` | `Duration` | `20s` | 最大重连延迟 |
| `maxServerPingDelay` | `Duration` | `10s` | 最大服务端 ping 延迟 |
| `getToken` | `(suspend () -> String)?` | `null` | 自动刷新 token 的回调 |
| `emulationEndpoint` | `String` | `""` | 模拟传输的 HTTP 端点（用于 HTTP Stream/SSE） |
| `transports` | `List<TransportConfig>` | `emptyList()` | 传输列表，用于自动回退 |

### BackoffReconnect

```kotlin
data class BackoffReconnect(
    val factor: Double = 2.0,
    val minDelay: Duration = 200.milliseconds,
    val maxDelay: Duration = 20.seconds,
) : ReconnectStrategy
```

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `factor` | `Double` | `2.0` | 指数退避因子 |
| `minDelay` | `Duration` | `200ms` | 最小重连延迟 |
| `maxDelay` | `Duration` | `20s` | 最大重连延迟 |

### ReconnectStrategy

```kotlin
interface ReconnectStrategy {
    fun timeBeforeNextAttempt(attempt: Int): Duration
}
```

可自定义实现替代默认的 `BackoffReconnect`。

### TransportType

```kotlin
enum class TransportType {
    WEBSOCKET,     // WebSocket 传输（默认）
    HTTP_STREAM,   // HTTP 流式传输
    SSE,           // Server-Sent Events 传输
}
```

### TransportConfig

```kotlin
data class TransportConfig(
    val transport: TransportType,
    val endpoint: String,
)
```

| 参数 | 类型 | 说明 |
|------|------|------|
| `transport` | `TransportType` | 传输类型 (WEBSOCKET, HTTP_STREAM, 或 SSE) |
| `endpoint` | `String` | 连接端点 URL |

自动回退使用示例：

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

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `token` | `String` | `""` | 订阅 token（用于私有频道认证） |
| `data` | `JsonElement?` | `null` | 订阅时携带的自定义数据 |
| `getToken` | `(suspend (SubscriptionTokenEvent) -> String)?` | `null` | Token 刷新回调 |
| `positioned` | `Boolean` | `false` | 启用位置追踪 |
| `recoverable` | `Boolean` | `false` | 启用消息恢复 |
| `joinLeave` | `Boolean` | `false` | 启用加入/离开事件 |
| `since` | `StreamPosition?` | `null` | 从指定流位置恢复 |
| `delta` | `String` | `""` | 增量压缩模式 |
| `tagsFilter` | `FilterNode?` | `null` | 消息标签过滤器，用于服务端过滤 |
| `minResubscribeDelay` | `Duration` | `500ms` | 最小重新订阅延迟 |
| `maxResubscribeDelay` | `Duration` | `20s` | 最大重新订阅延迟 |

### SubscriptionTokenEvent

```kotlin
data class SubscriptionTokenEvent(val channel: String)
```

### FilterNode

FilterNode 用于通过 `tagsFilter` 选项在服务端过滤消息。

```kotlin
data class FilterNode(
    val op: String = "",        // 逻辑运算符: "and", "or", "not"
    val key: String = "",       // 要比较的字段名
    val cmp: String = "",       // 比较运算符
    val `val`: String = "",   // 比较值
    val vals: List<String> = emptyList(),  // 用于 "in"/"nin" 比较的值列表
    val nodes: List<FilterNode> = emptyList(),  // 逻辑运算的子节点
)
```

#### 比较运算符

| 运算符 | 说明 |
|--------|------|
| `eq` | 等于 |
| `neq` | 不等于 |
| `in` | 在列表中 |
| `nin` | 不在列表中 |
| `ex` | 存在 |
| `nex` | 不存在 |
| `sw` | 以...开头 |
| `ew` | 以...结尾 |
| `ct` | 包含 |
| `lt` | 小于 |
| `lte` | 小于等于 |
| `gt` | 大于 |
| `gte` | 大于等于 |

#### 逻辑运算符

| 运算符 | 说明 |
|--------|------|
| `and` | AND 所有子节点 |
| `or` | OR 所有子节点 |
| `not` | NOT 子节点 |

#### 示例

```kotlin
// 简单等值过滤
val filter1 = FilterNode(key = "type", cmp = "eq", `val` = "news")

// IN 过滤
val filter2 = FilterNode(key = "category", cmp = "in", vals = listOf("news", "sports"))

// AND 过滤
val filter3 = FilterNode(
    op = "and",
    nodes = listOf(
        FilterNode(key = "status", cmp = "eq", `val` = "active"),
        FilterNode(key = "verified", cmp = "eq", `val` = "true"),
    )
)

// OR 过滤，嵌套 NOT
val filter4 = FilterNode(
    op = "or",
    nodes = listOf(
        FilterNode(key = "type", cmp = "eq", `val` = "news"),
        FilterNode(key = "type", cmp = "eq", `val` = "alert"),
    )
)
```

### ServeParams

```kotlin
data class ServeParams(
    val clientId: ClientId? = null,
    val pingInterval: Duration? = 25.seconds,
    val pongTimeout: Duration? = 10.seconds,
)
```

| 参数 | 类型 | 默认值 | 说明 |
|------|------|--------|------|
| `clientId` | `ClientId?` | 自动生成 (UUID) | 客户端唯一标识 |
| `pingInterval` | `Duration?` | `25s` | Ping 间隔，`null` 禁用心跳 |
| `pongTimeout` | `Duration?` | `10s` | Pong 超时时间 |

辅助方法：
- `withClientId(clientId)` — 指定 ClientId
- `withoutPing()` — 禁用心跳

---

## 事件

### 客户端事件

```kotlin
data class ConnectingEvent(val code: Int, val reason: String)
data class ConnectedEvent(val clientId: String, val version: String, val data: JsonElement?)
data class DisconnectedEvent(val code: Int, val reason: String)
```

### 订阅事件

```kotlin
data class SubscribingEvent(val channel: String, val code: Int, val reason: String)
data class SubscribedEvent(val channel: String, val data: JsonElement?)
data class UnsubscribedEvent(val channel: String, val code: Int, val reason: String)
data class PublicationEvent(
    val data: JsonElement?,
    val offset: Long,
    val info: ClientInfo?,
    val tags: Map<String, String>,
    val channel: String,
)
```

---

## 上下文类型（服务端）

### ConnectContext

```kotlin
data class ConnectContext(
    val clientId: ClientId,
    val clientName: String,
    val clientVersion: String,
    val token: String,
    val data: JsonElement?,
)
```

### DisconnectContext

```kotlin
data class DisconnectContext(val clientId: ClientId)
```

### RpcContext

```kotlin
data class RpcContext(
    val clientId: ClientId,
    val method: String,
    val params: Map<String, String>,   // 从路由 {param} 提取
)
```

### SubContext

```kotlin
data class SubContext(
    val clientId: ClientId,
    val channel: String,
    val params: Map<String, String>,   // 从路由 {param} 提取
    val data: JsonElement?,
)
```

### PublishContext

```kotlin
data class PublishContext(
    val clientId: ClientId,
    val channel: String,
    val params: Map<String, String>,   // 从路由 {param} 提取
)
```

### ClientId

```kotlin
data class ClientId(val value: String = Uuid.random().toString())
```

### ServerError

```kotlin
data class ServerError(val code: Int, override val message: String) : Exception(message)
```

在 `onConnect` handler 中抛出 `ServerError` 可拒绝连接，`code` 作为 WebSocket 关闭帧状态码。

---

## 错误

### RequestError

客户端操作可能抛出的异常：

```kotlin
sealed class RequestError : Exception() {
    data class ErrorResponse(val code: Int, override val message: String) : RequestError()
    data class UnexpectedReply(val reply: Reply) : RequestError()
    data object Timeout : RequestError()
    data object Cancelled : RequestError()
    data object ConnectionClosed : RequestError()
}
```

| 类型 | 说明 |
|------|------|
| `ErrorResponse` | 服务端返回的错误（包含错误码和消息） |
| `UnexpectedReply` | 收到非预期类型的回复 |
| `Timeout` | 请求超时 |
| `Cancelled` | 操作被取消 |
| `ConnectionClosed` | 连接已关闭 |

### ClientErrorCode

| 码 | 常量 | 说明 |
|---|------|------|
| 100 | `INTERNAL` | 服务器内部错误 |
| 101 | `UNAUTHORIZED` | 未授权 |
| 102 | `UNKNOWN_CHANNEL` | 未知频道 |
| 103 | `PERMISSION_DENIED` | 权限不足 |
| 104 | `METHOD_NOT_FOUND` | RPC 方法不存在 |
| 105 | `ALREADY_SUBSCRIBED` | 重复订阅 |
| 106 | `LIMIT_EXCEEDED` | 超出限制 |
| 107 | `BAD_REQUEST` | 请求格式错误 |
| 108 | `NOT_AVAILABLE` | 功能不可用 |
| 109 | `TOKEN_EXPIRED` | Token 已过期 |
| 110 | `EXPIRED` | 已过期 |
| 111 | `TOO_MANY_REQUESTS` | 请求过于频繁 |
| 112 | `UNRECOVERABLE_POSITION` | 无法恢复的位置 |

辅助方法：
- `ClientErrorCode.isTemporary(code)` — 是否为临时错误（可重试）
- `ClientErrorCode.description(code)` — 错误描述文本

### DisconnectErrorCode

| 码 | 常量 | 说明 |
|---|------|------|
| 3000 | `CONNECTION_CLOSED` | 连接关闭 |
| 3001 | `SHUTDOWN` | 服务端关闭 |
| 3004 | `SERVER_ERROR` | 服务端内部错误 |
| 3005 | `EXPIRED` | 连接过期 |
| 3006 | `SUB_EXPIRED` | 订阅过期 |
| 3008 | `SLOW` | 客户端过慢 |
| 3009 | `WRITE_ERROR` | 写入错误 |
| 3010 | `INSUFFICIENT_STATE` | 状态不足 |
| 3011 | `FORCE_RECONNECT` | 强制重连 |
| 3012 | `NO_PONG` | Pong 超时 |
| 3013 | `TOO_MANY_REQUESTS` | 请求过于频繁 |
| 3500 | `INVALID_TOKEN` | Token 无效 |
| 3501 | `BAD_REQUEST` | 请求格式错误 |
| 3502 | `STALE` | 连接过期 |
| 3503 | `FORCE_NO_RECONNECT` | 强制断开（不重连） |
| 3504 | `CONNECTION_LIMIT` | 连接数超限 |
| 3505 | `CHANNEL_LIMIT` | 频道数超限 |
| 3506 | `INAPPROPRIATE_PROTOCOL` | 协议不匹配 |
| 3507 | `PERMISSION_DENIED` | 权限不足 |
| 3508 | `NOT_AVAILABLE` | 功能不可用 |
| 3509 | `TOO_MANY_ERRORS` | 错误过多 |

辅助方法：
- `DisconnectErrorCode.shouldReconnect(code)` — 是否应该自动重连
- `DisconnectErrorCode.description(code)` — 错误描述文本

重连规则：
- `3000-3499` → 重连
- `3500-3999` → 不重连（致命错误）
- `4500-4999` → 不重连
- 其他 → 重连

---

## 数据类型

### ServerInfo

连接成功后可通过 `client.serverInfo()` 获取：

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

### ClientState

```kotlin
enum class ClientState { DISCONNECTED, CONNECTING, CONNECTED }
```

### SubscriptionState

```kotlin
enum class SubscriptionState { UNSUBSCRIBED, SUBSCRIBING, SUBSCRIBED }
```

### RemoveSubscriptionException

移除未 unsubscribe 的订阅时抛出：

```kotlin
class RemoveSubscriptionException(message: String) : Exception(message)
```
