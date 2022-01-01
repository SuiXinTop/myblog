package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.vo.BlogVo;
import com.spring.common.entity.po.Blog;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author STARS
 */
public interface BlogDao extends BaseMapper<Blog> {

    /**
     * 获取博客内容
     *
     * @param blogId the blog id
     * @return the my blog
     */
    BlogVo selectByBlogId(Integer blogId);

    /**
     * 获取某用户的所有博客
     *
     * @param userId the user id
     * @return the blog vo
     */
    List<BlogVo> selectByUserId(Integer userId);

    /**
     * 获取该标签的所有博客
     *
     * @param tagId the tag id
     * @return the list
     */
    List<BlogVo> selectByTagId(Integer tagId);

    /**
     * 查询正常博客
     *
     * @return the list
     */
    List<BlogVo> selectNormal();

    /**
     * 查询回收站的博客
     *
     * @return the list
     */
    List<BlogVo> selectException();

    /**
     * 查询最热博客 limit 20
     *
     * @return the list
     */
    List<BlogVo> selectHot();

    /**
     * 查询最新博客 limit 20
     *
     * @return the list
     */
    List<BlogVo> selectNew();

    /**
     * 查询该用户最近发布的博客
     *
     * @param userId the user id
     * @return the list
     */
    List<Blog> selectNewByUserId(Integer userId);

    /**
     * 关键词全文搜索
     *
     * @param param the param
     * @return the list
     */
    List<BlogVo> selectAllByParam(String param);

    /**
     * 将博客状态变为删除
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_state =1 where blog_id = #{blogId}")
    Integer updateBlogState(Integer blogId);

    /**
     * 博客浏览量+1
     *
     * @param blogId the blog id
     */
    @Update("update my_blog set blog_view = blog_view + 1 where blog_id =#{blogId}")
    void addView(Integer blogId);

    /**
     * 博客赞数+1
     *
     * @param blogId the blog id
     */
    @Update("update my_blog set blog_like = blog_like + 1 where blog_id =#{blogId}")
    void addLike(Integer blogId);

    /**
     * 博客收藏数+1
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_collect = blog_collect + 1 where blog_id = #{blogId}")
    Integer addCollect(Integer blogId);

    /**
     * 博客收藏数-1
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_collect = blog_collect - 1 where blog_id = #{blogId}")
    Integer subCollect(Integer blogId);

    /**
     * 博客评论数+1
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_comment = blog_comment + 1 where blog_id = #{blogId}")
    Integer addComment(Integer blogId);

    /**
     * 博客评论数-1
     *
     * @param blogId the blog id
     * @return the integer
     */
    @Update("update my_blog set blog_comment = blog_comment - 1 where blog_id = #{blogId}")
    Integer subComment(Integer blogId);

    int getBlogCount(Integer userId);

    int getBlogCountAll();
}