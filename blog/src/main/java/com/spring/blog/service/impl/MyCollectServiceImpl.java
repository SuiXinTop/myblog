package com.spring.blog.service.impl;

import cn.hutool.core.date.DateTime;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyBlogDao;
import com.spring.blog.dao.MyCollectDao;
import com.spring.blog.service.MyCollectService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyCollect;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
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
public class MyCollectServiceImpl implements MyCollectService {
    private final MyCollectDao myCollectDao;
    private final MyBlogDao myBlogDao;

    @Override
    public RestMsg select(int pageNum, int pageSize, int userId, int isAsc) {
        PageHelper.startPage(pageNum, pageSize);
        List<MyCollect> list = myCollectDao.selectAllByUserId(userId, isAsc);
        if (list.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS, new PageInfo<>(list));
    }

    @Override
    public RestMsg insert(MyCollect myCollect) {
        myCollect.setCollectTime(new DateTime());
        if (myCollectDao.insert(myCollect) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }
        if (myBlogDao.addCollect(myCollect.getBlogId()) == 0) {
            throw new ServiceException(MsgConstant.INSERT_FAULT);
        }

        return RestMsg.success(MsgConstant.INSERT_SUCCESS, "");
    }

    @Override
    public RestMsg delete(List<Integer> collectIds) {
        List<MyCollect> myCollectList = myCollectDao.selectBatchIds(collectIds);

        myCollectList.forEach(i -> {
            if (myBlogDao.subCollect(i.getBlogId()) == 0) {
                throw new ServiceException(MsgConstant.DELETE_FAULT);
            }
        });

        if (myCollectDao.deleteBatchIds(collectIds) == 0) {
            throw new ServiceException(MsgConstant.DELETE_FAULT);
        }
        return RestMsg.success(MsgConstant.DELETE_SUCCESS, "");
    }

    @Override
    public boolean hasCollect(MyCollect myCollect) {
        return myCollectDao.hasCollect(myCollect) != 0;
    }

}