package com.spring.myblog.common.exception.user;

/**
 * @author xxx
 * @create 2021-11-06
 */
public class HasLoginException extends UserException {
    private static final long serialVersionUID = 1L;

    public HasLoginException() {
        super("请勿重复登陆");
    }
    public HasLoginException(String message) {
        super(message);
    }
}
