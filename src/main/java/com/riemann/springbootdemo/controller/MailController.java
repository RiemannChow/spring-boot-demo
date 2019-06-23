package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.model.EmailMessage;
import com.riemann.springbootdemo.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author riemann
 * @date 2019/06/23 10:47
 */
@RestController
public class MailController {

    @Autowired
    private MailService mailService;

    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public void sendMailMessage() {
        EmailMessage message = new EmailMessage();
        message.setMessageCode("MissingParameter");
        message.setMessageStatus("Failed");
        message.setCause("缺少参数,请确认");

        mailService.sendMessageMail(message, "测试消息通知", "emailMessage.html");
    }

}
