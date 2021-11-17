package com.spring.blog.service.impl;

import com.spring.blog.dao.MyTagDao;
import com.spring.blog.service.MyTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyTag)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@Service("myTagService")
public class MyTagServiceImpl implements MyTagService {
    @Resource
    private MyTagDao myTagDao;

}