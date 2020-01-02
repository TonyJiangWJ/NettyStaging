package com.tony.authorize.impl;

import com.tony.authorize.AuthorizeService;
import com.tony.client.RpcClient;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jiangwenjie 2020/1/2
 */
@Slf4j
public class DefaultAuthorizeServiceImpl implements AuthorizeService {
    @Autowired
    private RpcClient rpcClient;

    @Override
    public Object generateAuthorizeData(ChannelHandlerContext ctx) {
        return RpcCmd.emptyKey();
    }

    @Override
    public boolean serverCheckAuthorize(MessageDto messageDto, MessageDto respMsg) {
        String authorizeData = messageDto.dataOfClazz(String.class);
        boolean passed = false;
        try {
            if (respMsg != null) {
                String resultStr = respMsg.dataOfClazz(String.class);
                // 默认只判断发送和接受的数据是否相同
                passed = authorizeData.equals(resultStr);
            }
        } catch (Exception e) {
            log.error("连接认证处理异常", e);
        }
        return passed;
    }

    @Override
    public void clientSendAuthorize(RpcCmd rpcCmd) {
        log.info("客户端收到来自：「{}」 的认证请求，将认证消息原路返回", rpcCmd.getRemoteAddressKey());
        // 此处可以加入其他认证方式
        RpcCmd responseCmd = new RpcCmd();
        responseCmd.setMessage(rpcCmd.getMessage());
        responseCmd.setRemoteAddressKey(rpcCmd.getRemoteAddressKey());
        responseCmd.setRandomKey(rpcCmd.getRandomKey());
        rpcClient.send(responseCmd);
    }
}
