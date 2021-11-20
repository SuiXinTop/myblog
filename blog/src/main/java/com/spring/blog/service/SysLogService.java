package com.spring.blog.service;

import com.spring.common.model.RestMsg;

/**
 * (SysLog)表服务接口
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
public interface SysLogService {
    /**
     * Select rest msg.
     *
     * @param status    the status
     * @param startTime the start time
     * @param endTime   the end time
     * @param pageNum   the page num
     * @param pageSize  the page size
     * @return the rest msg
     */
    RestMsg select(Integer status, String startTime, String endTime, int pageNum, int pageSize);
}