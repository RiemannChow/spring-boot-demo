package com.riemann.springbootdemo.config;

import com.riemann.springbootdemo.service.impl.EmailTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author riemann
 * @date 2019/06/23 18:21
 */
@Configuration
public class TaskConfig {

    // 定义要执行的EmailTask任务类
    @Bean
    public JobDetail emailJobDetail() {
        return JobBuilder.newJob(EmailTask.class).withIdentity("emailJob").storeDurably().build();
    }

    // Simple触发器定义与设置
    /*@Bean
    public SimpleTrigger emailJobDetailTrigger() {
        // Simple类型：可设置时间间隔、是否重复、触发频率（misfire机制）等
        SimpleScheduleBuilder ssb = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5).repeatForever();

        // 一个Trigger只对应一个Job，Schedule调度器调度Trigger执行对应的Job
        SimpleTrigger sTrigger = TriggerBuilder.newTrigger().forJob(emailJobDetail()).
                withIdentity("sampleTrigger").withDescription("simple类型的触发器").withSchedule(ssb).build();
        return sTrigger;

    }*/

    // Cron触发器定义与设置
    @Bean
    public CronTrigger emailTaskyCronTrigger() {
        // Cron类型：通过cron表达式设置触发规则
        CronScheduleBuilder csb = CronScheduleBuilder.cronSchedule(String.format("0 0 0 15 * ?")) //每月15号
                .withMisfireHandlingInstructionDoNothing();

        // 一个Trigger只对应一个Job，Schedule调度器调度Trigger执行对应的Job
        CronTrigger cTrigger = TriggerBuilder.newTrigger().forJob(emailJobDetail()).
                withIdentity("emailTaskCronTrigger").withDescription("corn类型的触发器").withSchedule(csb).startNow().build();
        return cTrigger;
    }

}
