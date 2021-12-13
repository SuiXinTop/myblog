package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * (MyComment)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:53:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 454292928361574917L;

    @TableId(type = IdType.AUTO)
    private Integer comId;

    @NotBlank(message = "博客ID不能为空")
    private Integer blogId;

    @NotBlank(message = "用户ID不能为空")
    private Integer comOwner;

    @NotBlank(message = "评论内容不能为空")
    private String comBody;

    private Date comTime;

    @TableLogic(value = "1", delval = "0")
    private Integer comState;

}