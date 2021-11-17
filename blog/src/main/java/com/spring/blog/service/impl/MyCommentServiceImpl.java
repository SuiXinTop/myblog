package com.spring.blog.service.impl;

import com.spring.blog.dao.MyCommentDao;
import com.spring.blog.service.MyCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyComment)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@Service("myCommentService")
public class MyCommentServiceImpl implements MyCommentService {
    @Resource
    private MyCommentDao myCommentDao;

}