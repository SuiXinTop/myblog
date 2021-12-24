package com.spring.blog.controller.admin;

import com.spring.blog.service.UserService;
import com.spring.common.constant.RoleConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.security.annotation.PreRole;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
@RestController
@RequestMapping("admin/user")
@RequiredArgsConstructor
@Api(tags = "用户后台操作",consumes = "admin")
public class UserManage {
    private final UserService userService;

    @GetMapping("/normal")
    @ApiOperation(value = "显示正常用户")
    public RestMsg selectNormal(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return userService.selectNormal(pageNum,pageSize);
    }

    @GetMapping("/exception")
    @ApiOperation(value = "显示异常用户")
    public RestMsg selectException(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return userService.selectException(pageNum,pageSize);
    }

    @PreRole
    @DeleteMapping
    @ApiOperation(value = "封禁用户")
    public RestMsg delete(@RequestBody List<Integer> userIdList){
        return userService.deleteList(userIdList);
    }

    @PreRole
    @PutMapping("/recover")
    @ApiOperation(value = "恢复异常用户")
    public RestMsg recover(@RequestBody List<Integer> userIdList){
        return userService.recoverState(userIdList);
    }

    @PreRole(role = RoleConstant.SUPER_ADMIN)
    @PutMapping("/role")
    @ApiOperation(value = "修改用户角色")
    public RestMsg updateRole(Integer userId,Integer roleId){
        return userService.updateRole(userId,roleId);
    }

}
