package com.spring.chat.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.chat.controller.WebSocketForGroup;
import com.spring.chat.dao.ChatGroupDao;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.ChatGroup;
import com.spring.common.entity.po.User;
import com.spring.common.entity.vo.ChatGroupVo;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@Service
@RequiredArgsConstructor
public class ChatGroupService {
    private final ChatGroupDao chatGroupDao;

    public User getUser(Integer userId) {
        return chatGroupDao.getUser(userId);
    }

    public void insert(ChatGroup chatGroup) {
        chatGroupDao.insert(chatGroup);
    }

    public RestMsg getUserList(){
        Set<User> userSet = new HashSet<>(WebSocketForGroup.userSet);
        if (userSet.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(userSet);
    }

    public RestMsg getHistoryMsg(int pageNum ,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<ChatGroupVo> chatGroupVoList = chatGroupDao.getHistoryMsg();
        if(chatGroupVoList.isEmpty()){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,new PageInfo<>(chatGroupVoList));
    }
}
