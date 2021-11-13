package com.spring.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.myblog.entity.MyTag;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (MyTag)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
public interface MyTagDao extends BaseMapper<MyTag>{

}