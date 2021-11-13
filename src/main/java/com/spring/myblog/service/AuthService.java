package com.spring.myblog.service;

import com.spring.myblog.common.model.RestMsg;
import com.spring.myblog.entity.MyUser;

/**
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
     * Email login rest msg.
     *
     * @param email the email
     * @param code  the code
     * @return the rest msg
     */
    RestMsg emailLogin(String email,String code);

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
