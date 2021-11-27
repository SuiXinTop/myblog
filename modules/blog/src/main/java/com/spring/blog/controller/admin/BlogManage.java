package com.spring.blog.controller.admin;

import com.spring.blog.service.BlogService;
import com.spring.common.entity.dto.RestMsg;
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

    @GetMapping("/exception")
    @ApiOperation(value = "显示异常博客")
    public RestMsg selectException(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        return blogService.selectException(pageNum,pageSize);
    }

    @DeleteMapping("")
    @ApiOperation(value = "封禁博客")
    public RestMsg delete(List<Integer> blogIdList){
        return blogService.delete(blogIdList);
    }

    @PutMapping("")
    @ApiOperation(value = "恢复博客")
    public RestMsg recover(List<Integer> blogIdList){
        return blogService.recoverBlog(blogIdList);
    }
}
