package com.spring.blog.service.impl;

import com.spring.blog.dao.AttendDao;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.dao.CollectDao;
import com.spring.blog.service.DataDisplayService;
import com.spring.common.entity.dto.RestMsg;
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
    private final CollectDao collectDao;
    private final AttendDao attendDao;

    @Override
    public RestMsg getUserDetailCount(Integer userId) {
        UserDetailData detailData = new UserDetailData();
        detailData.setBlogCount(blogDao.getBlogCount(userId));
        detailData.setAttendCount(attendDao.getAttendCount(userId));
        detailData.setFansCount(attendDao.getFansCount(userId));
        detailData.setCollectCount(collectDao.getCollectCount(userId));
        return RestMsg.success(detailData);
    }
}
