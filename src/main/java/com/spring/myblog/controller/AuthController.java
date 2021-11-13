package com.spring.myblog.controller;

import cn.hutool.core.util.StrUtil;
import com.spring.myblog.common.constant.HttpConstant;
import com.spring.myblog.common.exception.user.HasLoginException;
import com.spring.myblog.common.model.RestMsg;
import com.spring.myblog.entity.MyUser;
import com.spring.myblog.service.AuthService;
import com.spring.myblog.service.EmailService;
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
    public RestMsg emailLogin(String email,
                              String code,
                              @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (!StrUtil.isEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.emailLogin(email, code);
    }

    @PostMapping("/refresh")
    @ApiOperation(value = "刷新token")
    public void refresh(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        authService.refresh(token);
    }

    @PostMapping("/logout")
    @ApiOperation(value = "退出账号")
    public RestMsg logout(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        return authService.logout(token);
    }

    @Async
    @PostMapping("/emailCode")
    @ApiOperation(value = "发送邮箱验证码")
    public void emailCode(String path, String code){
        emailService.sendHtmlMail(path,code);
    }
}
