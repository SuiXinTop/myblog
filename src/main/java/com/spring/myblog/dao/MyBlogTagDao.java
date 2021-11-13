package com.spring.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.myblog.entity.MyBlogTag;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (MyBlogTag)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:53:32
 */
public interface MyBlogTagDao extends BaseMapper<MyBlogTag> {

}