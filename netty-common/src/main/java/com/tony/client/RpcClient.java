package com.tony.client;

import com.tony.RpcException;
import com.tony.constants.EnumResponseState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.util.Callback;
import io.netty.channel.Channel;

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
     * 发送消息，基于remoteKey和消息内容messageDto
     *
     * @param channel
     * @param messageDto
     * @return 返回发送状态
     */
    EnumResponseState send(Channel channel, MessageDto messageDto);

    /**
     * 发送请求，返回请求结果
     *
     * @param rpcCmd
     * @return 请求结果
     * @throws RpcException
     */
    MessageDto request(RpcCmd rpcCmd) throws RpcException;

    /**
     * 发送请求，返回请求结果
     *
     * @param rpcCmd
     * @param timeout 单位秒
     * @return
     * @throws RpcException
     */
    MessageDto request(RpcCmd rpcCmd, long timeout) throws RpcException;

    /**
     * 发送请求，返回请求结果
     *
     * @param remoteKey
     * @param messageDto
     * @return
     * @throws RpcException
     */
    MessageDto request(String remoteKey, MessageDto messageDto) throws RpcException;

    /**
     * 发送请求，返回请求结果
     *
     * @param remoteKey
     * @param messageDto
     * @param timeout    单位秒
     * @return
     * @throws RpcException
     */
    MessageDto request(String remoteKey, MessageDto messageDto, long timeout) throws RpcException;

    /**
     * 异步请求
     *
     * @param rpcCmd
     * @param callback
     */
    void requestAsync(RpcCmd rpcCmd, Callback<MessageDto, Void> callback);

    /**
     * 异步请求
     *
     * @param rpcCmd
     * @param timeout
     * @param callback
     */
    void requestAsync(RpcCmd rpcCmd, Callback<MessageDto, Void> callback, long timeout);

    /**
     * 异步请求
     *
     * @param remoteKey
     * @param messageDto
     * @param callback
     */
    void requestAsync(String remoteKey, MessageDto messageDto, Callback<MessageDto, Void> callback);

    /**
     * 异步请求
     *
     * @param remoteKey
     * @param messageDto
     * @param timeout
     * @param callback
     */
    void requestAsync(String remoteKey, MessageDto messageDto, Callback<MessageDto, Void> callback, long timeout);

    /**
     * 异步请求
     *
     * @param channel
     * @param messageDto
     * @param callback
     * @param timeout
     */
    void requestAsync(Channel channel, MessageDto messageDto, Callback<MessageDto, Void> callback, long timeout);

    /**
     * 获取所有的远程连接key
     *
     * @return 远程连接key列表.
     */
    List<String> loadAllRemoteKey();
}
