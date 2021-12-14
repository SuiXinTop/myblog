package com.spring.chat.controller;

import com.spring.chat.service.ChatGroupService;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@RestController
@RequestMapping("group")
@Api(tags = "群聊")
@RequiredArgsConstructor
public class GroupController {
    private final ChatGroupService chatGroupService;

    @GetMapping("/userList")
    @ApiOperation(value = "查询群员列表")
    public RestMsg getUserList() {
        return chatGroupService.getUserList();
    }

    @GetMapping("/historyMsg")
    public RestMsg getHistoryMsg(@NotNull int pageNum, @NotNull int pageSize) {
        return chatGroupService.getHistoryMsg(pageNum, pageSize);
    }
}
