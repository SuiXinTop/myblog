package com.spring.blog.controller.user;

import com.spring.blog.service.CommentService;
import com.spring.common.entity.po.Comment;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyComment)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@RestController
@RequestMapping("myComment")
@Api(tags = "评论管理模块")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/insert")
    @ApiOperation(value = "新建评论")
    public RestMsg insert(@RequestBody Comment comment) {
        return commentService.insert(comment);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改评论内容")
    public RestMsg update(@RequestBody Comment comment) {
        return commentService.update(comment);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除评论")
    public RestMsg delete(@RequestBody List<Integer> comIds) {
        return commentService.delete(comIds);
    }

    @GetMapping("/selectByBlogId")
    @ApiOperation(value = "查询博客评论")
    public RestMsg selectByBlogId(@RequestParam(value = "blogId") Integer blogId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.selectByBlogId(blogId, pageNum, pageSize);
    }

    @GetMapping("/selectByUserId")
    @ApiOperation(value = "查询个人历史评论")
    public RestMsg select(@RequestParam(value = "userId") Integer userId,
                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return commentService.selectByUserId(userId, pageNum, pageSize);
    }

}