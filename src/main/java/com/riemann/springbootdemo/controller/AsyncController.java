package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.service.AsyncService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

/**
 * @author riemann
 * @date 2019/09/13 0:07
 */
@Slf4j
@RestController
@RequestMapping(value = "async")
public class AsyncController {

    private static final Logger logger = LoggerFactory.getLogger(AsyncController.class);

    @Autowired
    AsyncService asyncService;

    /**
     * 异步调用
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncMethods")
    public void asyncMethods() throws Exception {
        long start = System.currentTimeMillis();
        asyncService.doTaskOne();//异步调用
        asyncService.doTaskTwo();//异步调用
        asyncService.doTaskThree();//异步调用
        long end = System.currentTimeMillis();
        logger.info("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncMethodsReturn")
    public void asyncMethodsReturn() throws Exception {

        Future<String> doTaskFour = asyncService.doTaskFour();//异步回调
        Future<String> doTaskFive = asyncService.doTaskFive();//异步回调
        Future<String> doTaskSix = asyncService.doTaskSix();//异步回调

        /*Callable four = () -> {return asyncService.doTaskFour();};
        Callable five = () -> {return asyncService.doTaskFive();};
        Callable six = () -> {return asyncService.doTaskSix();};

        FutureTask<String> fa = new FutureTask<String>(four);
        FutureTask<String> fb = new FutureTask<String>(five);
        FutureTask<String> fc = new FutureTask<String>(six);

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(fa);
        executor.submit(fb);
        executor.submit(fc);

        executor.shutdown();*/

        /*Executors.newSingleThreadExecutor().execute(fa);
        Executors.newSingleThreadExecutor().execute(fb);
        Executors.newSingleThreadExecutor().execute(fc);
        */

        long start = System.currentTimeMillis();
        while (true) {
            if (doTaskFour.isDone() && doTaskFive.isDone() && doTaskSix.isDone()) {
                break;
            }
        }
        Thread.sleep(1000);
        int s = Integer.parseInt(doTaskFour.get()) + Integer.parseInt(doTaskFive.get()) + Integer.parseInt(doTaskSix.get());
        logger.info("调用三个接口值的总和: " + s);

        long end = System.currentTimeMillis();
        logger.info("任务全部完成，总耗时：" + (end - start) + "毫秒");

    }

    /**
     * 异步回调
     *
     * @throws Exception
     */
    @GetMapping(value = "asyncPoolShutDown")
    public void asyncPoolShutDown() throws Exception {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000; i++) {
            asyncService.doTaskOne();//异步调用
            asyncService.doTaskTwo();//异步调用
            asyncService.doTaskThree();//异步调用
            logger.info("+++++++++++++++++++++++ " + i + " +++++++++++++++++++++++++++++++++");
            if (i == 9999) {
                System.exit(0);
            }
        }
        long end = System.currentTimeMillis();
        logger.info("任务全部完成，总耗时：" + (end - start) + "毫秒");
    }


}
