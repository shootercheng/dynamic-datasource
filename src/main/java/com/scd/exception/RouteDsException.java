package com.scd.exception;

/**
 * 数据源路由异常
 * @author chengdu
 * @date 2019/11/24
 */
public class RouteDsException extends RuntimeException {
    public RouteDsException() {
    }

    public RouteDsException(String message) {
        super(message);
    }

    public RouteDsException(String message, Throwable cause) {
        super(message, cause);
    }
}
