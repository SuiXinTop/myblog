package com.spring.blog.service.impl;

import com.spring.blog.dao.*;
import com.spring.blog.service.DataDisplayService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SystemData;
import com.spring.common.entity.dto.UserDetailData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-18
 * @描述
 */
@Service
@RequiredArgsConstructor
public class DataDisplayServiceImpl implements DataDisplayService {
    private final BlogDao blogDao;
    private final UserDao userDao;
    private final CollectDao collectDao;
    private final AttendDao attendDao;
    private final AnnouncementDao announcementDao;

    @Override
    public RestMsg getUserDetailCount(Integer userId) {
        UserDetailData detailData = new UserDetailData();
        detailData.setBlogCount(blogDao.getBlogCount(userId));
        detailData.setAttendCount(attendDao.getAttendCount(userId));
        detailData.setFansCount(attendDao.getFansCount(userId));
        detailData.setCollectCount(collectDao.getCollectCount(userId));
        return RestMsg.success(detailData);
    }

    @Override
    public RestMsg getAdminDetailData(){
        SystemData systemData=new SystemData();
        systemData.setUserCount(userDao.selectCount());
        systemData.setBlogCount(blogDao.getBlogCountAll());
        systemData.setAnnounceCount(announcementDao.getAnnounceCount());
        return RestMsg.success(systemData);
    }
}
