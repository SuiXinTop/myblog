package com.spring.blog.service;

import com.spring.common.entity.MyUser;
import com.spring.common.model.RestMsg;

/**
 * (MyUser)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:54:50
 */
public interface MyUserService {

    /**
     * Register rest msg.
     *
     * @param myUser the my user
     * @param code   the code
     * @return the rest msg
     */
    RestMsg register(MyUser myUser,String code);

    /**
     * Update rest msg.
     *
     * @param myUser the my user
     * @return the rest msg
     */
    RestMsg update(MyUser myUser);

    /**
     * Update email rest msg.
     *
     * @param myUser the my user
     * @param code   the code
     * @return the rest msg
     */
    RestMsg updateSecurity(MyUser myUser, String code);

    /**
     * Select rest msg.
     *
     * @param userId the user id
     * @return the rest msg
     */
    RestMsg select(Integer userId);

    /**
     * Delete rest msg.
     *
     * @param userId the user id
     * @return the rest msg
     */
    RestMsg delete(Integer userId);
}