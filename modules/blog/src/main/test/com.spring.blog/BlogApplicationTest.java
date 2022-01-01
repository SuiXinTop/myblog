package com.spring.blog;

import com.spring.blog.search.impl.EsSearchServiceImpl;
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
    private EsSearchServiceImpl esSearchServiceImpl;
    @Autowired
    private DocumentService documentService;

    @Test
    @SneakyThrows
    void name() {
    }

    @Test
    @SneakyThrows
    void name2() {
        System.out.println((documentService.getDocument("blog","ztosQX0BBmNk28HI1Ok_")).toString());
    }
}
