package com.tony.authorize.impl;

import com.tony.authorize.AuthorizeService;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.util.RSAUtil;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author jiangwenjie 2020/1/2
 */
@Slf4j
public class RsaAuthorizeServiceImpl implements AuthorizeService {

    @Autowired
    private RSAUtil rsaUtil;
    @Autowired
    private RpcClient rpcClient;

    @Override
    public Object generateAuthorizeData(ChannelHandlerContext ctx) {
        return rsaUtil.encryptWithPrivateKey(ctx.channel().toString() + RpcCmd.emptyKey());
    }

    @Override
    public boolean serverCheckAuthorize(MessageDto messageDto, MessageDto respMsg) {
        String encryptedString = messageDto.dataOfClazz(String.class);
        log.debug("加密数据：{}", encryptedString);
        String decryptContent = rsaUtil.decryptByPublicKey(encryptedString);
        String responseContent = respMsg.dataOfClazz(String.class);
        return responseContent != null && StringUtils.equals(decryptContent, responseContent);
    }

    @Override
    public void clientSendAuthorize(RpcCmd rpcCmd) {
        log.info("客户端收到来自：「{}」 的认证请求，解密数据并返回", rpcCmd.getRemoteAddressKey());
        // 此处可以加入其他认证方式
        RpcCmd responseCmd = new RpcCmd();
        MessageDto messageDto = new MessageDto();
        messageDto.setData(rsaUtil.decryptByPublicKey(rpcCmd.getMessage().dataOfClazz(String.class)));
        messageDto.setAction(EnumNettyActions.AUTHORIZE.getActionKey());
        messageDto.setState(EnumNettyState.RESPONSE_OK.getState());
        responseCmd.setMessage(messageDto);
        responseCmd.setRemoteAddressKey(rpcCmd.getRemoteAddressKey());
        responseCmd.setRandomKey(rpcCmd.getRandomKey());
        rpcClient.send(responseCmd);
    }
}
