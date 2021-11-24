package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    
    private Integer blogId;
    
    private Integer comOwner;

    private String comBody;

    private Date comTime;

    @TableLogic(value = "1", delval = "0")
    private Integer comState;

}