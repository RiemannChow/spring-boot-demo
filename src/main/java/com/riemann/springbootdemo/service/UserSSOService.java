package com.riemann.springbootdemo.service;

import com.riemann.springbootdemo.model.ResultSSO;
import com.riemann.springbootdemo.model.UserSSO;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author riemann
 * @date 2019/06/29 15:46
 */
@Component
public interface UserSSOService {

    ResultSSO registerUser(UserSSO userSSO);

    ResultSSO userLogin(String account, String password,
                        HttpServletRequest request, HttpServletResponse response);

    void logout(String token);

    ResultSSO queryUserByToken(String token);

    UserSSO getUserByToken(String token);

}
