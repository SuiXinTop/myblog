package com.spring.blog.controller;

import com.spring.blog.service.MyHistoryService;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
@RestController
@RequestMapping("history")
@Api(tags = "浏览历史模块")
@RequiredArgsConstructor
public class HistoryController {
    private final MyHistoryService myHistoryService;

    @GetMapping("/select")
    @ApiOperation(value = "历史查询")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "isAsc", defaultValue = "0") int isAsc,
                          @RequestParam(value = "userId") Integer userId){
        return myHistoryService.select(pageNum,pageSize,isAsc,userId);
    }
}
