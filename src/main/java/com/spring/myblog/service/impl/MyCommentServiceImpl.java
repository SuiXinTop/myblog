package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyComment;
import com.spring.myblog.dao.MyCommentDao;
import com.spring.myblog.service.MyCommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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