package com.tony.client;

import com.tony.constants.EnumResponseState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;

import java.util.List;

/**
 * @author jiangwenjie 2019/10/23
 */
public interface RpcClient {
    /**
     * 发送消息，基于rpcCmd
     *
     * @param rpcCmd
     * @return 返回发送状态
     */
    EnumResponseState send(RpcCmd rpcCmd);

    /**
     * 发送消息，基于remoteKey和消息内容messageDto
     *
     * @param remoteKey
     * @param messageDto
     * @return 返回发送状态
     */
    EnumResponseState send(String remoteKey, MessageDto messageDto);

    /**
     * 发送请求，返回请求结果
     *
     * @param rpcCmd
     * @return 请求结果
     */
    MessageDto request(RpcCmd rpcCmd);

    /**
     * 发送请求，返回请求结果
     *
     * @param rpcCmd
     * @param timeout
     * @return
     */
    MessageDto request(RpcCmd rpcCmd, long timeout);

    /**
     * 发送请求，返回请求结果
     *
     * @param remoteKey
     * @param messageDto
     * @return
     */
    MessageDto request(String remoteKey, MessageDto messageDto);

    /**
     * 发送请求，返回请求结果
     *
     * @param remoteKey
     * @param messageDto
     * @param timeout
     * @return
     */
    MessageDto request(String remoteKey, MessageDto messageDto, long timeout);

    /**
     * 获取所有的远程连接key
     *
     * @return 远程连接key列表.
     */
    List<String> loadAllRemoteKey();
}