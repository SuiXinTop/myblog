package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.ReplyMap;
import com.spring.common.entity.po.Reply;

import java.util.List;

/**
 * (MyRely)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
public interface ReplyDao extends BaseMapper<Reply> {
  /**
   * Select all by owner list.
   *
   * @param userId the user id
   * @return the list
   */
  List<ReplyMap> selectAllByOwner(Integer userId);
}