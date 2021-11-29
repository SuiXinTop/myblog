package com.spring.chat.controller;

import com.spring.chat.service.ChatService;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("chat")
@Api(tags = "单聊")
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/channelList/{userId}")
    public RestMsg getChannelList(@PathVariable(value = "userId") Integer userId) {
        return chatService.getChannelList(userId);
    }

    @PostMapping("/channel")
    public RestMsg createChannel(Integer fromUser, Integer toUser) {
        return chatService.createChannel(fromUser, toUser);
    }

    @GetMapping("/lastMsg/{channelId}")
    public RestMsg getLastMsg(@PathVariable(value = "channelId") Integer channelId) {
        return chatService.getLastMsg(channelId);
    }

}
