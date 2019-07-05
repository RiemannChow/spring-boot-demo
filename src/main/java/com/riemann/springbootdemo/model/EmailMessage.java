package com.riemann.springbootdemo.model;

import lombok.Data;

/**
 * @author riemann
 * @date 2019/06/23 10:40
 */
@Data
public class EmailMessage {

    private String messageCode;

    private String messageStatus;

    private String cause;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

}
