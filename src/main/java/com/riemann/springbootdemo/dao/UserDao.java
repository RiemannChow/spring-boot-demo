package com.riemann.springbootdemo.dao;

import com.riemann.springbootdemo.model.UserDomain;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author riemann
 * @date 2019/04/27 0:28
 */
public interface UserDao {

    int insert(UserDomain record);

    List<UserDomain> selectUsers();

}
