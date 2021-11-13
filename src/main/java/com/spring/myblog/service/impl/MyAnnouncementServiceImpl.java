package com.spring.myblog.service.impl;

import com.spring.myblog.dao.AnnouncementDao;
import com.spring.myblog.service.MyAnnouncementService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyAnnouncement)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:52:24
 */
@Service("myAnnouncementService")
public class MyAnnouncementServiceImpl implements MyAnnouncementService {
    @Resource
    private AnnouncementDao announcementDao;

}