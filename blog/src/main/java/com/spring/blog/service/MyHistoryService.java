package com.spring.blog.service;

import com.spring.common.model.RestMsg;

/**
 * @author STARS
 */
public interface MyHistoryService {
    RestMsg select(int pageNum, int pageSize, int isAsc, Integer userId);
}
