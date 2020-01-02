package com.tony.answer;

import com.tony.authorize.AuthorizeService;
import com.tony.client.RpcClient;
import com.tony.constants.EnumNettyActions;
import com.tony.constants.EnumNettyState;
import com.tony.message.MessageDto;
import com.tony.message.RpcCmd;
import com.tony.message.data.Point2PointMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static com.tony.constants.EnumNettyActions.BROADCAST;

/**
 * @author jiangwenjie 2019/10/26
 */
@Slf4j
@Service
public class ClientAnswerHandlerServiceImpl implements AnswerHandlerService {
    @Autowired
    private RpcClient rpcClient;
    @Autowired
    private AuthorizeService authorizeService;

    @Override
    public void handleCmdRequest(RpcCmd rpcCmd) {
        // 如果是请求类消息，执行业务请求
        String action = rpcCmd.getMessage().getAction();
        // 如果有key则需要响应请求
        if (!StringUtils.isEmpty(rpcCmd.getRandomKey())) {
            EnumNettyActions nettyAction = EnumNettyActions.getActionByKey(action);
            switch (nettyAction) {
                case P2P:
                    Point2PointMessage point2PointMessage = rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class);
                    log.info("客户端收到来自：「{}」的点对点消息:{}", point2PointMessage.getTargetAddressKey(), point2PointMessage.getMessage());
                    break;
                case BROADCAST:
                    MessageDto messageDto = new MessageDto();
                    messageDto.setData("ok, i know");
                    messageDto.setState(EnumNettyState.RESPONSE_OK.getState());
                    messageDto.setAction(BROADCAST.getActionKey());
                    rpcCmd.setMessage(messageDto);
                    log.info("客户端响应广播消息：" + rpcCmd.getMessage());
                    rpcClient.send(rpcCmd);
                    break;
                case AUTHORIZE:
                    authorizeService.clientSendAuthorize(rpcCmd);
                    break;
                default:
                    // 执行业务操作，更新message
                    // ....
                    MessageDto message = new MessageDto();
                    message.setAction(action);
                    message.setData("客户端执行请求成功");
                    message.setState(EnumNettyState.RESPONSE_OK.getState());
                    rpcCmd.setMessage(message);
                    rpcClient.send(rpcCmd);
            }
        }
    }

    @Override
    public void handleCmdResponse(RpcCmd cmd) {
        MessageDto message = cmd.getMessage();
        String actionKey = message.getAction();
        log.info("客户端收到{}消息：【{}】", EnumNettyActions.getActionByKey(actionKey).getActionName(), message);
        if (BROADCAST.getActionKey().equals(actionKey)) {
            String content = message.dataOfClazz(String.class);
            if (content.contains("new client")) {
                String newClientKey = content.substring(22);
                Point2PointMessage point2PointMessage = new Point2PointMessage();
                point2PointMessage.setMessage("Hello, new client. I'm old client");
                point2PointMessage.setTargetAddressKey(newClientKey);
                MessageDto messageDto = new MessageDto();
                messageDto.setState(EnumNettyState.REQUEST.getState());
                messageDto.setAction(EnumNettyActions.P2P.getActionKey());
                messageDto.setData(point2PointMessage);
                RpcCmd rpcCmd = new RpcCmd();
                rpcCmd.setMessage(messageDto);
                rpcCmd.setRandomKey(RpcCmd.emptyKey());
                rpcCmd.setRemoteAddressKey(cmd.getRemoteAddressKey());
                log.info("客户端发送hello信息到新客户端");
                rpcClient.send(rpcCmd);
            }
        }
    }
}
