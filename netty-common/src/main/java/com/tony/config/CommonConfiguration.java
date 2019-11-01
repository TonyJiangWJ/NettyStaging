package com.tony.config;

import com.tony.constants.EnumNettyProtoType;
import com.tony.listener.RpcConnectionListener;
import com.tony.listener.impl.DefaultRpcConnectionListener;
import com.tony.message.NettyContext;
import com.tony.serializer.ObjectSerializer;
import com.tony.serializer.impl.ProtobufSerializer;
import com.tony.serializer.impl.ProtostuffSerializer;
import com.tony.serializer.impl.PureProtobufSerializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
@Slf4j
public class CommonConfiguration {

    @Value("${netty.proto.type:stuff-protobuf}")
    private String protoType;

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

    @Bean
    @ConditionalOnMissingBean
    public ObjectSerializer objectSerializer() {
        ObjectSerializer objectSerializer = null;
        if (EnumNettyProtoType.STUFF_PROTOBUF.getKey().equals(protoType)) {
            objectSerializer = ProtobufSerializer.getInstance();
        } else if (EnumNettyProtoType.PURE_PROTOBUF.getKey().equals(protoType)) {
            objectSerializer = PureProtobufSerializer.getInstance();
        } else {
            protoType = EnumNettyProtoType.PROTOSTUFF.getKey();
            objectSerializer = ProtostuffSerializer.getInstance();
        }
        NettyContext.getInstance().setProtoType(protoType);
        log.info("current prototype is: {}", protoType);
        return objectSerializer;
    }
}
