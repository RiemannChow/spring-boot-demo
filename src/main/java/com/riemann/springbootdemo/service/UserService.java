package com.riemann.springbootdemo.service;

import com.github.pagehelper.PageInfo;
import com.riemann.springbootdemo.model.UserDomain;

/**
 * @author riemann
 * @date 2019/04/27 0:23
 */
public interface UserService {

    int addUser(UserDomain user);

    PageInfo<UserDomain> findAllUser(int pageNum, int pageSize);
}
