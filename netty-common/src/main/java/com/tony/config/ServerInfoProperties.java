package com.tony.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author jiangwenjie 2019/10/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
@ConfigurationProperties(prefix = "netty.server")
public class ServerInfoProperties {

    private String host = "127.0.0.1";
    private int port = 1110;
    private long checkTime = 10000;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public long getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(long checkTime) {
        this.checkTime = checkTime;
    }
}
