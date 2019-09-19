package com.riemann.springbootdemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author riemann
 * @date 2019/09/17 22:07
 */
@Controller
public class AopController {
    @RequestMapping(value = "/aop/sayHello",method = RequestMethod.GET)
    @ResponseBody
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
