package com.spring.blog.search.impl;

import com.spring.blog.search.SearchService;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SearchModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * es服务器过期后启用
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-15
 * @描述 es服务器过期后启用
 */
@Service("dbSearch")
@RequiredArgsConstructor
public class DbSearchServiceImpl implements SearchService {

    @Override
    public RestMsg searchBlogByParam(SearchModel model){
        return RestMsg.success("");
    }

    @Override
    public RestMsg searchBlogByTagId(Integer tagId, int pageNum, int pageSize){
        return RestMsg.success("");

    }
}
