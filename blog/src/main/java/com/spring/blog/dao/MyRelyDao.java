package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyRely;

import java.util.List;

/**
 * (MyRely)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
public interface MyRelyDao extends BaseMapper<MyRely> {
  List<MyRely> selectAllByOwner(Integer userId);
}