package com.tony.config;

import com.tony.authorize.AuthorizeService;
import com.tony.authorize.impl.RsaAuthorizeServiceImpl;
import com.tony.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jiangwenjie 2019/10/24
 */
@Configuration
@ComponentScan
@Import({CommonConfiguration.class})
public class AutoConfiguration {

    @Value("${rsa.public.key}")
    private String publicKeyBase64;

    @Bean
    @ConditionalOnProperty(name = "rsa.enabled", matchIfMissing = true)
    public RSAUtil rsaUtil() throws Exception {
        RSAUtil rsaUtil = new RSAUtil();
        rsaUtil.initByPublicKey(publicKeyBase64);
        return rsaUtil;
    }

    @Bean
    @ConditionalOnProperty(name = "rsa.enabled", matchIfMissing = true)
    public AuthorizeService authorizeService() {
        return new RsaAuthorizeServiceImpl();
    }
}
