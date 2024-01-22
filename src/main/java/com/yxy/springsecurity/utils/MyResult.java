package com.yxy.springsecurity.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import static com.yxy.springsecurity.utils.ResultEnum.YF_0000;
import static com.yxy.springsecurity.utils.ResultEnum.YF_9999;


public class MyResult<T> implements Serializable {

    private static final long serialVersionUID = 4580737268023862568L;

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public MyResult() {
    }
    public MyResult(Integer code){
        this.code = code;
    }

    public MyResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public MyResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //是否成功（自定义结果码为1000为成功）
    @JsonIgnore
    public boolean isSuccess(){
        return this.code == 1000;
    }


    //成功时引用

    public static <T> MyResult<T> success(){
        return success(YF_0000);
    }
    public static <T> MyResult<T> success(T data){
        return success(YF_0000,data);
    }
    public static <T> MyResult<T> success(ResultEnum re){
        return success(re,null);
    }
    public static <T> MyResult<T> success(ResultEnum re,T data){
        Integer code = re.getCode();
        String msg = re.getMsg();
        return success(code,msg,data);
    }
    public static <T> MyResult<T> success(Integer code, String msg, T data){
        MyResult<T> result = new MyResult<>(1000);
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }



    //失败时引用

    public static <T> MyResult<T> fail(){
        return fail(YF_9999);
    }
    public static <T> MyResult<T> fail(ResultEnum re){
        return fail(re,null);
    }
    public static <T> MyResult<T> fail(String msg){
        return fail(YF_9999.getCode(),msg,null);
    }
    public static <T> MyResult<T> fail(T data){
        return fail(YF_9999,data);
    }
    public static <T> MyResult<T> fail(ResultEnum re, T data){
        Integer code = re.getCode();
        String msg = re.getMsg();
        return fail(code,msg,data);
    }
    public static <T> MyResult<T> fail(Integer code , String msg, T data){
        MyResult<T> result = new MyResult<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}