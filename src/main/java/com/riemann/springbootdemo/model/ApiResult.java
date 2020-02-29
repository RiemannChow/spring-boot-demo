package com.riemann.springbootdemo.model;

import com.riemann.springbootdemo.enums.ReturnEnum;

/**
 * API接口请求统一返回接口
 */
public class ApiResult<T> {

    private Integer code;

    private T data;

    private String msg;

    public ApiResult() {
        this(ReturnEnum.SUCCESS.getCode(), null, ReturnEnum.SUCCESS.getMessage());
    }

    public ApiResult(T data) {
        this(ReturnEnum.SUCCESS.getCode(), data, ReturnEnum.SUCCESS.getMessage());
    }

    public ApiResult(Integer code, String msg) {
        this(code, null, msg);
    }

    public ApiResult(ReturnEnum returnEnum) {
        this(returnEnum.getCode(), returnEnum.getMessage());
    }

    public ApiResult(ReturnEnum returnEnum, T data) {
        this(returnEnum.getCode(), data, returnEnum.getMessage());
    }

    public ApiResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
