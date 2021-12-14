package com.spring.chat.controller;

import com.spring.chat.service.ChatService;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

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

    @GetMapping("/channelList")
    @ApiOperation(value = "获取聊天列表")
    public RestMsg getChannelList(@RequestParam(value = "userId") Integer userId) {
        return chatService.getChannelList(userId);
    }

    @PostMapping("/channel")
    @ApiOperation(value = "创建聊天")
    public RestMsg createChannel(Integer fromUser, Integer toUser) {
        return chatService.createChannel(fromUser, toUser);
    }

    @GetMapping("/offLineMsg")
    @ApiOperation(value = "拉取离线消息")
    public RestMsg getOfflineMsg(@NotNull @RequestParam Integer channelId) {
        return chatService.getOfflineMsg(channelId);
    }

    @DeleteMapping("/offLineMsg")
    @ApiOperation(value = "收到后删除离线消息")
    public void deleteOfflineMsg(@NotNull @RequestParam Integer channelId) {
        chatService.deleteOfflineMsg(channelId);
    }

    @PutMapping("/offLineMsg")
    @ApiOperation(value = "收到后更新离线消息")
    public void updateOfflineMsg(@NotNull @RequestParam Integer channelId) {
        chatService.updateOfflineMsg(channelId);
    }

    @PutMapping("/lastTime")
    @ApiOperation(value = "更新最近聊天时间")
    public void updateLastTime(@NotNull Integer channelId) {
        chatService.updateLastTime(channelId);
    }

    @GetMapping("/msgList")
    @ApiOperation(value = "更新最近聊天时间")
    public RestMsg getMsgList(@NotNull Integer channelId,
                              @NotNull @RequestParam int pageNum,
                              @NotNull @RequestParam int pageSize) {
        Integer otherChannelId = WebSocketForChat.getWebSocketPool().get(channelId.toString()).getOtherChannelId();
        return chatService.getMsgList(channelId, otherChannelId,pageNum,pageSize);
    }

}
