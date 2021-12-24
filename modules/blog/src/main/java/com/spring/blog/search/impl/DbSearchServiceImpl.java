package com.spring.blog.search.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.spring.blog.dao.BlogDao;
import com.spring.blog.search.SearchService;
import com.spring.common.constant.MsgConstant;
import com.spring.common.entity.dto.RestMsg;
import com.spring.common.entity.vo.BlogVo;
import com.spring.common.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * es服务器过期后启用
 *
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-12-15
 * @描述 es服务器过期后启用
 */
@Service("dbSearch")
@RequiredArgsConstructor
public class DbSearchServiceImpl implements SearchService {
    private final BlogDao blogDao;

    @Override
    public RestMsg searchBlogByParam(String param,int pageNum,int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogVoList = blogDao.selectAllByParam(param);
        if (blogVoList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(new PageInfo<>(blogVoList));
    }

    @Override
    public RestMsg searchBlogByTagId(Integer tagId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<BlogVo> blogVoList = blogDao.selectByTagId(tagId);
        if (blogVoList.isEmpty()) {
            throw new ServiceException(MsgConstant.NO_DATA);
        }
        return RestMsg.success(new PageInfo<>(blogVoList));
    }
}
