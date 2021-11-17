package com.spring.blog.service.impl;

import com.spring.common.model.RestMsg;
import com.spring.blog.dao.MyBlogDao;
import com.spring.blog.service.MyBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * (MyBlog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
@Service("myBlogService")
@RequiredArgsConstructor
public class MyBlogServiceImpl implements MyBlogService {
    private final MyBlogDao myBlogDao;
    @Override
    public RestMsg select(int blogId){
        return RestMsg.success(myBlogDao.selectByBlogId(blogId));
    }

}