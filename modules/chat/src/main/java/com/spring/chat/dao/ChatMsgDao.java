package com.spring.chat.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.ChatMsg;

import java.util.List;

/**
 * @author STARS
 */
public interface ChatMsgDao extends BaseMapper<ChatMsg> {

    List<ChatMsg> getLastMsg(Integer channelId);

}
