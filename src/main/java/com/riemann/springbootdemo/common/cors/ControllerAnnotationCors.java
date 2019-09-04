package com.riemann.springbootdemo.common.cors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author riemann
 * @date 2019/08/26 23:13
 */
// 第三种方式：使用注解（局部跨域）
// 1、在控制器上注解
@Controller
@CrossOrigin(origins = "http://xx-domain.com", maxAge = 3600)
public class ControllerAnnotationCors {

   /* @RequestMapping("/hello")
    @ResponseBody
    public String index( ){
        return "Hello World";
    }
*/
}
