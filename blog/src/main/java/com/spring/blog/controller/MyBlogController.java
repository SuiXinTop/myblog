package com.spring.blog.controller;

import com.spring.blog.common.annotation.Log;
import com.spring.blog.elastic.BlogRepository;
import com.spring.blog.service.MyBlogService;
import com.spring.common.enmu.BusinessType;
import com.spring.common.enmu.OperatorType;
import com.spring.common.entity.MyBlog;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyBlog)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:17
 */
@RestController
@RequestMapping("myBlog")
@Api(tags = "博客管理模块")
@RequiredArgsConstructor
public class MyBlogController {
    private final MyBlogService myBlogService;
    private final BlogRepository blogRepository;

    @PostMapping("/insert")
    @ApiOperation(value = "新增博客")
    @Log(logName = "ss", businessType = BusinessType.INSERT, operatorType = OperatorType.USER)
    public RestMsg insert(@RequestBody MyBlog myBlog) {
        List<MyBlog> s = blogRepository.getList(MyBlog.class, "123123", "myblog");
        return RestMsg.success(s);
    }

    @PutMapping("/update")
    public RestMsg update(@RequestBody MyBlog myBlog) {
        return RestMsg.success("");
    }

    @DeleteMapping("/delete")
    public RestMsg delete(Integer blogId) {
        return RestMsg.success("");
    }

    @GetMapping("/select")
    @ApiOperation(value = "获取博客内容")
    public RestMsg select(int blogId) {
        return myBlogService.select(blogId);
    }

}