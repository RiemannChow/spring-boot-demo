package com.riemann.springbootdemo.enums;

/**
 * 返回枚举类
 */
public enum ReturnEnum {

    SUCCESS(200, "success"),

    FAILED(500, "failed"),

    PARAM_ERROR(400, "param error");

    private final int code;
    private final String message;

    ReturnEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
