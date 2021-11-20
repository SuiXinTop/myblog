package com.spring.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

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
public class SysLog implements Serializable {

    private static final long serialVersionUID = 336898678276371831L;

    @TableId(type = IdType.AUTO)
    private Object logId;

    private String logName;

    private Object businessType;

    private String methodName;

    private String requestMethod;

    private String operName;

    private Object operType;

    private String operIp;

    private String operUrl;

    private Object operStatus;

    private String errorMsg;

    private Date operTime;

}