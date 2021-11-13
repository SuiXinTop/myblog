package com.spring.myblog.common.exception.user;

/**
 * 用户错误记数异常类
 * 
 * @author ruoyi
 */
public class UserPasswordRetryLimitCountException extends UserException {
    private static final long serialVersionUID = 1L;

    public UserPasswordRetryLimitCountException(int retryLimitCount)
    {
        super("同一时段，尝试次数过多");
    }
}
