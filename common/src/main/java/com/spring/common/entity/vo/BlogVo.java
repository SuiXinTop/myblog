package com.spring.common.entity.vo;

import com.spring.common.entity.po.Blog;
import com.spring.common.entity.po.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-23
 * @描述
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogVo extends Blog implements Serializable {

    private static final long serialVersionUID = -60278110476686887L;

    private User user;

    private List<BlogTagVo> blogTagList;
}
