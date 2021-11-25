package com.spring.blog;

import com.spring.blog.elastic.SearchService;
import com.spring.elastic.service.DocumentService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author STARS
 */
@SpringBootTest
public class BlogApplicationTest {
    @Autowired
    private SearchService searchService;
    @Autowired
    private DocumentService documentService;

    @Test
    @SneakyThrows
    void name() {
        System.out.println(searchService.searchBlogEngine("神魔",1,10));
    }

    @Test
    @SneakyThrows
    void name2() {
        System.out.println((documentService.getDocument("blog","ztosQX0BBmNk28HI1Ok_")).toString());
    }
}
