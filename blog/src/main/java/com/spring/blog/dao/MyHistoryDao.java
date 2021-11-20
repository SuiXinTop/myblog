package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.MyHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
public interface MyHistoryDao extends BaseMapper<MyHistory> {
    List<MyHistory> selectAllByUserId(@Param("userId") Integer userId,@Param("isAsc") int isAsc);
}
