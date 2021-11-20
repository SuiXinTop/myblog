package com.spring.blog.controller;

import com.spring.blog.service.MyCollectService;
import com.spring.common.entity.MyCollect;
import com.spring.common.model.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyCollect)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("myCollect")
@Api(tags = "收藏模块")
public class MyCollectController {
    private final MyCollectService myCollectService;

    @GetMapping("/select")
    @ApiOperation(value = "查询个人收藏,默认时间倒序")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                   @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                   @RequestParam(value = "userId") int userId,
                   @RequestParam(value = "isAsc", defaultValue = "0") int isAsc) {
        return myCollectService.select(pageNum, pageSize, userId, isAsc);
    }

    @PostMapping("/insert")
    @ApiOperation(value = "添加收藏",notes = "blogId,userId")
    public RestMsg insert(@RequestBody MyCollect myCollect) {
        return myCollectService.insert(myCollect);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "逻辑删除收藏，可批量")
    public RestMsg delete(@RequestBody List<Integer> collectId) {
        return myCollectService.delete(collectId);
    }

    @PostMapping("/hasCollect")
    @ApiOperation(value = "是否已收藏",notes = "blogId,userId")
    public boolean hasCollect(@RequestBody MyCollect myCollect){
        return myCollectService.hasCollect(myCollect);
    }


}