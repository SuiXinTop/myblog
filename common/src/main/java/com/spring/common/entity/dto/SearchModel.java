package com.spring.common.entity.dto;

import com.spring.common.enmu.SortType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @NotBlank
    private String param;

    @NotNull
    private int pageNum;

    @NotNull
    private int pageSize;

    private SortType sortType;

    private String start;

    private String end;
}
