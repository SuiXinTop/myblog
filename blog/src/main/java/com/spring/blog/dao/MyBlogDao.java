package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyBlog;

/**
 * (MyBlog)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
public interface MyBlogDao extends BaseMapper<MyBlog> {

    /**
     * Select by blog id my blog.
     *
     * @param blogId the blog id
     * @return the my blog
     */
    MyBlog selectByBlogId(int blogId);
}