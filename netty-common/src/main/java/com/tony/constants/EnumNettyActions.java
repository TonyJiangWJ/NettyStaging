package com.tony.constants;

/**
 * @author jiangwenjie 2019/10/22
 */
public enum EnumNettyActions {
    /**
     *
     */
    DISCONNECT("dc", "断开连接"),
    NEW_CONNECT("nc", "新建连接"),
    HEART_CHECK("hc", "心跳检测"),
    BROADCAST("bc", "广播消息"),
    P2P("p2p", "点对点消息"),
    AUTHORIZE("auth", "认证请求"),
    SIMPLE_ACTION("sm", "普通业务请求");

    private String actionKey;
    private String actionName;

    EnumNettyActions(String actionKey, String actionName) {
        this.actionName = actionName;
        this.actionKey = actionKey;
    }

    public String getActionName() {
        return actionName;
    }

    public String getActionKey() {
        return actionKey;
    }

    public static EnumNettyActions getActionByKey(String key) {
        for (EnumNettyActions value : values()) {
            if (value.actionKey.equals(key)) {
                return value;
            }
        }
        throw new IllegalArgumentException("action for key:" + key + " is not exist");
    }
}
