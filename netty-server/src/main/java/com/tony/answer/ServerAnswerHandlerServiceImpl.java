package com.tony.answer;

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

import java.io.Serializable;

/**
 * @author jiangwenjie 2019/10/26
 */
@Slf4j
@Service
public class ServerAnswerHandlerServiceImpl implements AnswerHandlerService {

    @Autowired
    private RpcClient rpcClient;

    @Override
    public void handleCmdRequest(RpcCmd rpcCmd) {
        String action = rpcCmd.getMessage().getAction();
        // 执行业务操作，更新message
        // ....
//                try {
//                    // 模拟服务器端业务逻辑延迟
//                    Thread.sleep(1500);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
        // 如果有key则需要响应请求
        if (!StringUtils.isEmpty(rpcCmd.getRandomKey())) {
            if (EnumNettyActions.SIMPLE_ACTION.getActionKey().equals(action)) {
                MessageDto message = new MessageDto();
                message.setAction(action);
                String data = rpcCmd.getMessage().dataOfClazz(String.class);
                if ("Hello，now is listClients".equals(data)) {
                    message.setData((Serializable) rpcClient.loadAllRemoteKey());
                } else {
                    message.setData("服务端执行请求成功, for:" + data.substring(13));
                }
                message.setState(EnumNettyState.RESPONSE_OK.getState());
                rpcCmd.setMessage(message);
                log.info("服务端执行完毕，发送反馈消息：「{}」", message);
                rpcClient.send(rpcCmd);
            } else if (EnumNettyActions.P2P.getActionKey().equals(action)) {
                Point2PointMessage p2pMessage = rpcCmd.getMessage().dataOfClazz(Point2PointMessage.class);
                String targetAddressKey = p2pMessage.getTargetAddressKey();
                String originAddressKey = rpcCmd.getRemoteAddressKey();
                p2pMessage.setTargetAddressKey(originAddressKey);
                MessageDto redirectMessage = new MessageDto();
                redirectMessage.setData(p2pMessage);
                redirectMessage.setAction(EnumNettyActions.P2P.getActionKey());
                redirectMessage.setState(EnumNettyState.REQUEST.getState());
                RpcCmd redirectCmd = new RpcCmd();
                redirectCmd.setRemoteAddressKey(targetAddressKey);
                redirectCmd.setRandomKey(redirectCmd.emptyKey());
                redirectCmd.setMessage(redirectMessage);

                String contentData = p2pMessage.getMessage();
                log.info("服务端转发：「{}」to「{}」的点对点消息: {}", originAddressKey, targetAddressKey, contentData);
                rpcClient.send(redirectCmd);
            } else {
                log.info("非业务请求不作处理。");
            }
        }
    }
}
