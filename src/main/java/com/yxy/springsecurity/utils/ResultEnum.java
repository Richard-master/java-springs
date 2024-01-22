package com.yxy.springsecurity.utils;

public enum ResultEnum {
    YF_0000(1000, "成功"),
    YF_9999(9999,"系统异常");


    private Integer code;
    private String msg;

    ResultEnum(Integer code , String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
