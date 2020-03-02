package com.riemann.springbootdemo.transaction;

import com.riemann.springbootdemo.dao.UserDao;
import com.riemann.springbootdemo.model.ApiResult;
import com.riemann.springbootdemo.model.UserDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author riemann
 * @date 2020/01/07 20:01
 */
@RestController
public class MyTransactionTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyTransactionTest.class);

    @Resource
    private UserDao userDao;

    @GetMapping(value = "/transactionTest",produces = "application/json;charset=UTF-8")
    @Transactional(rollbackFor = Exception.class)
    public ApiResult transactionTest(UserDomain user) {
        insertUser(user);
        return new ApiResult();
    }

    public void insertUser(UserDomain user) {

        user.setUserId(3);
        user.setUserName("riemann");
        user.setPassword("root");
        user.setPhone("13129535588");
        userDao.insert(user);
        int i = 1 / 0;
        System.out.println(i);
    }
}
