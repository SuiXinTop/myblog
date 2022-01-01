package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.io.Serializable;

/**
 * (MyRely)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:54:04
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_reply")
public class Reply implements Serializable {

    private static final long serialVersionUID = 790705687502029413L;

    @TableId(type = IdType.AUTO)
    private Integer replyId;

    @NotBlank
    private Integer comId;

    @NotBlank
    private Integer replyOwner;

    @NotBlank
    private String replyBody;

    private Date replyTime;

    @TableLogic(value = "1", delval = "0")
    private Integer replyState;

}