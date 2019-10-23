package com.tony.runner;

import com.tony.config.ServerProperty;
import com.tony.initializer.RpcServerInitializer;
import io.netty.channel.ChannelInitializer;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

/**
 * @author jiangwenjie 2019/10/23
 */
@Service
public class NettyServerRunner implements ApplicationRunner, DisposableBean {

    @Autowired
    private RpcServerInitializer rpcServerInitializer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // TODO replace with @Value
        ServerProperty serverProperty = new ServerProperty();
        serverProperty.setListenHost("127.0.0.1");
        serverProperty.setListenPort(1112);
        rpcServerInitializer.init(serverProperty);
    }


    @Override
    public void destroy() throws Exception {

    }
}
