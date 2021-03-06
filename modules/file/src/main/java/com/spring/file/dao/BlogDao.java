package com.spring.file.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.po.Blog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @author STARS
 */
public interface BlogDao extends BaseMapper<Blog> {

    /**
     * Select update list.
     *
     * @param now    the now
     * @param before the before
     * @return the list
     */
    List<Blog> selectUpdate(@Param("now") Date now, @Param("before") Date before);


    /**
     * Add view.
     *
     * @param blogId the blog id
     */
    @Update("update my_blog set blog_view = blog_view + 1 where blog_id =#{blogId}")
    void addView(Integer blogId);

    /**
     * Add like.
     *
     * @param blogId the blog id
     */
    @Update("update my_blog set blog_like = blog_like + 1 where blog_id =#{blogId}")
    void addLike(Integer blogId);
}
