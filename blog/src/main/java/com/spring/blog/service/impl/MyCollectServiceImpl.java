package com.spring.blog.service.impl;

import com.spring.blog.dao.MyCollectDao;
import com.spring.blog.service.MyCollectService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyCollect)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@Service("myCollectService")
public class MyCollectServiceImpl implements MyCollectService {
    @Resource
    private MyCollectDao myCollectDao;

}