package com.spring.myblog.service.impl;

import com.spring.myblog.entity.MyTag;
import com.spring.myblog.dao.MyTagDao;
import com.spring.myblog.service.MyTagService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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