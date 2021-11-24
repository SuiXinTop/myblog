package com.spring.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.spring.common.entity.po.Announcement;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyAnnouncement)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:52:24
 */
public interface AnnouncementService extends IService<Announcement> {
    /**
     * Insert rest msg.
     *
     * @param announcement the announcement
     * @return the rest msg
     */
    RestMsg insert(Announcement announcement);

    /**
     * Update rest msg.
     *
     * @param announcement the announcement
     * @return the rest msg
     */
    RestMsg update(Announcement announcement);

    /**
     * Delete rest msg.
     *
     * @param amtIds the amt ids
     * @return the rest msg
     */
    RestMsg delete(List<Integer> amtIds);

    /**
     * Top rest msg.
     *
     * @param amtIds the amt id
     * @return the rest msg
     */
    RestMsg top(List<Integer> amtIds);

    /**
     * Select rest msg.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @param isAsc    the is asc
     * @return the rest msg
     */
    RestMsg select(int pageNum, int pageSize, int isAsc);
}