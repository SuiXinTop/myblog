package com.spring.blog.service;

import com.spring.common.entity.po.User;
import com.spring.common.entity.dto.RestMsg;

/**
 * (MyUser)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:54:50
 */
public interface UserService {

    /**
     * Register rest msg.
     *
     * @param user the my user
     * @param code   the code
     * @return the rest msg
     */
    RestMsg register(User user, String code);

    /**
     * Update rest msg.
     *
     * @param user the my user
     * @return the rest msg
     */
    RestMsg update(User user);

    /**
     * Update email rest msg.
     *
     * @param user the my user
     * @param code   the code
     * @return the rest msg
     */
    RestMsg updateSecurity(User user, String code);

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