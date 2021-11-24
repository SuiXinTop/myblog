package com.spring.blog.controller.system;

import cn.hutool.core.util.StrUtil;
import com.spring.blog.service.AuthService;
import com.spring.blog.service.EmailService;
import com.spring.common.constant.HttpConstant;
import com.spring.common.entity.dto.EmailCode;
import com.spring.common.entity.po.User;
import com.spring.common.exception.user.HasLoginException;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
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
    @ApiOperation(value = "登录授权", notes = "email,password")
    public RestMsg login(@RequestBody User user,
                         @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (StrUtil.isNotEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.login(user);
    }

    @PostMapping("/admin")
    @ApiOperation(value = "管理员登录授权", notes = "email,password")
    public RestMsg admin(@RequestBody User user,
                         @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (StrUtil.isNotEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.admin(user);
    }

    @PostMapping("/emailLogin")
    @ApiOperation(value = "邮箱登录授权", notes = "email,code")
    public RestMsg emailLogin(@RequestBody EmailCode emailCode,
                              @RequestHeader(value = HttpConstant.TOKEN_NAME, required = false) String token) {
        if (StrUtil.isNotEmpty(token)) {
            throw new HasLoginException();
        }
        return authService.login(emailCode);
    }

    @DeleteMapping("/logout")
    @ApiOperation(value = "退出账号")
    public RestMsg logout(@RequestHeader(value = HttpConstant.TOKEN_NAME) String token) {
        return authService.logout(token);
    }

    @PostMapping("/emailCode")
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
