package com.spring.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.blog.dao.MyRoleDao;
import com.spring.blog.service.MyRoleService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.MyRole;
import com.spring.common.exception.ServiceException;
import com.spring.common.model.RestMsg;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (MyRole)表服务实现类
 *
 * @author makejava
 * @since 2021-11-13 11:54:13
 */
@Service("myRoleService")
@RequiredArgsConstructor
public class MyRoleServiceImpl implements MyRoleService {
    private final MyRoleDao myRoleDao;

    @Override
    public RestMsg selectAll() {
        QueryWrapper<MyRole> queryWrapper =new QueryWrapper<>();

        List<MyRole> list = myRoleDao.selectList(queryWrapper);
        if(list.isEmpty()){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,list);
    }

}