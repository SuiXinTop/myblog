package com.spring.blog.controller.system;

import com.spring.blog.elastic.SearchService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SearchModel;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-21
 * @描述
 */
@RestController
@RequestMapping("search")
@RequiredArgsConstructor
@Api(tags = "搜索引擎")
public class SearchController {
    private final SearchService searchService;

    @GetMapping("/blog")
    public RestMsg searchBlog(@NotNull @RequestParam(value = "param", defaultValue = "") String param,
                              @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                              @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                              @RequestParam(value = "start", defaultValue = "") String start,
                              @RequestParam(value = "end", defaultValue = "") String end) throws IOException {
        SearchModel searchModel = new SearchModel(param, pageNum, pageSize, 1, start, end);
        return searchService.boolSearchBlog(searchModel);
    }

}
