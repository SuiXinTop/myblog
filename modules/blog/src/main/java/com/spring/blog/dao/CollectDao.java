package com.spring.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.spring.common.entity.bo.CollectMap;
import com.spring.common.entity.po.Collect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * (MyCollect)表数据库访问层
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
public interface CollectDao extends BaseMapper<Collect> {
    /**
     * Select all by user id list.
     *
     * @param userId the user id
     * @param isAsc  the is asc
     * @return the list
     */
    List<CollectMap> selectAllByUserId(@Param("userId") int userId, @Param("isAsc") int isAsc);

    /**
     * Has collect integer.
     *
     * @param collect the my collect
     * @return the integer
     */
    @Select("select count(*) from my_collect where blog_id = #{blogId} and user_id = #{userId} and collect_state = 1")
    int hasCollect(Collect collect);
}