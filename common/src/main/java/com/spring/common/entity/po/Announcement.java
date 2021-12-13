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
 * (MyAnnouncement)实体类
 *
 * @author makejava
 * @since 2021-11-13 11:52:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("my_announcement")
public class Announcement implements Serializable {

    private static final long serialVersionUID = 676809028744947187L;

    @TableId(type = IdType.AUTO)
    private Integer amtId;

    @NotBlank
    private String amtTitle;

    @NotBlank
    private String amtBody;

    private Integer amtTop;

    private Date amtTime;

    @TableLogic(value = "1", delval = "0")
    private Integer amtState;
}