package com.tony.runner;

import com.tony.config.ServerInfo;
import com.tony.initializer.RpcServerInitializer;
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
    @Autowired
    private ServerInfo serverInfo;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        rpcServerInitializer.init(serverInfo);
    }


    @Override
    public void destroy() throws Exception {
        rpcServerInitializer.destroy();
    }
}
