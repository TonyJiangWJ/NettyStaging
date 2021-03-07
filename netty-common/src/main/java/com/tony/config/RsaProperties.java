package com.tony.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2021/1/22
 */
@Component
@ConfigurationProperties("rsa")
public class RsaProperties {

    private boolean enabled = false;
    private long validTime = 30000;
    private String publicKey;

    private String keyPath;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public long getValidTime() {
        return validTime;
    }

    public void setValidTime(long validTime) {
        this.validTime = validTime;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getKeyPath() {
        return keyPath;
    }

    public void setKeyPath(String keyPath) {
        this.keyPath = keyPath;
    }
}
