## NettyStaging 一套可以直接使用的Netty脚手架代码
- 基于netty4实现的一套netty消息传递框架代码，可以接入业务代码实现其他需要长连接的功能
- 运行框架采用SpringBoot 2.0.5
- 整体思路参考自TX-LCN分布式事务框架中的netty实现
- 消息序列化采用protostuff实现，仅支持java。跨语言建议使用protobuff
- 实现消息发送，消息请求并等待响应结果，自动心跳检测等
- 可以直接以此为骨架构建长连接通信业务，扩展实现AnswerHandlerService接口和定义EnumNettyActions。具体可参考ServerAnswerHandlerServiceImpl和ClientAnswerHandlerServiceImpl，demo中实现了自动对新连接的客户端发送点对点hello消息
- 加入了异步请求操作，以及实现模拟的认证功能，具体可以修改com.tony.listener.RpcConnectionListener.authorizeConnection
## 序列化支持
- 序列化支持三种方式，分别是protostuff，stuff-protobuf, pure-protobuf，可以在application.yml中通过`netty.proto.type`进行指定，默认实现是protostuff
- 以上三种方式的使用方便程度依次是protostuff>stuff-protobuf>pure-protobuf，序列化性能上则反过来。
### protostuff方式
- protostuff方式是io.protostuff提供的基于protobuff实现的一种序列化方式，适合纯java代码之间序列化和反序列化，也是当前框架默认使用的。特别的一点就是Serializable对象可以直接进行序列化和反序列化，但是如果到其他语言就没法进行互相转化。
### stuff-protobuf
- 这个是io.protostuff中内置的protobuf实现，但是没法使用Any类型，对于需要使用泛型的只能将泛型对象先设置为bytes，然后进行二次序列化，可以跨语言平台序列化和反序列化
### pure-protobuf
- 纯protobuf实现，不基于io.protostuff。完全基于protobuf官方提供的方法，需要在java和其他语言中都使用同一个.proto生成的对象，可以使用Any，然后在java中实现泛型扩展。
- 当前框架中为了更好的兼容，维持了另外两种方式的POJO，通过POJOConverter将protobuf提供的POJO和现有POJO进行转换，当需要增加对象类型的时候需要在该工具类中增加相应的转换方法，使用起来有些许麻烦。
### 具体的使用要求 可以参考博客文章
- 博客地址[tonyjiangwj.github.io](https://tonyjiangwj.github.io/2019/11/01/%E4%BD%BF%E7%94%A8Protobuf%E5%AE%9E%E7%8E%B0%E8%B7%A8%E8%AF%AD%E8%A8%80%E5%BA%8F%E5%88%97%E5%8C%96%E5%92%8C%E5%8F%8D%E5%BA%8F%E5%88%97%E5%8C%96%EF%BC%8CJava-Python%E5%AE%9E%E4%BE%8B/)
### 序列化对象，消息传递对象
```shell
RpcCmd RPC传递的封装对象
--message
    MessageDto
    --action
    String 当前消息的业务类型
    --state
    int 业务执行状态````````
    --serialData
    Serializable 序列化泛型对象，protostuff和prue-protobuf实现时使用
    --bytesData
    byte[] stuff-protobuff实现时使用，泛型对象会进行二次序列化
--remoteAddressKey
    String 用于保存需要传递的socket地址
--randomKey
    String 业务随机key，锁的唯一标识，用于等待消息反馈
--rpcContent
    RpcContent 消息锁对象
```
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