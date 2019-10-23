package com.tony.constants;

/**
 * @author jiangwenjie 2019/10/22
 */
public enum EnumNettyState {
    /**
     *
     */
    REQUEST(100, "发送请求"),
    RESPONSE_OK(200, "响应请求"),
    RESPONSE_ERROR(500, "请求异常");
    private int state;
    private String name;

    EnumNettyState(int state, String name) {
        this.state = state;
        this.name = name;
    }

    public int getState() {
        return state;
    }

    public String getName() {
        return name;
    }
}
