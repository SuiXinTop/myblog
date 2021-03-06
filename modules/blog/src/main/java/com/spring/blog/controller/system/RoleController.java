package com.spring.blog.controller.system;

import com.spring.blog.service.RoleService;
import com.spring.common.entity.dto.RestMsg;
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
@RequestMapping("role")
@Api(tags = "角色管理模块",consumes = "admin")
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/all")
    @ApiOperation(value = "查询所有角色")
    public RestMsg select(){
        return roleService.selectAll();
    }

}