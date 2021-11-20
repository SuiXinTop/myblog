package com.spring.blog.controller;

import com.spring.blog.service.MyCommentService;
import com.spring.common.entity.MyComment;
import com.spring.common.model.RestMsg;
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
    private final MyCommentService myCommentService;

    @PostMapping("/insert")
    @ApiOperation(value = "新建评论")
    public RestMsg insert(@RequestBody MyComment myComment) {
        return myCommentService.insert(myComment);
    }

    @PostMapping("/update")
    @ApiOperation(value = "修改评论内容")
    public RestMsg update(@RequestBody MyComment myComment) {
        return myCommentService.update(myComment);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除评论")
    public RestMsg delete(@RequestBody List<Integer> comIds) {
        return myCommentService.delete(comIds);
    }

    @GetMapping("/select")
    @ApiOperation(value = "查询个人历史评论")
    public RestMsg select(@RequestParam(value = "userId") Integer userId,
                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return myCommentService.select(userId,pageNum,pageSize);
    }

}