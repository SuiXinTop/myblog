package com.spring.rocket.elastic;

import com.alibaba.fastjson.JSON;
import com.spring.common.entity.po.Blog;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
        IndexRequest request = new IndexRequest(index);
        request.id(id);
        request.timeout(TimeValue.timeValueSeconds(1));
        request.source(JSON.toJSONString(t), XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public <T> IndexResponse addDocument(T t, String index) throws IOException {
        IndexRequest request = new IndexRequest(index);
        request.timeout(TimeValue.timeValueSeconds(1))
                .source(JSON.toJSONString(t), XContentType.JSON);
        return restHighLevelClient.index(request, RequestOptions.DEFAULT);
    }

    public DeleteResponse deleteDocument(String index, String id) throws IOException {
        DeleteRequest request = new DeleteRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(1));
        return restHighLevelClient.delete(request, RequestOptions.DEFAULT);
    }

    public <T> UpdateResponse updateDocument(T t, String index, String id) throws IOException {
        UpdateRequest request = new UpdateRequest(index, id);
        request.timeout(TimeValue.timeValueSeconds(1))
                .doc(JSON.toJSONString(t), XContentType.JSON);
        return restHighLevelClient.update(request, RequestOptions.DEFAULT);
    }

    public boolean addBulkRequest(String index, List<Blog> list) throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout(TimeValue.timeValueSeconds(1));

        list.forEach(i -> {
            //批量更新或者删除只需修改方法即可
            request.add(new IndexRequest(index)
                            .id(i.getBlogId().toString())
                    .source(JSON.toJSONString(i), XContentType.JSON));
        });
        BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    public boolean updateBulkRequest(String index, List<Blog> list) throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout(TimeValue.timeValueSeconds(6));
        list.forEach(i -> {
            //批量更新或者删除只需修改方法即可
            request.add(new UpdateRequest(index,i.getBlogId().toString())
                    .doc(JSON.toJSONString(i),XContentType.JSON));
        });
        BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    public <T> boolean delBulkRequest(String index, List<T> list) throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout(TimeValue.timeValueSeconds(1));

        list.forEach(i -> {
            //批量更新或者删除只需修改方法即可
            request.add(new DeleteRequest(index,i.toString()));
        });
        BulkResponse responses = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        return !responses.hasFailures();
    }

    public Map<String, Object> getDocument(String index, String id) throws IOException {
        GetRequest request = new GetRequest(index, id);
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        return response.getSource();
    }

}
