package com.spring.myblog.common.exception.user;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
public class EmailCodeNotMatchException extends UserException{
    private static final long serialVersionUID = 1L;

    public EmailCodeNotMatchException() {
        super("邮箱验证码错误");
    }
}
