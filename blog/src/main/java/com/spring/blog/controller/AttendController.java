package com.spring.blog.controller;

import com.spring.blog.service.MyAttendService;
import com.spring.common.entity.MyAttend;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyAttend)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:05
 */
@RestController
@RequestMapping("myAttend")
@Api(tags = "好友管理模块")
@RequiredArgsConstructor
public class AttendController {
    private final MyAttendService myAttendService;

    @PostMapping("/insert")
    @ApiOperation(value = "添加关注")
    public RestMsg insert(@RequestBody MyAttend myAttend) {
        return myAttendService.insert(myAttend);
    }

    @GetMapping("/selectAttend")
    @ApiOperation(value = "查看关注列表")
    public RestMsg selectAttend(@RequestParam(value = "fansUserId") Integer fansUserId,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return myAttendService.selectAttend(fansUserId, pageNum, pageSize);
    }

    @GetMapping("/selectFans")
    @ApiOperation(value = "查看粉丝列表")
    public RestMsg selectFans(@RequestParam(value = "attendUserId") Integer attendUserId,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return myAttendService.selectFans(attendUserId, pageNum, pageSize);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "逻辑删除关注或粉丝")
    public RestMsg delete(@RequestBody List<Integer> attendIds) {
        return myAttendService.delete(attendIds);
    }


}