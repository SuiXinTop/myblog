package com.spring.auth.service;

import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;

/**
 * @author STARS
 */
public interface EmailService {

    /**
     * 发送验证邮件
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg sendVerifyEmail(EmailCode emailCode);

    /**
     * 核验邮箱验证码
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg verifyEmailCode(EmailCode emailCode);

    /**
     * 用于创建用户或更换新的邮箱
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg sendRegisterMail(EmailCode emailCode);

}
