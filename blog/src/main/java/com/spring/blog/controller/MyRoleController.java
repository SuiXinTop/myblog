package com.spring.blog.controller;

import com.spring.blog.service.MyRoleService;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * (MyRole)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:54:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("myRole")
@Api(tags = "角色管理模块")
public class MyRoleController {

    private final MyRoleService myRoleService;

    @PostMapping("/select")
    @ApiOperation(value = "查询所有角色")
    public RestMsg select(){
        return myRoleService.selectAll();
    }

}