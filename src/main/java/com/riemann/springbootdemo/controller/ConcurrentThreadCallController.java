package com.riemann.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author riemann
 * @date 2019/10/31 23:06
 */
@Controller
public class ConcurrentThreadCallController {
    @RequestMapping(value = "/ConcurrentThreadCall/sayHello",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
