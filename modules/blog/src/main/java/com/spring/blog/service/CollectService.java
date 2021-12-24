package com.spring.blog.service;

import com.spring.common.entity.po.Collect;
import com.spring.common.entity.dto.RestMsg;

import java.util.List;

/**
 * (MyCollect)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:42
 */
public interface CollectService {
    /**
     * Select rest msg.
     *
     * @param pageNum  the page num
     * @param pageSize the page size
     * @param userId   the user id
     * @param isAsc    the is asc
     * @return the rest msg
     */
    RestMsg select(int pageNum, int pageSize, int userId, int isAsc);

    /**
     * Insert rest msg.
     *
     * @param collect the my collect
     * @return the rest msg
     */
    RestMsg insert(Collect collect);

    /**
     * Delete rest msg.
     *
     * @param collectId the collect id
     * @return the rest msg
     */
    RestMsg deleteList(List<Integer> collectId);

    RestMsg delete(Collect collect);

    /**
     * Has collect boolean.
     *
     * @param collect the my collect
     * @return the boolean
     */
    boolean hasCollect(Collect collect);

}