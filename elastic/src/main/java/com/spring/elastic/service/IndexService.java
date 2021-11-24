package com.spring.elastic.service;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * The type Index service.
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-21
 * @描述
 */
@Service
public class IndexService {
    private final RestHighLevelClient restHighLevelClient;

    public IndexService(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * 创建索引
     *
     * @param index the index
     * @throws IOException the io exception
     */
    public boolean createIndex(String index) throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest(index);
        //2. 客户端执行请求 IndicesClient,请求后获得响应
        CreateIndexResponse response = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        //输出是否创建成功
        return response.isAcknowledged();
    }

    /**
     * 查询是否存在索引
     *
     * @param index the index
     * @return the boolean
     * @throws IOException the io exception
     */
    public boolean existIndex(String index) throws IOException {
        GetIndexRequest request = new GetIndexRequest(index);
        return restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
    }

    /**
     * 删除索引
     *
     * @param index the index
     * @throws IOException the io exception
     */
    public boolean deleteIndex(String index) throws IOException {
        //创建删除索引请求对象
        DeleteIndexRequest request = new DeleteIndexRequest(index);
        //客户端执行请求
        AcknowledgedResponse response = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        //输出是否删除成功
        return response.isAcknowledged();
    }

}
