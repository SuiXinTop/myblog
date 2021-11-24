package com.spring.blog.controller.blog;

import com.spring.blog.service.TagService;
import com.spring.common.entity.po.Tag;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * (MyTag)表控制层
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@RestController
@RequestMapping("myTag")
@RequiredArgsConstructor
@Api(tags = "博客标签模块(TODO)")
public class TagController {
    private final TagService tagService;

    @PostMapping("/insert")
    @ApiOperation(value = "新建标签")
    public RestMsg insert(@RequestBody List<Tag> tagList) {
        return tagService.insert(tagList);
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除标签")
    public RestMsg delete(List<Integer> tagIds) {
        return tagService.delete(tagIds);
    }

    @GetMapping("/select")
    @ApiOperation(value = "查询标签")
    public RestMsg select(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return tagService.select(pageNum, pageSize);
    }
}