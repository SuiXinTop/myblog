package com.spring.blog.service;

import com.spring.common.entity.dto.RestMsg;

/**
 * The interface Data display service.
 */
public interface DataDisplayService {


    /**
     * 获取用户的各类数据
     *
     * @param userId the user id
     * @return the user detail count
     */
    RestMsg getUserDetailCount(Integer userId);

    /**
     * 获取系统状态数据
     *
     * @return the admin detail data
     */
    RestMsg getAdminDetailData();
}
