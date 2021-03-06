package com.spring.blog.controller.blog;

import com.spring.blog.service.BlogService;
import com.spring.common.enmu.BusinessType;
import com.spring.common.enmu.OperatorType;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.History;
import com.spring.security.annotation.Log;
import com.spring.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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

    @PreAuth
    @PostMapping
    @ApiOperation(value = "新增博客")
    @Log(logName = "新增博客", businessType = BusinessType.INSERT, operatorType = OperatorType.USER)
    public RestMsg insert(@Validated(value = Blog.Insert.class) @RequestBody Blog blog) {
        return blogService.insert(blog);
    }

    @PreAuth
    @PostMapping("/temp")
    @ApiOperation(value = "暂存博客")
    public RestMsg saveTemp(@RequestBody Blog blog) {
        return blogService.saveTemp(blog);
    }

    @PreAuth
    @PutMapping
    @ApiOperation(value = "修改博客")
    @Log(logName = "修改博客", businessType = BusinessType.UPDATE, operatorType = OperatorType.USER)
    public RestMsg update(@Validated(value = Blog.Update.class) @RequestBody Blog blog) {
        return blogService.update(blog);
    }

    @PreAuth
    @DeleteMapping
    @ApiOperation(value = "逻辑删除博客")
    @Log(logName = "删除博客", businessType = BusinessType.DELETE, operatorType = OperatorType.USER)
    public RestMsg delete(@RequestBody List<Integer> blogIdList) {
        return blogService.delete(blogIdList);
    }

    @GetMapping
    @ApiOperation(value = "获取博客内容")
    public RestMsg select(@NotNull(message = "博客ID不能为空") @RequestParam(value = "blogId") Integer blogId) {
        return blogService.select(blogId);
    }

    @PreAuth
    @GetMapping("/temp")
    @ApiOperation(value = "获取暂存")
    public RestMsg getTemp(@NotNull(message = "用户ID不能为空") Integer userId) {
        return blogService.getTemp(userId);
    }

    @GetMapping("/blogList")
    @ApiOperation(value = "获取用户的博客列表")
    public RestMsg selectBlogList(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                  @NotNull(message = "用户ID不能为空") @RequestParam(value = "userId") int userId) {
        return blogService.selectBlogList(userId, pageNum, pageSize);
    }

    @GetMapping("/new")
    @ApiOperation(value = "获取新增博客列表")
    public RestMsg selectNew() {
        return blogService.selectNew();
    }

    @GetMapping("/new/{userId}")
    @ApiOperation(value = "获取该用户最新博客10条")
    public RestMsg selectNewByUserId(@NotNull @PathVariable(value = "userId") Integer userId) {
        return blogService.selectNewByUserId(userId);
    }

    @GetMapping("/hot")
    @ApiOperation(value = "热搜榜")
    public RestMsg selectByHot() {
        return blogService.selectHot();
    }

    @PostMapping("/view")
    @ApiOperation(value = "增加浏览量")
    public void addView(@Validated @RequestBody History history) {
        blogService.addView(history);
    }

    @PreAuth
    @PostMapping("/like")
    @ApiOperation(value = "增加点赞数")
    public void addLike(@NotNull @RequestParam(value = "blogId") Integer blogId) {
        blogService.addLike(blogId);
    }

    @PreAuth
    @PostMapping("/tag")
    @ApiOperation(value = "新建标签关联")
    public RestMsg insertTag(@RequestBody List<Integer> tagIdList,
                             @NotNull(message = "博客ID不能为空") @RequestParam(value = "blogId") Integer blogId) {
        return blogService.insertTag(tagIdList,blogId);
    }

    @PreAuth
    @DeleteMapping("/tag")
    @ApiOperation(value = "删除标签关联")
    public RestMsg deleteTag(@RequestBody List<Integer> blogTagIdList,
                             @NotNull(message = "博客ID不能为空") @RequestParam(value = "blogId") Integer blogId) {
        return blogService.deleteTag(blogTagIdList, blogId);
    }

}