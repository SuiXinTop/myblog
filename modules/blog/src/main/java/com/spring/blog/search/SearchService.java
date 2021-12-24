package com.spring.blog.search;

import com.spring.common.entity.dto.RestMsg;

/**
 * The interface Search service.
 *
 * @author STARS
 */
public interface SearchService {

    /**
     * Bool search blog rest msg.
     *
     * @param param    the param
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg searchBlogByParam(String param,int pageNum,int pageSize);

    /**
     * Search blog by tag id rest msg.
     *
     * @param tagId    the tag id
     * @param pageNum  the page num
     * @param pageSize the page size
     * @return the rest msg
     */
    RestMsg searchBlogByTagId(Integer tagId,int pageNum,int pageSize);
}
