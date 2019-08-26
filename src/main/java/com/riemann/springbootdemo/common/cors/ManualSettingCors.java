package com.riemann.springbootdemo.common.cors;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author riemann
 * @date 2019/08/26 23:21
 */
// 第四种方式:手工设置响应头（局部跨域）
public class ManualSettingCors {

    @RequestMapping("/hello")
    @ResponseBody
    public String index(HttpServletResponse response){
        response.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        return "Hello World";
    }

}
