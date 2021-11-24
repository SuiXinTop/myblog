package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.HistoryMap;
import com.spring.common.entity.po.History;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-19
 * @描述
 */
public interface HistoryDao extends BaseMapper<History> {
    /**
     * Select all by user id list.
     *
     * @param userId the user id
     * @param isAsc  the is asc
     * @return the list
     */
    List<HistoryMap> selectAllByUserId(@Param("userId") Integer userId, @Param("isAsc") int isAsc);
}
