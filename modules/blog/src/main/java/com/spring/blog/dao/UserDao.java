package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.vo.UserVo;
import com.spring.common.entity.po.User;
import org.apache.ibatis.annotations.Update;

import java.util.List;

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
  UserVo selectAllByEmail(String userEmail);

  /**
   * Select all by user id my user.
   *
   * @param userId the user id
   * @return the my user
   */
  UserVo selectAllByUserId(Integer userId);

  List<UserVo> selectException();

  /**
   * Select all by user name list.
   *
   * @param userName the user name
   * @return the list
   */
  List<UserVo> selectAllByUserName(String userName);

  @Update("update my_user set user_state = 1 where user_id = #{userId}")
  Integer updateUserState(Integer userId);


}