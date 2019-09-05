package com.riemann.springbootdemo.common.cors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author riemann
 * @date 2019/08/26 23:16
 */
// 2、在方法上注解
@Controller
public class MethodAnnotationCors {

    @RequestMapping("/hello")
    @ResponseBody
    @CrossOrigin("http://localhost:8080")
    public String index(){
        return "Hello World";
    }

}
