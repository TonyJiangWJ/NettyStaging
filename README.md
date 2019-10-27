## NettyStaging 一套可以直接使用的Netty脚手架代码
- 基于netty4实现的一套netty消息传递框架代码，可以接入业务代码实现其他需要长连接的功能
- 运行框架采用SpringBoot 2.0.5
- 整体思路参考自TX-LCN分布式事务框架中的netty实现
- 消息序列化采用protostuff实现
- 实现消息发送，消息请求并等待响应结果，自动心跳检测等
- 可以直接以此为骨架构建长连接通信业务，扩展实现AnswerHandlerService接口和定义EnumNettyActions。具体可参考ServerAnswerHandlerServiceImpl和ClientAnswerHandlerServiceImpl，demo中实现了自动对新连接的客户端发送点对点hello消息

## 运行demo
- clone当前仓库，进入NettyStaging文件目录
- 在目录中执行 ```mvn clean install -Dmaven.test.skip```
- 服务端 ```java -jar netty-server/target/netty-server-1.0-SNAPSHOT.jar```
- 客户端 ```java -jar netty-client/target/netty-client-1.0-SNAPSHOT.jar```
- 服务端运行示例
    ```log
    2019-10-24 00:04:22.894  INFO 17433 --- [           main] c.t.i.NettyRpcServerInitializer          : server listen on [127.0.0.1:1112]
    2019-10-24 00:04:22.907  INFO 17433 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x580eda6f] REGISTERED
    2019-10-24 00:04:22.908  INFO 17433 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x580eda6f] BIND: /127.0.0.1:1112
    2019-10-24 00:04:22.914  INFO 17433 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x580eda6f, L:/127.0.0.1:1112] ACTIVE
    2019-10-24 00:04:34.454  INFO 17433 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x580eda6f, L:/127.0.0.1:1112] READ: [id: 0xde01aebf, L:/127.0.0.1:1112 - R:/127.0.0.1:55286]
    2019-10-24 00:04:34.455  INFO 17433 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x580eda6f, L:/127.0.0.1:1112] READ COMPLETE
    2019-10-24 00:04:46.017  INFO 17433 --- [   server-ans-0] com.tony.answer.ServerRpcAnswer          : 服务端收到客户端请求：「MessageDto(action=sm, state=100, data=Hello，now is 462760767012179)」
    2019-10-24 00:04:47.428  INFO 17433 --- [ntLoopGroup-3-1] c.tony.listener.ServerHeartbeatListener  : 服务端收到心跳请求：「{"action":"hc","state":100}」cmdInfo:{"message":{"action":"hc","state":100},"randomKey":"462759253005553","remoteAddressKey":"/127.0.0.1:55286"}
    2019-10-24 00:04:49.591  INFO 17433 --- [ntLoopGroup-3-1] c.tony.listener.ServerHeartbeatListener  : 服务端收到心跳请求：「{"action":"hc","state":100}」cmdInfo:{"message":{"action":"hc","state":100},"randomKey":"462759253005553","remoteAddressKey":"/127.0.0.1:55286"}
    2019-10-24 00:04:49.592  INFO 17433 --- [ntLoopGroup-3-1] c.tony.listener.ServerHeartbeatListener  : 服务端收到心跳请求：「{"action":"hc","state":100}」cmdInfo:{"message":{"action":"hc","state":100},"randomKey":"462759253005553","remoteAddressKey":"/127.0.0.1:55286"}
    2019-10-24 00:04:50.658  INFO 17433 --- [   server-ans-1] com.tony.answer.ServerRpcAnswer          : 服务端收到客户端请求：「MessageDto(action=sm, state=100, data=Hello，now is 462775967781819)」
    2019-10-24 00:04:53.173  INFO 17433 --- [   server-ans-2] com.tony.answer.ServerRpcAnswer          : 服务端收到客户端请求：「MessageDto(action=sm, state=100, data=Hello，now is 462778479504322)」
    ```
- 客户端运行示例
    ```log 
    2019-10-24 00:08:40.435  INFO 17517 --- [           main] com.tony.NettyClientApplication          : Started NettyClientApplication in 1.216 seconds (JVM running for 1.669)
    2019-10-24 00:08:40.463  INFO 17517 --- [           main] c.t.i.NettyRpcClientInitializer          : Try connect socket[/127.0.0.1:1112] - count 1
    2019-10-24 00:08:41.575  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端发送业务请求信息：「{"action":"sm","data":"Hello，now is 463006839854903","state":100}」
    2019-10-24 00:08:43.145  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端请求逻辑中获取响应消息：「{"action":"sm","data":"服务端执行请求成功, for:463006839854903","state":200}」
    2019-10-24 00:08:44.149  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端发送业务请求信息：「{"action":"sm","data":"Hello，now is 463009458775144","state":100}」
    2019-10-24 00:08:45.655  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端请求逻辑中获取响应消息：「{"action":"sm","data":"服务端执行请求成功, for:463009458775144","state":200}」
    2019-10-24 00:08:46.659  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端发送业务请求信息：「{"action":"sm","data":"Hello，now is 463011969131451","state":100}」
    2019-10-24 00:08:48.169  INFO 17517 --- [    auto-send-0] com.tony.runner.NettyClientRunner        : 客户端请求逻辑中获取响应消息：「{"action":"sm","data":"服务端执行请求成功, for:463011969131451","state":200}」
    ```