package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.SysLog;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * (SysLog)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
public interface SysLogDao extends BaseMapper<SysLog> {
    /**
     * Select all by param list.
     *
     * @param status     the status
     * @param startTime the start time
     * @param endTime   the end time
     * @return the list
     */
    List<SysLog> selectAllByParam(@Param("status") Integer status, @Param("startTime") Date startTime, @Param("endTime") Date endTime);
}