package com.spring.blog.controller.system;

import cn.hutool.core.util.StrUtil;
import com.spring.blog.service.AuthService;
import com.spring.blog.service.EmailService;
import com.spring.common.constant.HttpConstant;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserLogin;
import com.spring.common.exception.user.HasLoginException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
    private final HttpServletRequest request;

    @PostMapping("/login")
    @ApiOperation(value = "登录授权", notes = "email,password")
    public RestMsg login(@RequestBody UserLogin user) {
        if (StrUtil.isNotEmpty(request.getHeader(HttpConstant.TOKEN_NAME))) {
            throw new HasLoginException();
        }
        return authService.login(user);
    }

    @PostMapping("/admin")
    @ApiOperation(value = "管理员登录授权", notes = "email,password")
    public RestMsg admin(@RequestBody UserLogin user) {
        if (StrUtil.isNotEmpty(request.getHeader(HttpConstant.TOKEN_NAME))) {
            throw new HasLoginException();
        }
        return authService.admin(user);
    }

    @PostMapping("/email")
    @ApiOperation(value = "邮箱登录授权")
    public RestMsg emailLogin(@RequestBody EmailCode emailCode) {
        if (StrUtil.isNotEmpty(request.getHeader(HttpConstant.TOKEN_NAME))) {
            throw new HasLoginException();
        }
        return authService.login(emailCode);
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "退出账号")
    public RestMsg logout(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        return authService.logout(token);
    }

    @PostMapping("/verifyEmail")
    @ApiOperation(value = "发送邮箱验证码(消息)")
    public RestMsg emailCode(@RequestBody EmailCode emailCode) {
        return emailService.verifyMail(emailCode);
    }

    @PostMapping("/registerMail")
    @ApiOperation(value = "发送注册验证码(消息)")
    public RestMsg sendRegisterMail(@RequestBody EmailCode emailCode) {
        return emailService.registerMail(emailCode);
    }
}
