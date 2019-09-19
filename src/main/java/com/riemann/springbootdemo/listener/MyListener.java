package com.riemann.springbootdemo.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @author riemann
 * @date 2019/09/17 0:11
 */
@WebListener
public class MyListener implements ServletRequestListener {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        log.info("my listener has received request, uri: " + request.getServletPath());
    }


    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        log.info("my listener has sent response, uri: " + request.getServletPath());
    }
}
