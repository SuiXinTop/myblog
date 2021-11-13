package com.spring.myblog.common.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 封装一个返回统一格式数据的结果集
 *
 * @author STARS
 */
@Data
public class RestMsg implements Serializable {
    private int code;
    private String msg;
    private Object data;

    /**
     * 消息返回方法
     *
     * @param code the code
     * @param msg  the msg
     * @param data the data
     * @return result msg
     */
    public static RestMsg success(int code, String msg, Object data) {
        RestMsg rg = new RestMsg();
        rg.setCode(code);
        rg.setMsg(msg);
        rg.setData(data);
        return rg;
    }

    /**
     * Fail result msg.
     *
     * @param code the code
     * @param msg  the msg
     * @param data the data
     * @return the result msg
     */
    public static RestMsg fail(int code, String msg, Object data) {
        RestMsg rg = new RestMsg();
        rg.setCode(code);
        rg.setMsg(msg);
        rg.setData(data);
        return rg;
    }

    /**
     * Success result msg.
     *
     * @param data the data
     * @return the result msg
     */
    public static RestMsg success(Object data) {
        return success(200, "操作成功", data);
    }

    /**
     * Fail result msg.
     *
     * @param msg the msg
     * @return the result msg
     */
    public static RestMsg fail(String msg) {
        return fail(400, msg, null);
    }

    /**
     * Fail result msg.
     *
     * @param msg  the msg
     * @param data the data
     * @return the result msg
     */
    public static RestMsg fail(String msg, Object data) {
        return fail(400, msg, data);
    }
}
