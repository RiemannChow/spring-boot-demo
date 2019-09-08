package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.model.ResultSSO;
import com.riemann.springbootdemo.service.UserSSOService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author riemann
 * @date 2019/06/29 17:05
 */
@RestController
@RequestMapping("/user")
public class UserSSOController {

    @Autowired
    private UserSSOService userSSOService;

    @RequestMapping(value="/login", method= RequestMethod.POST)
    @ResponseBody
    public ResultSSO userLogin(String username, String password,
                                    HttpServletRequest request, HttpServletResponse response) {
        try {
            ResultSSO result = userSSOService.userLogin(username, password, request, response);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultSSO.build(500, "");
        }
    }

    @RequestMapping(value="/logout/{token}")
    public String logout(@PathVariable String token) {
        userSSOService.logout(token); // 思路是从Redis中删除key，实际情况请和业务逻辑结合
        return "index";
    }

    @RequestMapping("/token/{token}")
    @ResponseBody
    public Object getUserByToken(@PathVariable String token) {
        ResultSSO result = null;
        try {
            result = userSSOService.queryUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = ResultSSO.build(500, "");
        }
        return result;
    }

}
