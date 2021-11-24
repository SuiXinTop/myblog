package com.spring.blog.elastic;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.spring.common.constant.ElasticField;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.dto.SearchModel;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-21
 * @描述
 */
@Service
@RequiredArgsConstructor
public class DocumentService {
    private final RestHighLevelClient restHighLevelClient;

    /**
     * 添加文档
     *
     * @param <T>   the type parameter
     * @param t     the t
     * @param index the index
     * @return rest status
     * @throws IOException the io exception
     */
    public <T> IndexResponse addDocument(T t, String index, String id) throws IOException {
        //创建请求
        IndexRequest request = new IndexRequest(index);
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        //将我们的数据放入请求
        request.source(JSON.toJSONString(t), XContentType.JSON);
        //客户端发送请求
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return response;
    }

    public <T> IndexResponse addDocument(T t, String index) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.timeout(TimeValue.timeValueSeconds(1))
                .source(JSON.toJSONString(t), XContentType.JSON);
        IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        return response;
    }

    public DeleteResponse deleteDocument(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(1));
        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return response;
    }

    public <T> UpdateResponse updateDocument(T t, String index, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(1))
                .doc(JSON.toJSONString(t), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return response;
    }

    public <T> boolean addBulkRequest(String index, List<T> list) throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout(TimeValue.timeValueSeconds(1));

        list.forEach(i -> {
            //批量更新或者删除只需修改方法即可
            request.add(new IndexRequest(index)
                    .source(JSON.toJSONString(i), XContentType.JSON));
        });
        BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    public Map<String, Object> getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

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
