package com.tony.initializer;

import com.tony.config.ServerInfo;
import org.springframework.beans.factory.DisposableBean;

import java.net.SocketAddress;
import java.util.Optional;
import java.util.concurrent.Future;

/**
 * @author jiangwenjie 2019/10/23
 */
public interface RpcClientInitializer extends DisposableBean {
    /**
     * 初始化操作
     *
     * @param serverInfo 服务端信息
     * @param sync
     */
    void init(ServerInfo serverInfo, boolean sync);

    /**
     * 建立连接
     *
     * @param socketAddress
     * @return
     */
    Optional<Future> connect(SocketAddress socketAddress);

    /**
     * 重新建立连接
     *
     * @param socketAddress
     * @return
     */
    default Optional<Future> reconnect(SocketAddress socketAddress) {
        return connect(socketAddress);
    }
}
