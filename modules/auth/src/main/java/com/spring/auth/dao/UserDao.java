package com.spring.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.vo.UserVo;
import com.spring.common.entity.po.User;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2021 -11-13 11:54:50
 */
public interface UserDao extends BaseMapper<User> {


    /**
     * Select exit by email int.
     *
     * @param userEmail the user email
     * @return the int
     */
    int selectExitByEmail(String userEmail);

    /**
     * Select user role my user.
     *
     * @param userEmail the user email
     * @return the my user
     */
    UserVo selectAllByEmail(String userEmail);


}