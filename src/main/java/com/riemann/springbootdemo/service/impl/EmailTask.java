package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.model.EmailMessage;
import com.riemann.springbootdemo.service.MailService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author riemann
 * @date 2019/06/23 18:15
 */
@Component
public class EmailTask extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(EmailTask.class);

    @Autowired
    private MailService mailService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        EmailMessage message = new EmailMessage();
        message.setMessageCode("MissingParameter");
        message.setMessageStatus("Failed");
        message.setCause("缺少参数,请确认");
        //  这里写调用执行定时调度任务的逻辑
        mailService.sendMessageMail(message, "测试消息通知", "emailMessage.html");
        logger.info("------定时任务成功 EmailTask: ------" + new Date());
    }

}
