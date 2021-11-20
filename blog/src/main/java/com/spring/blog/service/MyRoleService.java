package com.spring.blog.service;

import com.spring.common.model.RestMsg;

/**
 * (MyRole)表服务接口
 *
 * @author makejava
 * @since 2021-11-13 11:54:13
 */
public interface MyRoleService {
    /**
     * Select all rest msg.
     *
     * @return the rest msg
     */
    RestMsg selectAll();

}