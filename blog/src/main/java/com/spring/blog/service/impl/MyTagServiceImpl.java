package com.spring.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.spring.blog.dao.MyTagDao;
import com.spring.blog.service.MyTagService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyTag;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
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
public class MyTagServiceImpl implements MyTagService {
    private final MyTagDao myTagDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public RestMsg insert(List<MyTag> myTagList) {
        if (myTagList.isEmpty()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        myTagList.forEach(myTag -> {
            if (myTagDao.insert(myTag) == 0) {
                throw new ServiceException(MsgConstant.INSERT_FAULT);
            }
        });
        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg delete(List<Integer> tagIds) {
        if (myTagDao.deleteBatchIds(tagIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public RestMsg select(int pageNum, int pageSize) {
        QueryWrapper<MyTag> queryWrapper = new QueryWrapper<>();
        PageHelper.startPage(pageNum, pageSize);
        List<MyTag> myTagList = myTagDao.selectList(queryWrapper);
        if (myTagList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, myTagList);
    }

}