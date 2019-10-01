package com.riemann.springbootdemo.interceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author riemann
 * @date 2019/09/17 0:32
 */
@Configuration
public class MyLoginConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(new MyLoginInterceptor()).addPathPatterns("/*/**");

        //当然可以设置部分不拦截，比如不拦截test下面的a开头的所有接口
        //.excludePathPatterns("/api/test/a/**");

        //拦截全部可以这样 /*/*/**

        // 这里可以注册多个，先注册，先拦截
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
