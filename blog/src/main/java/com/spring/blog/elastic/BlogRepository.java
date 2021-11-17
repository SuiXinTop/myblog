package com.spring.blog.elastic;

import cn.hutool.core.util.StrUtil;
import com.spring.common.entity.MyBlog;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-16
 * @描述
 */
@Component
@RequiredArgsConstructor
public class BlogRepository {
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    /**
     * 判断索引是否存在
     *
     * @param indexName 索引名称
     * @return boolean
     */
    public boolean indexExist(String indexName) {
        if (StrUtil.isEmpty(indexName)) {
            return false;
        }
        return elasticsearchRestTemplate.indexOps(IndexCoordinates.of(indexName)).exists();
    }

    /**
     * 根据索引名称，删除索引
     *
     * @param index 索引类
     */
    public void indexDelete(String index) {
        elasticsearchRestTemplate.indexOps(IndexCoordinates.of(index)).delete();
    }

    /**
     * 索引新增数据
     *
     * @param myBlog the my blog
     * @return the my blog
     */
    public void save(MyBlog myBlog) {
        elasticsearchRestTemplate.save(myBlog);
    }

    /**
     * 批量插入数据
     *
     * @param queries 数据
     * @param index   索引名称
     */
    public void saveBatch(List<IndexQuery> queries, String index) {
        // 批量新增数据，此处数据，不要超过100m，100m是es批量新增的筏值，修改可能会影响性能
        elasticsearchRestTemplate.bulkIndex(queries, IndexCoordinates.of(index));
    }

    public MyBlog getById(Object id) {
        return elasticsearchRestTemplate.get(id.toString(), MyBlog.class);
    }

    /**
     * 根据数据id删除索引
     *
     * @param id 索引id
     */
    public <T> void deleteById(Object id) {
        if (null != id) {
            // 根据索引删除索引id数据
            elasticsearchRestTemplate.delete(id.toString(), MyBlog.class);
        }
    }

    public void deleteDocumentByQuery(String val) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery("blogId", val))
                .build();

        elasticsearchRestTemplate.delete(nativeSearchQuery, MyBlog.class);
    }

    public void deleteDocumentAll() {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .build();
        elasticsearchRestTemplate.delete(nativeSearchQuery, MyBlog.class);
    }
//        /**
//         * 根据id更新索引数据,不存在则创建索引
//         *
//         * @param t     索引实体
//         * @param id    主键
//         * @param index 索引名称
//         * @param <T>   索引实体
//         */
//        public <T> void update (T t, Integer id, String index){
//            // 查询索引中数据是否存在
//            Object data = elasticsearchRestTemplate.get(id.toString(), t.getClass(), IndexCoordinates.of(index));
//            if (data != null) {
//                // 存在则更新
//                UpdateQuery build = UpdateQuery.builder(id.toString()).withDocument(Document.parse(JSON.toJSONString(t))).build();
//                elasticsearchRestTemplate.update(build, IndexCoordinates.of(index));
//            } else {
//                // 不存在则创建
//                elasticsearchRestTemplate.save(t);
//            }
//        }
//

    /**
     * Gets list.
     *
     * @param <T>    the type parameter
     * @param c      the c
     * @param search the search
     * @param index  the index
     * @return the list
     */
    public <T > List < T > getList(Class < T > c, String search, String index) {
            TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("blogTitle", search);
            NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(termQueryBuilder);
            nativeSearchQuery.addSort(Sort.by(Sort.Direction.DESC, "_score"));
            nativeSearchQuery.setTrackTotalHits(true);
            SearchHits<T> taxKnowledgeMatter = elasticsearchRestTemplate.search(nativeSearchQuery, c, IndexCoordinates.of(index));
            List<SearchHit<T>> searchHits = taxKnowledgeMatter.getSearchHits();
            List<T> returnResult = new ArrayList<>();
            searchHits.forEach(item -> {
                T content = item.getContent();
                returnResult.add(content);
            });
            return returnResult;
        }
//
//        public <T > List < T > getList(Class < T > c, String search, int page, int size, Integer siteId, String index){
//            BoolQueryBuilder title = QueryBuilders.boolQuery().
//                    must(QueryBuilders.matchQuery("title", search)).
//                    must(QueryBuilders.termQuery("siteId", siteId));
//            NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(title);
////        nativeSearchQuery.addSort(Sort.by(Sort.Direction.DESC,"_score"));
//            nativeSearchQuery.setPageable(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "_score")));
//            nativeSearchQuery.setTrackTotalHits(true);
//            SearchHits<T> tax_knowledge_matter = elasticsearchRestTemplate.search(nativeSearchQuery, c, IndexCoordinates.of(index));
//            List<SearchHit<T>> searchHits = tax_knowledge_matter.getSearchHits();
//            List<T> returnResult = new ArrayList<>();
//            searchHits.forEach(item -> {
//                T content = item.getContent();
//                returnResult.add(content);
//            });
//            return returnResult;
//        }
//    }
}