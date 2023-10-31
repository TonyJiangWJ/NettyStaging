package com.tony.handler;


import com.tony.initializer.RpcClientInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.net.SocketAddress;

/**
 * @author jiangwj20966 2023/10/31
 */
@Component
public class ReconnectManager {

    private final RpcClientInitializer clientInitializer;

    public ReconnectManager(@Autowired RpcClientInitializer clientInitializer) {
        this.clientInitializer = clientInitializer;
    }

    /**
     * 重新建立连接
     *
     * @param socketAddress
     * @return
     */
    public void reconnect(SocketAddress socketAddress) {
        clientInitializer.connect(socketAddress);
    }
}
