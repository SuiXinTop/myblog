package com.spring.blog.service;

import com.spring.common.entity.po.Tag;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyTag)表服务接口
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
public interface TagService {

    RestMsg insert(List<Tag> tagList);

    RestMsg delete(List<Integer> tagIds);

    RestMsg select(int pageNum, int pageSize);

}