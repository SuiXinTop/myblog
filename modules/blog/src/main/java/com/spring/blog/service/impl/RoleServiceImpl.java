package com.spring.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.spring.blog.dao.RoleDao;
import com.spring.blog.service.RoleService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.po.Role;
import com.spring.common.exception.ServiceException;
import com.spring.common.entity.dto.RestMsg;
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
public class RoleServiceImpl implements RoleService {
    private final RoleDao roleDao;

    @Override
    public RestMsg selectAll() {
        QueryWrapper<Role> queryWrapper =new QueryWrapper<>();

        List<Role> list = roleDao.selectList(queryWrapper);
        if(list.isEmpty()){
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(MsgConstant.SELECT_SUCCESS,list);
    }

}