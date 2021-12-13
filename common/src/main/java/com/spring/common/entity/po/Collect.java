package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * (MyCollect)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_collect")
public class Collect implements Serializable {

    private static final long serialVersionUID = 552675684727759491L;

    @TableId(type = IdType.AUTO)
    private Integer collectId;

    @NotNull
    private Integer blogId;

    @NotNull
    private Integer userId;

    private Date collectTime;

    @TableLogic(value = "1", delval = "0")
    private Integer collectState;

}