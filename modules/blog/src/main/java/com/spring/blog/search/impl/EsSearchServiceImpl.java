package com.spring.blog.search.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.search.SearchService;
import com.spring.common.constant.ElasticField;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SearchModel;
import com.spring.common.entity.vo.BlogVo;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
@Service("esSearch")
@RequiredArgsConstructor
public class EsSearchServiceImpl implements SearchService {
    private final RestHighLevelClient restHighLevelClient;
    private final BlogDao blogDao;

    @Override
    public RestMsg searchBlogByParam(SearchModel model) {
        SearchRequest request = new SearchRequest(ElasticField.BLOG_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder
                .must(QueryBuilders.termQuery(ElasticField.BLOG_STATE, 1))
                .must(QueryBuilders.multiMatchQuery(model.getParam(), ElasticField.BLOG_TITLE,
                        ElasticField.TAG_NAME, ElasticField.USER_NAME));

        if (StrUtil.isNotEmpty(model.getStart()) && StrUtil.isNotEmpty(model.getEnd())) {
            boolQueryBuilder
                    .filter(QueryBuilders.rangeQuery(ElasticField.TAG_NAME)
                            .gte(model.getStart()).lte(model.getEnd()));
        }

        sourceBuilder
                .query(boolQueryBuilder)
                .sort(ElasticField.BLOG_TIME)
                .timeout(new TimeValue(60, TimeUnit.SECONDS))
                .from((model.getPageNum() - 1) * model.getPageSize())
                .size(model.getPageSize());

        request.source(sourceBuilder);
        try {
            SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
            return RestMsg.success(response.getHits().getHits());
        } catch (IOException e) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
    }

    @Override
    public RestMsg searchBlogByTagId(Integer tagId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogVoList = blogDao.selectByTagId(tagId);
        if (blogVoList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(new PageInfo<>(blogVoList));
    }

}
