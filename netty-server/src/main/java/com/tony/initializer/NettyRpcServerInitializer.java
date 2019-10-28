package com.tony.initializer;

import com.tony.config.ServerInfo;
import com.tony.constants.EnumNettyType;
import com.tony.message.NettyContext;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * @author jiangwenjie 2019/10/23
 */
@Slf4j
@Component
public class NettyRpcServerInitializer implements RpcServerInitializer {

    @Autowired
    private ChannelInitializer nettyServerChannelInitializer;

    private EventLoopGroup workerGroup;
    private NioEventLoopGroup bossGroup;

    @Override
    public void init(ServerInfo serverInfo) {
        NettyContext.getInstance().setNettyType(EnumNettyType.server);
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    // 生产环境上移除LoggingHandler，避免打印过多的日志
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(nettyServerChannelInitializer);
            if (StringUtils.hasText(serverInfo.getHost())) {
                serverBootstrap.bind(serverInfo.getHost(), serverInfo.getPort());
            } else {
                serverBootstrap.bind(serverInfo.getPort());
            }
            log.info("server listen on [{}:{}]", StringUtils.hasText(serverInfo.getHost()) ? serverInfo.getHost() : "0.0.0.0", serverInfo.getPort());
        } catch (Exception e) {
            log.error("server start error:", e);
        }
    }

    @Override
    public void destroy() throws Exception {
        Optional.of(workerGroup).ifPresent(EventExecutorGroup::shutdownGracefully);
        Optional.of(bossGroup).ifPresent(EventExecutorGroup::shutdownGracefully);
        log.info("server down");
    }
}
