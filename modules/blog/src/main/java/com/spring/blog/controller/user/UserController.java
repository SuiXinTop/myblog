package com.spring.blog.controller.user;

import com.spring.blog.service.UserService;
import com.spring.common.enmu.BusinessType;
import com.spring.common.enmu.OperatorType;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.UserRegister;
import com.spring.common.entity.dto.UserSecurity;
import com.spring.common.entity.po.User;
import com.spring.security.annotation.Log;
import com.spring.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @Log(logName = "用户注册", businessType = BusinessType.INSERT, operatorType = OperatorType.USER)
    public RestMsg register(@Validated @RequestBody UserRegister user) {
        return userService.register(user);
    }

    @PreAuth
    @PutMapping("/info")
    @ApiOperation(value = "修改用户信息", notes = "基本信息,密码")
    @Log(logName = "修改用户信息", businessType = BusinessType.UPDATE, operatorType = OperatorType.USER)
    public RestMsg update(@RequestBody User user) {
        return userService.update(user);
    }

    @PreAuth
    @PutMapping("/security")
    @ApiOperation(value = "修改用户邮箱", notes = "邮箱")
    @Log(logName = "修改用户邮箱", businessType = BusinessType.UPDATE, operatorType = OperatorType.USER)
    public RestMsg updateSecurity(@RequestBody UserSecurity user) {
        return userService.updateSecurity(user);
    }

    @GetMapping("/userId/{userId}")
    @ApiOperation(value = "查询个人信息")
    public RestMsg selectByUserId(@NotNull @PathVariable(value = "userId") Integer userId) {
        return userService.selectByUserId(userId);
    }

    @GetMapping("/userName/{userName}")
    @ApiOperation(value = "查询个人信息")
    public RestMsg selectByUserName(@NotBlank @PathVariable(value = "userName") String userName,
                                    @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return userService.selectByUserName(userName, pageNum, pageSize);
    }

    @PreAuth
    @DeleteMapping
    @ApiOperation(value = "逻辑销户")
    public RestMsg delete(@NotNull @RequestParam(value = "userId") Integer userId) {
        return userService.delete(userId);
    }
}