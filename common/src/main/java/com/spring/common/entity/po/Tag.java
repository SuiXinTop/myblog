package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * (MyTag)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_tag")
public class Tag implements Serializable {

    private static final long serialVersionUID = -59456370788776577L;

    @TableId(type = IdType.AUTO)
    private Integer tagId;

    @NotBlank
    private String tagName;

}