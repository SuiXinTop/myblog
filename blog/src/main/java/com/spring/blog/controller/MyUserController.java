package com.spring.blog.controller;

import com.spring.blog.common.annotation.PreAuth;
import com.spring.blog.service.MyUserService;
import com.spring.common.entity.MyUser;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * (MyUser)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
@RestController
@RequestMapping("myUser")
@RequiredArgsConstructor
@Api(tags = "用户管理模块")
public class MyUserController {
    private final MyUserService myUserService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public RestMsg register(@RequestBody MyUser myUser,
                            @RequestParam("code") String code) {
        return myUserService.register(myUser, code);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "基本信息")
    @PreAuth
    public RestMsg update(@RequestBody MyUser myUser) {
        return myUserService.update(myUser);
    }

    @PostMapping("/updateSecurity")
    @ApiOperation(value = "修改用户验证信息", notes = "密码,邮箱")
    @PreAuth
    public RestMsg updateSecurity(@RequestBody MyUser myUser,
                               @RequestParam("code") String code) {
        return myUserService.updateSecurity(myUser, code);
    }

    @GetMapping("/select")
    @ApiOperation(value = "查询个人信息")
    public RestMsg select(Integer userId) {
        return myUserService.select(userId);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "逻辑销户")
    @PreAuth
    public RestMsg delete(Integer userId) {
        return myUserService.delete(userId);
    }
}