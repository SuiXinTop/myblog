package com.spring.common.entity.bo;

import com.spring.common.entity.po.BlogTag;
import com.spring.common.entity.po.Tag;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-23
 * @描述
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlogTagMap extends BlogTag implements Serializable {

    private static final long serialVersionUID = -27003107228331626L;

    private Tag tag;
}
