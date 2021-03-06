package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.Attend;
import com.spring.common.entity.vo.AttendVo;

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
    List<AttendVo> selectFans(Integer attendUserId);

    /**
     * Select attend list.
     *
     * @param fansUserId the fans user id
     * @return the list
     */
    List<AttendVo> selectAttend(Integer fansUserId);

    /**
     * Delete by attend user id and fans user id int.
     *
     * @param attend the attend
     * @return the int
     */
    int deleteByAttendUserIdAndFansUserId(Attend attend);

    /**
     * Check has attend int.
     *
     * @param attend the attend
     * @return the int
     */
    int checkHasAttend(Attend attend);

    int getAttendCount(Integer userId);

    int getFansCount(Integer userId);
}