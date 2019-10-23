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
    SIMPLE_ACTION("sm", "普通业务请求")
    ;

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
}
