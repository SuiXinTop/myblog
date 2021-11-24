package com.spring.blog.service;

import com.spring.common.entity.po.Attend;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyAttend)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:05
 */
public interface AttendService {
    /**
     * Insert rest msg.
     *
     * @param attend the my attend
     * @return the rest msg
     */
    RestMsg insert(Attend attend);

    /**
     * Select attend rest msg.
     *
     * @param fansUserId the fans user id
     * @param pageNum    the page num
     * @param pageSize   the page size
     * @return the rest msg
     */
    RestMsg selectAttend(Integer fansUserId, int pageNum, int pageSize);

    /**
     * Select fans rest msg.
     *
     * @param attendUserId the attend user id
     * @param pageNum      the page num
     * @param pageSize     the page size
     * @return the rest msg
     */
    RestMsg selectFans(Integer attendUserId, int pageNum, int pageSize);

    /**
     * Delete rest msg.
     *
     * @param attendIds the attend ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> attendIds);
}