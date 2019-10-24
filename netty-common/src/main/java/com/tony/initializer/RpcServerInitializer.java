package com.tony.initializer;

import com.tony.config.ServerInfo;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author jiangwenjie 2019/10/23
 */
public interface RpcServerInitializer extends DisposableBean {
    /**
     * 初始化服务端
     *
     * @param serverInfo
     */
    void init(ServerInfo serverInfo);
}
