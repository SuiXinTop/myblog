package com.spring.blog.controller.user;

import com.spring.blog.service.ReplyService;
import com.spring.common.entity.po.Reply;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyRely)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@RestController
@RequestMapping("rely")
@RequiredArgsConstructor
@Api(tags = "回复管理模块(TODO)")
public class ReplyController {
    private final ReplyService replyService;

    @PostMapping("")
    @ApiOperation(value = "新建回复")
    public RestMsg insert(@RequestBody Reply reply) {
        return replyService.insert(reply);
    }

    @PutMapping("")
    @ApiOperation(value = "修改回复内容")
    public RestMsg update(@RequestBody Reply reply) {
        return replyService.update(reply);
    }

    @DeleteMapping("")
    @ApiOperation(value = "删除回复")
    public RestMsg delete(List<Integer> replyIds) {
        return replyService.delete(replyIds);
    }

    @GetMapping("/userId")
    @ApiOperation(value = "查看个人回复")
    public RestMsg select(@RequestParam(value = "userId") Integer userId,
                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return replyService.select(userId,pageNum,pageSize);
    }

}