package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyRole;
import com.spring.common.entity.MyUser;
import org.springframework.stereotype.Repository;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
public interface MyUserDao extends BaseMapper<MyRole> {

  /**
   * Select user role my user.
   *
   * @param myUser the my user
   * @return the my user
   */
  MyUser selectUserRole(MyUser myUser);
}