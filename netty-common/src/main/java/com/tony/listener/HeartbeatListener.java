package com.tony.listener;

import com.tony.message.RpcCmd;

/**
 * @author jiangwenjie 2019/10/22
 */
public interface HeartbeatListener {

    /**
     * 客户端收到心跳请求处理
     * @param rpcCmd
     */
    default void onClientReceiveHeart(RpcCmd rpcCmd) {

    }

    /**
     * 服务端收到心跳请求处理
     * @param rpcCmd
     */
    default void onServerReceiveHeart(RpcCmd rpcCmd) {

    }
}
