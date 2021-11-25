package com.spring.blog.controller.user;

import com.spring.blog.service.UserService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;
import com.spring.security.annotation.PreAuth;
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
@RequestMapping("user")
@RequiredArgsConstructor
@Api(tags = "用户管理模块")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    @ApiOperation(value = "用户注册")
    public RestMsg register(@RequestBody UserRegister user) {
        return userService.register(user);
    }

    @PutMapping("/info")
    @ApiOperation(value = "修改用户信息", notes = "基本信息")
    @PreAuth
    public RestMsg update(@RequestBody User user) {
        return userService.update(user);
    }

    @PutMapping("/security")
    @ApiOperation(value = "修改用户验证信息", notes = "密码,邮箱")
    @PreAuth
    public RestMsg updateSecurity(@RequestBody UserSecurity user) {
        return userService.updateSecurity(user);
    }

    @GetMapping("/userId")
    @ApiOperation(value = "查询个人信息")
    public RestMsg selectByUserId(@RequestParam(value = "userId") Integer userId) {
        return userService.selectByUserId(userId);
    }

    @GetMapping("/userName")
    @ApiOperation(value = "查询个人信息")
    public RestMsg selectByUserName(@RequestParam(value = "userName") String userName,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return userService.selectByUserName(userName, pageNum, pageSize);
    }

    @DeleteMapping("")
    @ApiOperation(value = "逻辑销户")
    @PreAuth
    public RestMsg delete(@RequestParam(value = "userId") Integer userId) {
        return userService.delete(userId);
    }
}