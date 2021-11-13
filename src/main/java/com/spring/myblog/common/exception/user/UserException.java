package com.spring.myblog.common.exception.user;

/**
 * 用户信息异常类
 * 
 * @author ruoyi
 */
public class UserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public UserException() {
        super();
    }

    public UserException(String message) {
        super(message);
    }
}
