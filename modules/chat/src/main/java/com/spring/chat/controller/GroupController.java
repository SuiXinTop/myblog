package com.spring.chat.controller;

import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.User;
import com.spring.common.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-29
 * @描述
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("group")
@Api(tags = "群聊")
public class GroupController {

    @GetMapping("/userList")
    @ApiOperation(value = "查询群员列表")
    public RestMsg getUserList() {
        Set<User> userSet = new HashSet<>(WebSocketForGroup.userSet);
        if (userSet.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(userSet);
    }
}
