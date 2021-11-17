package com.spring.blog.controller;

import cn.hutool.core.util.StrUtil;
import com.spring.common.exception.user.HasLoginException;
import com.spring.common.model.RestMsg;
import com.spring.common.constant.HttpConstant;
import com.spring.common.entity.EmailCode;
import com.spring.common.entity.MyUser;
import com.spring.blog.service.AuthService;
import com.spring.blog.service.EmailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-13
 * @描述
 */
@RestController
@RequestMapping("auth")
@Api(tags = "授权模块")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final EmailService emailService;

    @PostMapping("/login")
    @ApiOperation(value = "登录授权", notes = "id,email,password")
    public RestMsg login(@RequestBody MyUser myUser,
                         @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (!StrUtil.isEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.login(myUser);
    }

    @PostMapping("/emailLogin")
    @ApiOperation(value = "邮箱登录授权", notes = "email,code")
    public RestMsg emailLogin(@RequestBody EmailCode emailCode,
                              @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (!StrUtil.isEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.emailLogin(emailCode);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "刷新token")
    public void refresh(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        authService.refresh(token);
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "退出账号")
    public RestMsg logout(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        return authService.logout(token);
    }

    @PostMapping("/emailCode")
    @ApiOperation(value = "发送邮箱验证码(消息)")
    public void emailCode(@RequestBody EmailCode emailCode){
        emailService.sendHtmlMail(emailCode);
    }
}
