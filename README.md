## NettyStaging 一套可以直接使用的Netty脚手架代码
- 基于netty4实现的一套netty消息传递框架代码，可以接入业务代码实现其他需要长连接的功能
- 运行框架采用SpringBoot 2.0.5
- 整体思路参考自TX-LCN分布式事务框架中的netty实现
- 消息序列化采用protostuff实现
- 实现消息发送，消息请求并等待响应结果，自动心跳检测等
- 可以直接以此为骨架构建长连接通信业务，扩展实现AnswerHandlerService接口和定义EnumNettyActions。具体可参考ServerAnswerHandlerServiceImpl和ClientAnswerHandlerServiceImpl，demo中实现了自动对新连接的客户端发送点对点hello消息
- 加入了异步请求操作，以及实现模拟的认证功能，具体可以修改com.tony.listener.RpcConnectionListener.authorizeConnection
## 运行demo
- clone当前仓库，进入NettyStaging文件目录
- 在目录中执行 ```mvn clean install -Dmaven.test.skip```
- 服务端 ```java -jar netty-server/target/netty-server-1.0-SNAPSHOT.jar```
- 客户端 ```java -jar netty-client/target/netty-client-1.0-SNAPSHOT.jar```
- 服务端运行示例
    ```log
    2019-10-28 19:14:08.205  INFO 36470 --- [           main] com.tony.NettyServerApplication          : Started NettyServerApplication in 1.268 seconds (JVM running for 1.781)
    2019-10-28 19:14:08.288  INFO 36470 --- [           main] c.t.i.NettyRpcServerInitializer          : server listen on [0.0.0.0:1234]
    2019-10-28 19:14:08.299  INFO 36470 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x9aecb5a3] REGISTERED
    2019-10-28 19:14:08.300  INFO 36470 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x9aecb5a3] BIND: /0.0.0.0:1234
    2019-10-28 19:14:08.304  INFO 36470 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x9aecb5a3, L:/0:0:0:0:0:0:0:0:1234] ACTIVE
    2019-10-28 19:14:17.749  INFO 36470 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x9aecb5a3, L:/0:0:0:0:0:0:0:0:1234] READ: [id: 0xd336bfe6, L:/127.0.0.1:1234 - R:/127.0.0.1:54709]
    2019-10-28 19:14:17.749  INFO 36470 --- [ntLoopGroup-2-1] io.netty.handler.logging.LoggingHandler  : [id: 0x9aecb5a3, L:/0:0:0:0:0:0:0:0:1234] READ COMPLETE
    2019-10-28 19:14:17.755  INFO 36470 --- [ntLoopGroup-3-1] c.t.l.ServerRpcConnectionListener        : 服务端发送认证请求到：/127.0.0.1:54709
    2019-10-28 19:14:17.926  INFO 36470 --- [sync-rpc-call-0] c.t.l.ServerRpcConnectionListener        : 链接认证成功：/127.0.0.1:54709
    2019-10-28 19:14:17.927  INFO 36470 --- [sync-rpc-call-0] c.t.l.ServerRpcConnectionListener        : new client[/127.0.0.1:54709] connected, send broadcast to other clients
    ```
- 客户端运行示例
    ```log 
    2019-10-28 19:14:17.636  INFO 36475 --- [           main] com.tony.NettyClientApplication          : Started NettyClientApplication in 1.273 seconds (JVM running for 1.775)
    2019-10-28 19:14:17.673  INFO 36475 --- [           main] c.t.i.NettyRpcClientInitializer          : Try connect socket[/127.0.0.1:1234] - count 1
    2019-10-28 19:14:17.741  INFO 36475 --- [ntLoopGroup-2-1] c.t.l.impl.DefaultRpcConnectionListener  : connected to: /127.0.0.1:1234
    2019-10-28 19:14:17.869  INFO 36475 --- [client-answer-0] c.t.a.ClientAnswerHandlerServiceImpl     : 客户端收到来自：「/127.0.0.1:1234」 的认证请求，将认证消息原路返回
    2019-10-28 19:14:17.927  INFO 36475 --- [client-answer-1] c.t.a.ClientAnswerHandlerServiceImpl     : 客户端收到新建连接消息：【MessageDto(action=nc, state=200, data=Welcome to connect nettyStaging server! /127.0.0.1:54709)】
    ```