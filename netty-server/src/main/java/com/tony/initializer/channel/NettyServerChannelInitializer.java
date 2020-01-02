package com.tony.initializer.channel;

import com.tony.config.ServerInfo;
import com.tony.handler.ObjectSerializerDecoder;
import com.tony.handler.ObjectSerializerEncoder;
import com.tony.handler.RpcAnswerHandler;
import com.tony.handler.RpcCmdDecoder;
import com.tony.handler.RpcCmdEncoder;
import com.tony.handler.SocketManagerInitHandler;
import com.tony.serializer.ObjectSerializer;
import com.tony.util.RSAUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author jiangwenjie 2019/10/23
 */
@Component
public class NettyServerChannelInitializer extends ChannelInitializer<Channel> {

    @Autowired
    private RpcAnswerHandler rpcAnswerHandler;
    @Autowired
    private RpcCmdDecoder rpcCmdDecoder;
    @Autowired
    private SocketManagerInitHandler socketManagerInitHandler;
    @Autowired
    private ServerInfo serverInfo;
    @Autowired
    private ObjectSerializer objectSerializer;
    @Autowired(required = false)
    private RSAUtil rsaUtil;

    @Override
    protected void initChannel(Channel ch) throws Exception {
        long idleTime = serverInfo.getCheckTime();
        ch.pipeline()
                // 定长解码 解决粘包和半包问题
                .addLast(new LengthFieldPrepender(4, false))
                .addLast(new LengthFieldBasedFrameDecoder(
                        Integer.MAX_VALUE, 0, 4, 0, 4))

                // 设置自动间隔
                .addLast(new IdleStateHandler(idleTime, idleTime, idleTime, TimeUnit.MILLISECONDS))
                // 对象序列化和反序列化
                .addLast(new ObjectSerializerEncoder(objectSerializer, rsaUtil))
                .addLast(new ObjectSerializerDecoder(objectSerializer, rsaUtil))
                .addLast(rpcCmdDecoder)
                .addLast(new RpcCmdEncoder())
                // 服务端比客户端少了重连的handler
                .addLast(socketManagerInitHandler)
                .addLast(rpcAnswerHandler);
    }
}
