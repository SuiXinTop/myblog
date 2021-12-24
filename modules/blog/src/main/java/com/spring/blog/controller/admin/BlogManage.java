package com.spring.blog.controller.admin;

import com.spring.blog.service.BlogService;
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
 * @创建时间 2021-11-19
 * @描述
 */
@RestController
@RequestMapping("admin/blog")
@RequiredArgsConstructor
@Api(tags = "博客后台操作",consumes = "admin")
public class BlogManage {
    private final BlogService blogService;

    @GetMapping("/normal")
    @ApiOperation(value = "显示正常博客")
    public RestMsg selectNormal(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return blogService.selectNormal(pageNum,pageSize);
    }

    @GetMapping("/exception")
    @ApiOperation(value = "显示异常博客")
    public RestMsg selectException(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return blogService.selectException(pageNum,pageSize);
    }

    @PreRole
    @DeleteMapping
    @ApiOperation(value = "封禁博客")
    public RestMsg delete(@RequestBody List<Integer> blogIdList){
        return blogService.delete(blogIdList);
    }

    @PreRole
    @PutMapping
    @ApiOperation(value = "恢复博客")
    public RestMsg recover(@RequestBody List<Integer> blogIdList){
        return blogService.recoverBlog(blogIdList);
    }
}
