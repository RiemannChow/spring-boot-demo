package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.service.UserSSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author riemann
 * @date 2019/06/29 17:05
 */
@Controller
@RequestMapping("/user")
public class UserSSOController {

    @Autowired
    private UserSSOService userSSOService;



}
