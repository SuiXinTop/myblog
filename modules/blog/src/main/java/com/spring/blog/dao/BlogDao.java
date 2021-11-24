package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.BlogMap;
import com.spring.common.entity.po.Blog;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * (MyBlog)表数据库访问层
 *
 * @author makejava
 * @since 2021 -11-13 11:53:17
 */
public interface BlogDao extends BaseMapper<Blog> {

    /**
     * Select by blog id my blog.
     *
     * @param blogId the blog id
     * @return the my blog
     */
    BlogMap selectByBlogId(Integer blogId);

    /**
     * Select hot list.
     *
     * @return the list
     */
    List<BlogMap> selectHot();

    /**
     * Select new list.
     *
     * @return the list
     */
    List<BlogMap> selectNew();

    /**
     * Add collect.
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_collect = blog_collect + 1 where blog_id = #{blogId}")
    Integer addCollect(Integer blogId);

    /**
     * Sub collect integer.
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_collect = blog_collect - 1 where blog_id = #{blogId}")
    Integer subCollect(Integer blogId);

    /**
     * Add comment.
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_comment = blog_comment + 1 where blog_id = #{blogId}")
    Integer addComment(Integer blogId);

    /**
     * Sub comment.
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_comment = blog_comment - 1 where blog_id = #{blogId}")
    Integer subComment(Integer blogId);
}