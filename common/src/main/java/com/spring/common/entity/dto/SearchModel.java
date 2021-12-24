package com.spring.common.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author STARS
 * @创建者 SuiXinTop
 * @创建时间 2021-11-21
 * @描述
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchModel implements Serializable {
    private static final long serialVersionUID = -1242493306307174690L;

    private String param;

    private int pageNum;

    private int pageSize;

    private int sortType;

    private String start;

    private String end;
}
