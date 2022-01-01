package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.Announcement;

/**
 * @author STARS
 */
public interface AnnouncementDao extends BaseMapper<Announcement> {

   int getAnnounceCount();
}