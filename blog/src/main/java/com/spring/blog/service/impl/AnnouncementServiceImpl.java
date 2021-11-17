package com.spring.blog.service.impl;

import com.spring.blog.dao.AnnouncementDao;
import com.spring.blog.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (MyAnnouncement)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:52:24
 */
@Service("announcementService")
@RequiredArgsConstructor
public class AnnouncementServiceImpl implements AnnouncementService {
    private final AnnouncementDao announcementDao;

}