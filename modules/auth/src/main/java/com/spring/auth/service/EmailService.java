package com.spring.auth.service;

import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;

/**
 * @author STARS
 */
public interface EmailService {

    /**
     * Send html mail.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg sendVerifyEmail(EmailCode emailCode);

    /**
     * Send register mail rest msg.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg sendRegisterMail(EmailCode emailCode);

}
