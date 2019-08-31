package com.riemann.springbootdemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author riemann
 * @date 2019/08/23 23:50
 */
@Controller
public class LogbackController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/check/{flag}",method = RequestMethod.GET)
    @ResponseBody
    public String check(@PathVariable("flag") String flag){
        //日志级别从低到高分为TRACE < DEBUG < INFO < WARN < ERROR < FATAL，如果设置为WARN，则低于WARN的信息都不会输出。
        logger.trace("日志输出 trace");
        logger.debug("日志输出 debug");
        logger.info("日志输出 info");
        logger.warn("日志输出 warn");
        logger.error("日志输出 error");
        if ("true".equalsIgnoreCase(flag)) {
            return "success";
        }
        return "false";
    }
}
