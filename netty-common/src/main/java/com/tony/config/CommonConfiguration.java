package com.tony.config;

import com.tony.listener.RpcConnectionListener;
import com.tony.listener.impl.DefaultRpcConnectionListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangwenjie 2019/10/24
 */
@Configuration
@ComponentScan
public class CommonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConfigurationProperties("netty.server")
    public ServerInfo serverInfo() {
        return new ServerInfo();
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcConnectionListener rpcConnectionListener() {
        return new DefaultRpcConnectionListener();
    }
}
