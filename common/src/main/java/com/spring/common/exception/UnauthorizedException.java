package com.spring.common.exception;

/**
 * 授权异常
 *
 * @author zmr
 * @author lucas
 */
public class UnauthorizedException extends RuntimeException {

    private static final String DEFAULT_MSG = "unauthorized";

    private static final long serialVersionUID = 3885400551304383736L;

    public UnauthorizedException(String msg) {
        super(msg);
    }

    public UnauthorizedException() {
        super(DEFAULT_MSG);
    }
}