package com.spring.blog.controller.user;

import com.spring.blog.service.CollectService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Collect;
import com.spring.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (MyCollect)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("collect")
@Api(tags = "收藏模块")
public class CollectController {
    private final CollectService collectService;

    @GetMapping
    @ApiOperation(value = "查询个人收藏,默认时间倒序")
    public RestMsg select(@NotNull @RequestParam(value = "userId") int userId,
                          @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                          @RequestParam(value = "isAsc", defaultValue = "0") int isAsc) {
        return collectService.select(pageNum, pageSize, userId, isAsc);
    }

    @PreAuth
    @PostMapping
    @ApiOperation(value = "添加收藏")
    public RestMsg insert(@Validated @RequestBody Collect collect) {
        return collectService.insert(collect);
    }

    @PreAuth
    @DeleteMapping("/inList")
    @ApiOperation(value = "个人中心删除收藏")
    public RestMsg deleteList(@RequestBody List<Integer> collectIdList) {
        return collectService.deleteList(collectIdList);
    }

    @PreAuth
    @DeleteMapping("/inBlog")
    @ApiOperation(value = "博客删除收藏")
    public RestMsg delete(@Validated @RequestBody Collect collect) {
        return collectService.delete(collect);
    }

    @PostMapping("/hasCollect")
    @ApiOperation(value = "是否已收藏")
    public boolean hasCollect(@Validated @RequestBody Collect collect) {
        return collectService.hasCollect(collect);
    }


}