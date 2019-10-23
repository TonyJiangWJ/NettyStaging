package com.tony.initializer;

import com.tony.config.ServerProperty;
import org.springframework.beans.factory.DisposableBean;

/**
 * @author jiangwenjie 2019/10/23
 */
public interface RpcServerInitializer extends DisposableBean {
    /**
     * 初始化服务端
     *
     * @param serverProperty
     */
    void init(ServerProperty serverProperty);
}
