package com.spring.rocket.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyBlog;
import org.apache.ibatis.annotations.Update;

/**
 * The interface My blog dao.
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021 -11-19
 * @描述
 */
public interface MyBlogDao extends BaseMapper<MyBlog> {

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
