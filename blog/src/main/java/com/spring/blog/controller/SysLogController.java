package com.spring.blog.controller;

import com.spring.blog.service.SysLogService;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * (SysLog)表控制层
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("sysLog")
@Api(tags = "系统日志模块")
public class SysLogController {

    private final SysLogService sysLogService;

    @GetMapping("/select")
    @ApiOperation(value = "日志查询")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "status", defaultValue = "2") Integer status,
                          @RequestParam(value = "startTime", defaultValue = "") String startTime,
                          @RequestParam(value = "endTime", defaultValue = "") String endTime) {
        return sysLogService.select(status, startTime, endTime, pageNum, pageSize);
    }

}