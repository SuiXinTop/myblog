package com.spring.common.entity.vo;

import com.spring.common.entity.po.History;
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
public class HistoryVo extends History implements Serializable {

    private static final long serialVersionUID = 577892847350734239L;

    private BlogVo blog;
}
