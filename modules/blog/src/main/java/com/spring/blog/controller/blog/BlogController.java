package com.spring.blog.controller.blog;

import com.spring.blog.service.BlogService;
import com.spring.common.enmu.BusinessType;
import com.spring.common.enmu.OperatorType;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.BlogTag;
import com.spring.common.entity.po.History;
import com.spring.security.annotation.Log;
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
@RequestMapping("blog")
@Api(tags = "博客管理模块")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping("")
    @ApiOperation(value = "新增博客")
    @Log(logName = "新增博客", businessType = BusinessType.INSERT, operatorType = OperatorType.USER)
    public RestMsg insert(@RequestBody Blog blog) {
        return blogService.insert(blog);
    }

    @PutMapping("")
    @ApiOperation(value = "修改博客")
    public RestMsg update(@RequestBody Blog blog) {
        return blogService.update(blog);
    }

    @DeleteMapping("")
    @ApiOperation(value = "逻辑删除博客")
    public RestMsg delete(List<Integer> blogIds) {
        return blogService.delete(blogIds);
    }

    @GetMapping("/blogId")
    @ApiOperation(value = "获取博客内容")
    public RestMsg select(Integer blogId) {
        return blogService.select(blogId);
    }

    @GetMapping("/new")
    @ApiOperation(value = "博客列表，时间倒序")
    public RestMsg selectNew() {
        return blogService.selectNew();
    }

    @GetMapping("/hot")
    @ApiOperation(value = "热搜榜")
    public RestMsg selectByHot() {
        return blogService.selectHot();
    }

    @Async
    @PostMapping("/view")
    @ApiOperation(value = "增加浏览量")
    public void addView(@RequestBody History history) {
        blogService.addView(history);
    }

    @Async
    @PostMapping("/like")
    @ApiOperation(value = "增加点赞数")
    public void addLike(Integer blogId) {
        blogService.addLike(blogId);
    }

    @PostMapping("/tag")
    @ApiOperation(value = "新建标签关联")
    public RestMsg insertTag(@RequestBody List<BlogTag> blogTagList) {
        return blogService.insertTag(blogTagList);
    }

    @DeleteMapping("/tag")
    @ApiOperation(value = "删除标签关联")
    public RestMsg deleteTag(@RequestBody List<Integer> blogTagIds,Integer blogId) {
        return blogService.deleteTag(blogTagIds,blogId);
    }


}