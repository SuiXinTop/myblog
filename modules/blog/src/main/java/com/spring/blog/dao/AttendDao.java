package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.AttendMap;
import com.spring.common.entity.po.Attend;

import java.util.List;

/**
 * (MyAttend)表数据库访问层
 *
 * @author makejava
 * @since 2021 -11-13 11:53:05
 */
public interface AttendDao extends BaseMapper<Attend> {
    /**
     * Select fans list.
     *
     * @param attendUserId the attend user id
     * @return the list
     */
    List<AttendMap> selectFans(Integer attendUserId);

    /**
     * Select attend list.
     *
     * @param fansUserId the fans user id
     * @return the list
     */
    List<AttendMap> selectAttend(Integer fansUserId);
}