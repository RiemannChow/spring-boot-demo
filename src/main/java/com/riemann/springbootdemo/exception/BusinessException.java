package com.riemann.springbootdemo.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    private Integer code;

    private String errorMsg;

    private Long timestamp;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }
}
