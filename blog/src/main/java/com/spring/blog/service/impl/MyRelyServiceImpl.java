package com.spring.blog.service.impl;

import com.spring.blog.dao.MyRelyDao;
import com.spring.blog.service.MyRelyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyRely)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@Service("myRelyService")
public class MyRelyServiceImpl implements MyRelyService {
    @Resource
    private MyRelyDao myRelyDao;

}