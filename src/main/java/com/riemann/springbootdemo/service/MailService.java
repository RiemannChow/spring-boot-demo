package com.riemann.springbootdemo.service;

/**
 * @author riemann
 * @date 2019/06/23 10:24
 */
public interface MailService {

    void sendMessageMail(Object params, String title, String templateName);

}
