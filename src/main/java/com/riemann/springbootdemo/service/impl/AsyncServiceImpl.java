package com.riemann.springbootdemo.service.impl;

import com.riemann.springbootdemo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author riemann
 * @date 2019/09/13 0:18
 */
@Service
@Slf4j
public class AsyncServiceImpl implements AsyncService {

    private static Random random = new Random();

    private static final Logger logger = LoggerFactory.getLogger(AsyncServiceImpl.class);

    @Async
    @Override
    public void doTaskOne() throws Exception {
        logger.info("开始做任务一");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {

        }
        logger.info("任务一 : {}", random.nextInt(100000));
        long end = System.currentTimeMillis();
        logger.info("完成任务一，耗时：" + (end - start) + "毫秒");
    }

    @Async
    @Override
    public void doTaskTwo() throws Exception {
        logger.info("开始做任务二");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {

        }
        logger.info("任务二 : {}", random.nextInt(100000));
        long end = System.currentTimeMillis();
        logger.info("完成任务二，耗时：" + (end - start) + "毫秒");
    }

    @Async
    @Override
    public void doTaskThree() throws Exception {
        logger.info("开始做任务三");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {

        }
        logger.info("任务三 : {}", random.nextInt(100000));
        long end = System.currentTimeMillis();
        logger.info("完成任务三，耗时：" + (end - start) + "毫秒");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskFour() throws Exception {
        logger.info("开始做任务四");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {

        }
        logger.info("任务四 : {}", random.nextInt(100000));
        long end = System.currentTimeMillis();
        logger.info("完成任务四，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("4");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskFive() throws Exception {
        logger.info("开始做任务五");
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {

        }
        logger.info("任务五 : {}", random.nextInt(100000));
        long end = System.currentTimeMillis();
        logger.info("完成任务五，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("5");
    }

    @Async("taskExecutor")
    @Override
    public Future<String> doTaskSix() throws Exception {
        logger.info("开始做任务六");
        long start = System.currentTimeMillis();
        logger.info("任务六 : {}", random.nextInt(100000));
        for (int i = 0; i < 1000000; i++) {

        }
        long end = System.currentTimeMillis();
        logger.info("完成任务六，耗时：" + (end - start) + "毫秒");
        return new AsyncResult<>("6");
    }

}
