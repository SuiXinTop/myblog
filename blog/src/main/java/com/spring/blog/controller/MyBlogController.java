package com.spring.blog.controller;

import com.spring.blog.common.annotation.Log;
import com.spring.blog.service.MyBlogService;
import com.spring.common.enmu.BusinessType;
import com.spring.common.enmu.OperatorType;
import com.spring.common.entity.MyBlog;
import com.spring.common.entity.MyBlogTag;
import com.spring.common.entity.MyHistory;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
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

    @PostMapping("/insert")
    @ApiOperation(value = "新增博客")
    @Log(logName = "新增博客", businessType = BusinessType.INSERT, operatorType = OperatorType.USER)
    public RestMsg insert(@RequestBody MyBlog myBlog) {
        return myBlogService.insert(myBlog);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改博客")
    public RestMsg update(@RequestBody MyBlog myBlog) {
        return myBlogService.update(myBlog);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "逻辑删除博客")
    public RestMsg delete(List<Integer> blogIds) {
        return myBlogService.delete(blogIds);
    }

    @GetMapping("/select")
    @ApiOperation(value = "获取博客内容")
    public RestMsg select(Integer blogId) {
        return myBlogService.select(blogId);
    }

    @GetMapping("/selectNew")
    @ApiOperation(value = "博客列表，时间倒序")
    public RestMsg selectNew(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return myBlogService.selectNew(pageNum, pageSize);
    }

    @GetMapping("/addBrowse")
    @ApiOperation(value = "热搜榜")
    public RestMsg selectByHot() {
        return myBlogService.selectByHot();
    }

    @Async
    @PostMapping("/addView")
    @ApiOperation(value = "增加浏览量")
    public void addView(@RequestBody MyHistory myHistory) {
        myBlogService.addView(myHistory);
    }

    @Async
    @PostMapping("/addLike")
    @ApiOperation(value = "增加点赞数")
    public void addLike(Integer blogId) {
        myBlogService.addLike(blogId);
    }

    @PostMapping("/insertTag")
    @ApiOperation(value = "新建标签关联")
    public RestMsg insertTag(@RequestBody List<MyBlogTag> myBlogTagList) {
        return myBlogService.insertTag(myBlogTagList);
    }

    @DeleteMapping("/deleteTag")
    @ApiOperation(value = "删除标签关联")
    public RestMsg deleteTag(@RequestBody List<Integer> blogTagIds) {
        return myBlogService.deleteTag(blogTagIds);
    }


}