package com.spring.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.spring.blog.dao.TagDao;
import com.spring.blog.service.TagService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.po.Tag;
import com.spring.common.exception.ServiceException;
import com.spring.common.entity.dto.RestMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * (MyTag)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@Service("myTagService")
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insert(List<Tag> tagList) {
        if (tagList.isEmpty()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        tagList.forEach(myTag -> {
            if (tagDao.insert(myTag) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.INSERT_FAULT);
            }
        });
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, null);
    }

    @Override
    public RestMsg delete(List<Integer> tagIds) {
        if (tagDao.deleteBatchIds(tagIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, null);
    }

    @Override
    public RestMsg select(int pageNum, int pageSize) {
        QueryWrapper<Tag> queryWrapper = new QueryWrapper<>();
        PageHelper.startPage(pageNum, pageSize);
        List<Tag> tagList = tagDao.selectList(queryWrapper);
        if (tagList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, tagList);
    }

}