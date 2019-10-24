package com.tony.config;

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
}
