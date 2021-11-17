package com.spring.blog.service;

import com.spring.common.model.RestMsg;

/**
 * (MyBlog)表服务接口
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
public interface MyBlogService {
    RestMsg select(int blogId);
}