package com.ccsu.feng.test.enums;


/**
 * @author LX
 * @date 2020/1/29 - 21:43
 */
public enum ResultEnum {

    SUCCESS("200", "成功"),
    ERROR("500", "服务器出错"),
    WAIT("1111", "正在处理结果");

    private String code;

    private String msg;

    ResultEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
