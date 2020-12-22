package com.riemann.springbootdemo.enums;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    /**
     * GET POST 请求不对应异常
     */
    METHOD_NOT_ALLOWED(405, "Method Not Allowed"),

    /**
     * 服务器异常
     */
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),

    /**
     * 基本校验异常
     */
    INVALID_TOKEN_EXCEPTION(100001, "无效token"),
    ARGUMENT_NOT_VALIDATE_EXCEPTION(100002, "参数校验失败"),

    /**
     * IO异常
     */
    UPLOAD_FILE_ERROR_EXCEPTION(200001, "文件上传失败"),
    TIMEOUT_EXCEPTION(200001, "网络超时！"),

    /**
     * 业务型异常
     */
    BUSINESS_EXCEPTION(300000, "业务错误"),
    USER_NICKNAME_IS_NOT_DETECTED(300001, "您的昵称未审核通过！"),
    USER_FEEDBACK_FIVE_TODAY(300002, "今日反馈次数已达上限！");

    ExceptionEnum(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    private Integer code;

    private String errorMsg;

}