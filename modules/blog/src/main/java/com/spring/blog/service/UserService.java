package com.spring.blog.service;

import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;

import java.util.List;

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
     * @return the rest msg
     */
    RestMsg register(UserRegister user);

    /**
     * Update rest msg.
     *
     * @param user the my user
     * @return the rest msg
     */
    RestMsg update(User user);

    RestMsg recoverState(List<Integer> userIdList);

    RestMsg updateRole(Integer userId, Integer roleId);

        /**
         * Update email rest msg.
         *
         * @param user the my user
         * @return the rest msg
         */
    RestMsg updateSecurity(UserSecurity user);

    /**
     * Select rest msg.
     *
     * @param userId the user id
     * @return the rest msg
     */
    RestMsg selectByUserId(Integer userId);

    RestMsg selectException(int pageNum, int pageSize);

    /**
     * Select by user name rest msg.
     *
     * @param userName the user name
     * @return the rest msg
     */
    RestMsg selectByUserName(String userName, int pageNum, int pageSize);

        /**
         * Delete rest msg.
         *
         * @param userId the user id
         * @return the rest msg
         */
    RestMsg delete(Integer userId);

    RestMsg deleteList(List<Integer> userIdList);
}