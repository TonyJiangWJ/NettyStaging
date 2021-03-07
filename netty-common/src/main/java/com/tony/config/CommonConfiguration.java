package com.tony.config;

import com.tony.authorize.AuthorizeService;
import com.tony.authorize.impl.DefaultAuthorizeServiceImpl;
import com.tony.authorize.impl.RsaAuthorizeServiceImpl;
import com.tony.constants.EnumNettyProtoType;
import com.tony.listener.RpcConnectionListener;
import com.tony.listener.impl.DefaultRpcConnectionListener;
import com.tony.message.NettyContext;
import com.tony.serializer.ObjectSerializer;
import com.tony.serializer.SerializerFactory;
import com.tony.util.RSAUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author jiangwenjie 2019/10/24
 */
@Configuration
@ComponentScan
@Slf4j
@EnableConfigurationProperties({ServerInfoProperties.class, RsaProperties.class})
public class CommonConfiguration {

    @Value("${netty.proto.type:protostuff}")
    private String protoType;

    @Bean
    @ConditionalOnClass(ServerInfo.class)
    public ServerInfo serverInfo(ServerInfoProperties serverInfoProperties) {
        ServerInfo serverInfo = new ServerInfo();
        serverInfo.setCheckTime(serverInfoProperties.getCheckTime());
        serverInfo.setHost(serverInfoProperties.getHost());
        serverInfo.setPort(serverInfoProperties.getPort());
        return serverInfo;
    }

    @Bean
    @ConditionalOnMissingBean
    public RpcConnectionListener rpcConnectionListener() {
        return new DefaultRpcConnectionListener();
    }

    @Bean
    @ConditionalOnMissingBean
    public ObjectSerializer objectSerializer() {
        if (!EnumNettyProtoType.contains(protoType)) {
            log.warn("prototype for: {} is not exist, reset to default.", protoType);
            protoType = EnumNettyProtoType.PROTOSTUFF.getKey();
        }
        ObjectSerializer objectSerializer = SerializerFactory.getSerializer(protoType);
        NettyContext.getInstance().setProtoType(protoType);
        log.info("current prototype is: {}", protoType);
        return objectSerializer;
    }

    @Bean
    @ConditionalOnProperty(name = "rsa.enabled")
    public AuthorizeService rsaAuthorizeService() {
        return new RsaAuthorizeServiceImpl();
    }

    @Bean
    @ConditionalOnMissingBean
    public AuthorizeService authorizeService() {
        return new DefaultAuthorizeServiceImpl();
    }


    @Bean
    @ConditionalOnProperty(name = "rsa.enabled")
    public RSAUtil rsaUtil(RsaProperties rsaProperties) throws Exception {
        RSAUtil rsaUtil = new RSAUtil();
        if (StringUtils.isNotEmpty(rsaProperties.getPublicKey())) {
            rsaUtil.initByPublicKey(rsaProperties.getPublicKey());
            return rsaUtil;
        } else {
            rsaUtil = new RSAUtil(rsaProperties.getKeyPath());
        }
        rsaUtil.setValidTime(rsaProperties.getValidTime());
        return rsaUtil;
    }
}
