package com.spring.file.task;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
//    private final BlogDao blogDao;
//    private final DocumentService documentService;
//
//
//    /**
//     * 每5分钟同步mysql中修改的数据到es
//     */
//    @Scheduled(fixedRate = 1800000)
//    public void syncEs() throws IOException {
//        Date now = new DateTime();
//        Date before = DateUtil.offsetSecond(now, -1800);
//        List<Blog> blogList = blogDao.selectUpdate(now, before);
//
//        if (!blogList.isEmpty()) {
//
//            List<String> idList = blogList.stream()
//                    .map(blog -> blog.getBlogId().toString())
//                    .collect(Collectors.toList());
//
//            documentService.updateBulkRequest(ElasticField.BLOG_INDEX, blogList, idList);
//
//            log.info("======同步=======");
//        }
//    }
}
