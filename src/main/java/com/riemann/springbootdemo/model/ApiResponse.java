package com.riemann.springbootdemo.model;

/**
 * @author riemann
 * @date 2019/04/28 21:50
 */
public class ApiResponse {

    //返回给前台的状态码
    private String statusCode;

    //返回给前台的状态类型
    private String type;

    //返回给前台的信息
    private String message;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
