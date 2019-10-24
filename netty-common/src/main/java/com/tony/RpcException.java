package com.tony;

import lombok.Data;

/**
 * @author jiangwenjie 2019/10/24
 */
@Data
public class RpcException extends Exception {

    private int code;

    public RpcException() {
        super();
    }

    public RpcException(String message) {
        super(message);
    }

    public RpcException(int code, String message) {
        super(message);
        this.code = code;
    }


    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

    protected RpcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
