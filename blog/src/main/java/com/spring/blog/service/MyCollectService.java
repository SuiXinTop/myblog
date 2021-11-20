package com.spring.blog.service;

import com.spring.common.entity.MyCollect;
import com.spring.common.model.RestMsg;

import java.util.List;

/**
 * (MyCollect)表服务接口
 *
 * @author makejava
 * @since 2021 -11-13 11:53:42
 */
public interface MyCollectService {
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
     * @param myCollect the my collect
     * @return the rest msg
     */
    RestMsg insert(MyCollect myCollect);

    /**
     * Delete rest msg.
     *
     * @param collectId the collect id
     * @return the rest msg
     */
    RestMsg delete(List<Integer> collectId);

    /**
     * Has collect boolean.
     *
     * @param myCollect the my collect
     * @return the boolean
     */
    boolean hasCollect(MyCollect myCollect);

}