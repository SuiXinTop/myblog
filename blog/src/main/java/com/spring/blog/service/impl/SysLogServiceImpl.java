package com.spring.blog.service.impl;

import com.spring.blog.dao.SysLogDao;
import com.spring.blog.service.SysLogService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (SysLog)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {
    @Resource
    private SysLogDao sysLogDao;

}