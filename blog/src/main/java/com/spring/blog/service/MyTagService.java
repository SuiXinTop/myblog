package com.spring.blog.service;

import com.spring.common.entity.MyTag;
import com.spring.common.model.RestMsg;

import java.util.List;

/**
 * (MyTag)表服务接口
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
public interface MyTagService {

    RestMsg insert(List<MyTag> myTagList);

    RestMsg delete(List<Integer> tagIds);

    RestMsg select(int pageNum, int pageSize);

}