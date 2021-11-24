package com.spring.blog.controller.user;

import com.spring.security.annotation.PreAuth;
import com.spring.blog.service.UserService;
import com.spring.common.entity.po.User;
import com.spring.common.entity.dto.RestMsg;
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
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public RestMsg register(@RequestBody User user,
                            @RequestParam("code") String code) {
        return userService.register(user, code);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改用户信息", notes = "基本信息")
    @PreAuth
    public RestMsg update(@RequestBody User user) {
        return userService.update(user);
    }

    @PostMapping("/updateSecurity")
    @ApiOperation(value = "修改用户验证信息", notes = "密码,邮箱")
    @PreAuth
    public RestMsg updateSecurity(@RequestBody User user,
                               @RequestParam("code") String code) {
        return userService.updateSecurity(user, code);
    }

    @GetMapping("/select")
    @ApiOperation(value = "查询个人信息")
    public RestMsg select(Integer userId) {
        return userService.select(userId);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "逻辑销户")
    @PreAuth
    public RestMsg delete(Integer userId) {
        return userService.delete(userId);
    }
}