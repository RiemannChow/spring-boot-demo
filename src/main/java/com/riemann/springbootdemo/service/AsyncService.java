package com.riemann.springbootdemo.service;

import java.util.concurrent.Future;

/**
 * @author riemann
 * @date 2019/09/13 0:10
 */
public interface AsyncService {

    void doTaskOne() throws Exception;

    void doTaskTwo() throws Exception;

    void doTaskThree() throws Exception;

    Future<String> doTaskFour() throws Exception;

    Future<String> doTaskFive() throws Exception;

    Future<String> doTaskSix() throws Exception;

}
