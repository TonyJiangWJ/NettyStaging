package com.tony.initializer;

import com.tony.config.ServerInfo;
import com.tony.constants.EnumNettyType;
import com.tony.manager.SocketManager;
import com.tony.message.NettyContext;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Service
public class NettyRpcClientInitializer implements RpcClientInitializer {


    @Autowired
    private ChannelInitializer channelInitializer;

    private EventLoopGroup workerGroup;

    @Override
    public void init(ServerInfo serverInfo, boolean sync) {
        NettyContext.getInstance().setNettyType(EnumNettyType.client);
        workerGroup = new NioEventLoopGroup();
        // 当前设置仅仅连接一个服务端
        Optional<Future> future = connect(new InetSocketAddress(serverInfo.getHost(), serverInfo.getPort()));
        if (sync && future.isPresent()) {
            try {
                future.get().get(10, TimeUnit.SECONDS);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    public Optional<Future> connect(SocketAddress socketAddress) {
        int retry = 3;
        int count = 0;
        while (retry-- > 0) {
            count++;
            if (SocketManager.getInstance().noChannel(socketAddress)) {
                try {
                    log.info("Try connect socket[{}] - count {}", socketAddress, count);
                    Bootstrap bootstrap = new Bootstrap();
                    bootstrap.group(workerGroup)
                            .channel(NioSocketChannel.class)
                            .option(ChannelOption.SO_KEEPALIVE, true)
                            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                            .handler(channelInitializer);
                    return Optional.of(bootstrap.connect(socketAddress).syncUninterruptibly());
                } catch (Exception e) {
                    log.warn("fail connect socket[{}]. Retry in 5 seconds", socketAddress);
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException ex) {
                        log.error("sleep error", ex);
                    }
                }
            } else {
                log.info("socket[{}] is already connected", socketAddress);
                return Optional.empty();
            }
        }
        log.error("connect socket[{}] failed", socketAddress);
        return Optional.empty();
    }

    @Override
    public void destroy() throws Exception {
        Optional.of(workerGroup).ifPresent(EventExecutorGroup::shutdownGracefully);
        log.info("RPC client was down.");
    }
}
