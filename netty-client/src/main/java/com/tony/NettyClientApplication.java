package com.tony;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author jiangwenjie 2019/10/23
 */
@SpringBootApplication
public class NettyClientApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder().sources(NettyClientApplication.class).run(args);
    }
}
