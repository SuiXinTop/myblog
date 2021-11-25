package com.spring.blog.elastic;

import cn.hutool.core.util.StrUtil;
import com.spring.common.constant.ElasticField;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SearchModel;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @创建者 SuiXinTop
 * @创建时间 2021-11-25
 * @描述
 */
@Service
@RequiredArgsConstructor
public class SearchService {
    private final RestHighLevelClient restHighLevelClient;

    public Object searchBlogEngine(String param, int pageNum, int pageSize) throws IOException {
        SearchRequest request = new SearchRequest(ElasticField.BLOG_INDEX);
        //构建搜索条件
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

        MultiMatchQueryBuilder multiMatchQuery =
                QueryBuilders.multiMatchQuery(param, ElasticField.BLOG_TITLE, ElasticField.TAG_NAME, ElasticField.USER_NAME);

        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder
                .field(ElasticField.BLOG_TITLE)
                .field(ElasticField.TAG_NAME)
                .field(ElasticField.USER_NAME)
                .preTags("<em>")
                .postTags("</em>");

        sourceBuilder.query(multiMatchQuery)
                .timeout(new TimeValue(60, TimeUnit.SECONDS))
                .from((pageNum - 1) * pageSize).size(pageSize)
                .highlighter(highlightBuilder);

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);

        return response.getHits().getHits();
    }

    public RestMsg boolSearchBlog(SearchModel model) throws IOException {
        SearchRequest request = new SearchRequest(ElasticField.BLOG_INDEX);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery(ElasticField.BLOG_STATE, 1))
                .must(QueryBuilders.multiMatchQuery(model.getParam(), ElasticField.BLOG_TITLE, ElasticField.TAG_NAME, ElasticField.USER_NAME, ElasticField.BLOG_BODY));

        if (StrUtil.isNotEmpty(model.getStart()) && StrUtil.isNotEmpty(model.getEnd())) {
            boolQueryBuilder.filter(QueryBuilders.rangeQuery(ElasticField.TAG_NAME).gte(model.getStart()).lte(model.getEnd()));
        }

        sourceBuilder.query(boolQueryBuilder)
                .sort(ElasticField.BLOG_TIME)
                .timeout(new TimeValue(60, TimeUnit.SECONDS))
                .from((model.getPageNum() - 1) * model.getPageSize()).size(model.getPageSize());

        request.source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        return RestMsg.success(response.getHits().getHits());
    }
}
