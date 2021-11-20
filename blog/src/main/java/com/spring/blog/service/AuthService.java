package com.spring.blog.service;

import com.spring.common.model.RestMsg;
import com.spring.common.entity.EmailCode;
import com.spring.common.entity.MyUser;

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
     * @param myUser the my user
     * @return the rest msg
     */
    RestMsg login(MyUser myUser);

    /**
     * Admin rest msg.
     *
     * @param myUser the my user
     * @return the rest msg
     */
    RestMsg admin(MyUser myUser);

    /**
     * Email login rest msg.
     *
     * @param emailCode the email code
     * @return the rest msg
     */
    RestMsg emailLogin(EmailCode emailCode);

    /**
     * Refresh.
     *
     * @param token the token
     */
    void refresh(String token);

    /**
     * Logout rest msg.
     *
     * @param token the token
     * @return the rest msg
     */
    RestMsg logout(String token);
}
