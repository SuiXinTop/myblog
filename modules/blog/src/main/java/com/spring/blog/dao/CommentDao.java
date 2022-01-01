package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.vo.CommentVo;
import com.spring.common.entity.po.Comment;

import java.util.List;

/**
 * (MyComment)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
public interface CommentDao extends BaseMapper<Comment>{
   /**
    * Select by user id list.
    *
    * @param userId the user id
    * @return the list
    */
   List<CommentVo> selectAllByUserId(Integer userId);

   /**
    * Select all by blog id list.
    *
    * @param blogId the blog id
    * @return the list
    */
   List<CommentVo> selectAllByBlogId(Integer blogId);

   int getCommentCount(Integer userId);
}