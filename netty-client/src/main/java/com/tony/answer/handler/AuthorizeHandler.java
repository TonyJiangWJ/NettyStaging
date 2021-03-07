package com.tony.answer.handler;

import com.tony.authorize.AuthorizeService;
import com.tony.client.RpcClient;
import com.tony.message.RpcCmd;

/**
 * @author jiangwenjie 2021/2/22
 */
public class AuthorizeHandler extends ClientAnswerHandlerAdapter {

    private AuthorizeService authorizeService;

    public AuthorizeHandler(RpcClient rpcClient, RpcCmd rpcCmd) {
        super(rpcClient, rpcCmd);
    }

    public void setAuthorizeService(AuthorizeService authorizeService) {
        this.authorizeService = authorizeService;
    }

    @Override
    protected void doHandleRequest() {
        authorizeService.clientSendAuthorize(rpcCmd);
    }
}
