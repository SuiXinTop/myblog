package com.spring.blog.service;

import com.spring.common.entity.po.User;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;

/**
 * The interface Auth service.
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-13
 * @描述
 */
public interface AuthService {

    /**
     * Login rest msg.
     *
     * @param user the my user
     * @return the rest msg
     */
    RestMsg login(User user);

    /**
     * Email login rest msg.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg login(EmailCode emailCode);

    /**
     * Admin rest msg.
     *
     * @param user the my user
     * @return the rest msg
     */
    RestMsg admin(User user);

    /**
     * Logout rest msg.
     *
     * @param token the token
     * @return the rest msg
     */
    RestMsg logout(String token);
}
