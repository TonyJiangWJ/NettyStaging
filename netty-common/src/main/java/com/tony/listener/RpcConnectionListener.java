package com.tony.listener;

/**
 * @author jiangwenjie 2019/10/22
 */
public interface RpcConnectionListener {

    /**
     * 建立连接
     *
     * @param remoteKey
     */
    void connect(String remoteKey);

    /**
     * 解除连接
     *
     * @param remoteKey
     */
    void disconnect(String remoteKey);
}
