package com.spring.myblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.myblog.entity.MyRole;
import com.spring.myblog.entity.MyUser;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (MyUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:54:50
 */
public interface MyUserDao extends BaseMapper<MyRole> {

}