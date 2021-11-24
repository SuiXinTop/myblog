package com.spring.rocket.task;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.spring.common.entity.po.Blog;
import com.spring.rocket.dao.BlogDao;
import com.spring.rocket.elastic.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-21
 * @描述
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateTask {
    private final BlogDao blogDao;
    private final DocumentService documentService;


    /**
     * 每60秒同步mysql中修改的数据到es
     */
    @Scheduled(fixedRate = 60000)
    public void sum() throws IOException {
        Date now = new DateTime();
        Date before = DateUtil.offsetSecond(new DateTime(), -60);
        List<Blog> blogList = blogDao.selectUpdate(now, before);
        if (!blogList.isEmpty()) {
            documentService.updateBulkRequest("blog", blogList);
            log.info("======同步=======");
        }
    }
}
