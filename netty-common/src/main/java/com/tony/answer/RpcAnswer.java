package com.tony.answer;

import com.tony.message.RpcCmd;

/**
 * @author jiangwenjie 2019/10/22
 */
public interface RpcAnswer {

    /**
     * 执行业务回调
     *
     * @param rpcCmd 消息参数
     */
    void callback(RpcCmd rpcCmd);
}
