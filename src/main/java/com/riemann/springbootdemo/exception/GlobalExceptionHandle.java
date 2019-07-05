package com.riemann.springbootdemo.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @author riemann
 * @date 2019/06/08 11:59
 */
@ControllerAdvice
public class GlobalExceptionHandle extends RuntimeException{

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandle(HttpServletRequest request, Exception exception) {
        String url = request.getRequestURL().toString();
        logger.error("URL: " + url);
        logger.error("Excption: ", exception);
        ModelAndView mv = new ModelAndView();
        mv.addObject("exception", exception);
        mv.addObject("url", url);
        mv.setViewName("error");
        return mv;
    }

}
