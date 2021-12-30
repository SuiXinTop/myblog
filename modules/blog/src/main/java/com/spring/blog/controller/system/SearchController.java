package com.spring.blog.controller.system;

import com.spring.blog.search.SearchService;
import com.spring.common.entity.dto.RestMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-21
 * @描述
 */
@RestController
@RequestMapping("search")
@Api(tags = "搜索引擎")
public class SearchController {

    @Resource(name = "dbSearch")
    private SearchService searchService;

    @GetMapping("/param")
    @ApiOperation(value = "搜索引擎")
    public RestMsg searchBlogEs(@NotBlank String param,
                                @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return searchService.searchBlogByParam(param, pageNum, pageSize);
    }

    @GetMapping("/blog/{tagId}")
    @ApiOperation(value = "通过tagId获取博客列表")
    public RestMsg searchBlogByTagId(@NotNull @PathVariable(value = "tagId") Integer tagId,
                                     @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        return searchService.searchBlogByTagId(tagId, pageNum, pageSize);
    }

}
