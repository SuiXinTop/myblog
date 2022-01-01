package com.spring.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.HistoryDao;
import com.spring.blog.service.HistoryService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.vo.HistoryVo;
import com.spring.common.entity.dto.RestMsg;
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
public class HistoryServiceImpl implements HistoryService {
    private final HistoryDao historyDao;

    @Override
    public RestMsg select(int pageNum, int pageSize, int isAsc, Integer userId) {
        PageHelper.startPage(pageNum,pageSize);
        List<HistoryVo> myHistories = historyDao.selectAllByUserId(userId,isAsc);
        if(myHistories.isEmpty()){
            return RestMsg.fail(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,new PageInfo<>(myHistories));
    }

}
