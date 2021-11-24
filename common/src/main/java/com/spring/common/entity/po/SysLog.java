package com.spring.common.entity.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (SysLog)实体类
 *
 * @author makejava
 * @since 2021-11-13 12:54:13
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 336898678276371831L;

    @TableId(type = IdType.AUTO)
    private Integer logId;

    private String logName;

    private Integer businessType;

    private String methodName;

    private String requestMethod;

    private String operName;

    private Integer operType;

    private String operIp;

    private String operUrl;

    private Integer operStatus;

    private String errorMsg;

    private Date operTime;

}