package com.tony.authorize;

import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author jiangwenjie 2020/1/2
 */
public interface AuthorizeService {

    Object generateAuthorizeData(ChannelHandlerContext ctx);

    boolean serverCheckAuthorize(MessageDto messageDto, MessageDto respMsg);

    void clientSendAuthorize(RpcCmd rpcCmd);
}
