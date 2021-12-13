package com.spring.blog.controller.blog;

import com.spring.blog.service.TagService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.po.Tag;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * (MyTag)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@RestController
@RequestMapping("tag")
@RequiredArgsConstructor
@Api(tags = "博客标签模块")
public class TagController {
    private final TagService tagService;

    @PostMapping
    @ApiOperation(value = "新建标签")
    public RestMsg insert(@NotNull @RequestBody List<Tag> tagList) {
        return tagService.insert(tagList);
    }

    @DeleteMapping
    @ApiOperation(value = "删除标签")
    public RestMsg delete(@NotNull List<Integer> tagIdList) {
        return tagService.delete(tagIdList);
    }

    @GetMapping
    @ApiOperation(value = "查询标签")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return tagService.select(pageNum, pageSize);
    }
}