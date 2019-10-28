package com.tony.util;

/**
 * 回调接口 兼容openJdk
 * @author jiangwenjie 2019/10/28
 */
@FunctionalInterface
public interface Callback<P, R> {

    /**
     * 回调接口
     *
     * @param p 参数
     * @return 执行返回值
     */
    R call(P p);
}
