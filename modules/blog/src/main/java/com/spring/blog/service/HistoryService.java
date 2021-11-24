package com.spring.blog.service;

import com.spring.common.entity.dto.RestMsg;

/**
 * @author STARS
 */
public interface HistoryService {
    RestMsg select(int pageNum, int pageSize, int isAsc, Integer userId);
}
