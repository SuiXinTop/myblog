package com.spring.chat.service;

import com.spring.chat.dao.ChatGroupDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
