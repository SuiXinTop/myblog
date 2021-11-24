package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.dao.CollectDao;
import com.spring.blog.service.CollectService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.enmu.Status;
import com.spring.common.entity.bo.CollectMap;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Collect;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (MyCollect)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@Service("myCollectService")
@RequiredArgsConstructor
public class CollectServiceImpl implements CollectService {
    private final CollectDao collectDao;
    private final BlogDao blogDao;

    @Override
    public RestMsg select(int pageNum, int pageSize, int userId, int isAsc) {
        PageHelper.startPage(pageNum, pageSize);
        List<CollectMap> list = collectDao.selectAllByUserId(userId, isAsc);
        if (list.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(list));
    }

    @Override
    public RestMsg insert(Collect collect) {
        collect.setCollectTime(new DateTime());
        if (collectDao.insert(collect) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        if (blogDao.addCollect(collect.getBlogId()) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg delete(List<Integer> collectIds) {
        List<Collect> collectList = collectDao.selectBatchIds(collectIds);

        collectList.forEach(i -> {
            if (blogDao.subCollect(i.getBlogId()) == Status.Exception.ordinal()) {
                throw new ServiceException(MsgConstant.DELETE_FAULT);
            }
        });

        if (collectDao.deleteBatchIds(collectIds) == Status.Exception.ordinal()) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public boolean hasCollect(Collect collect) {
        return collectDao.hasCollect(collect) != Status.Exception.ordinal();
    }

}