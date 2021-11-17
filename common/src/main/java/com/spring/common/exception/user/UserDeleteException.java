package com.spring.common.exception.user;

/**
 * 用户账号已被删除
 * 
 * @author ruoyi
 */
public class UserDeleteException extends UserException{
    private static final long serialVersionUID = 1L;

    public UserDeleteException() {
        super("帐号已删户");
    }
}
