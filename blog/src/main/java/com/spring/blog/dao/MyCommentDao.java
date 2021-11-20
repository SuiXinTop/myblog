package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyComment;

import java.util.List;

/**
 * (MyComment)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
public interface MyCommentDao extends BaseMapper<MyComment>{
   /**
    * Select by user id list.
    *
    * @param userId the user id
    * @return the list
    */
   List<MyComment> selectByUserId(Integer userId);
}