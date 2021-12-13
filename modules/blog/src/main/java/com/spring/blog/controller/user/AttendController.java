package com.spring.blog.controller.user;

import com.spring.blog.service.AttendService;
import com.spring.common.entity.po.Attend;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (MyAttend)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:05
 */
@RestController
@RequestMapping("attend")
@Api(tags = "好友管理模块")
@RequiredArgsConstructor
public class AttendController {
    private final AttendService attendService;

    @PostMapping("")
    @ApiOperation(value = "添加关注")
    public RestMsg insert(@Validated @RequestBody Attend attend) {
        return attendService.insert(attend);
    }

    @GetMapping("/attendList")
    @ApiOperation(value = "查看关注列表")
    public RestMsg selectAttend(@NotNull @RequestParam(value = "fansUserId") Integer fansUserId,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return attendService.selectAttend(fansUserId, pageNum, pageSize);
    }

    @GetMapping("/fansList")
    @ApiOperation(value = "查看粉丝列表")
    public RestMsg selectFans(@NotNull @RequestParam(value = "attendUserId") Integer attendUserId,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return attendService.selectFans(attendUserId, pageNum, pageSize);
    }

    @DeleteMapping("")
    @ApiOperation(value = "逻辑删除关注或粉丝")
    public RestMsg delete(@RequestBody List<Integer> attendIdList) {
        return attendService.delete(attendIdList);
    }


}