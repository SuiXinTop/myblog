package com.spring.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.MyHistoryDao;
import com.spring.blog.service.MyHistoryService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyHistory;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class MyHistoryServiceImpl implements MyHistoryService {
    private final MyHistoryDao myHistoryDao;

    @Override
    public RestMsg select(int pageNum, int pageSize, int isAsc, Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<MyHistory> myHistories = myHistoryDao.selectAllByUserId(userId,isAsc);
        if(myHistories.isEmpty()){
            return RestMsg.fail(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,new PageInfo<>(myHistories));
    }

}
