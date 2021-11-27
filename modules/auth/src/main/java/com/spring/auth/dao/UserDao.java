package com.spring.auth.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.UserMap;
import com.spring.common.entity.po.User;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2021 -11-13 11:54:50
 */
public interface UserDao extends BaseMapper<User> {

  /**
   * Select user role my user.
   *
   * @param userEmail the user email
   * @return the my user
   */
  UserMap selectAllByEmail(String userEmail);


}