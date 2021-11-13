package com.spring.myblog.controller;

import com.spring.myblog.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@RestController
@RequestMapping("auth")
@Api(tags = "授权模块")
public class AuthController {
    @PostMapping("/login")
    @ApiOperation(value = "登录授权")
    public RestMsg login() {
        return RestMsg.success("退出成功");
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "刷新token")
    public void refresh() {

    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出账号")
    public RestMsg logout() {
        return RestMsg.success("退出成功");
    }
}
