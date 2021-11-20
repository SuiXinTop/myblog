package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.AnnouncementDao;
import com.spring.blog.service.AnnouncementService;
import com.spring.common.constant.Dictionary;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.Announcement;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Override
    public RestMsg insert(Announcement announcement) {
        announcement.setAmtTime(new DateTime());
        if (announcementDao.insert(announcement) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg update(Announcement announcement) {
        if (announcementDao.updateById(announcement) == 0) {
            throw new ServiceException(MsgConstant.UPDATE_FAULT);
        }
        return RestMsg.success(MsgConstant.UPDATE_SUCCESS, "");
    }

    @Override
    public RestMsg delete(List<Integer> amtIds) {
        if (announcementDao.deleteBatchIds(amtIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg top(List<Integer> amtIds) {
        Announcement announcement = Announcement.builder().amtTop(1).build();
        amtIds.forEach(i -> {
            announcement.setAmtId(i);
            announcementDao.updateById(announcement);
        });
        return RestMsg.success(MsgConstant.TOP_SUCCESS, "");
    }

    @Override
    public RestMsg select(int pageNum, int pageSize, int isAsc) {
        QueryWrapper<Announcement> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc(isAsc == 1, Dictionary.AMT_TOP)
                .orderByAsc(isAsc == 1, Dictionary.AMT_TIME)
                .orderByDesc(isAsc == 0, Dictionary.AMT_TOP)
                .orderByDesc(isAsc == 0, Dictionary.AMT_TIME);

        PageHelper.startPage(pageNum, pageSize);
        List<Announcement> announcementList = announcementDao.selectList(queryWrapper);
        if(announcementList.isEmpty()){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,new PageInfo<>(announcementList));
    }
}