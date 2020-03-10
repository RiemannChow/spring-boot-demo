package com.riemann.springbootdemo.controller;

import com.riemann.springbootdemo.enums.ReturnEnum;
import com.riemann.springbootdemo.model.ApiResult;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class RedisLockController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisLockController.class);

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping("/deduct_stock")
    @Transactional
    public ApiResult deductStock() {
        String lockKey = "lockKey";
        String clientId = UUID.randomUUID().toString();
        try {
            Boolean flag = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, clientId, 30, TimeUnit.SECONDS);
            if (!flag) return new ApiResult(ReturnEnum.FAILED, "Not the same lock");
            int stockNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stockNum > 0) {
                int realStockNum = stockNum - 1;
                stringRedisTemplate.opsForValue().set("stock", realStockNum + "");
                LOGGER.info("扣减成功，剩余库存：{}", realStockNum);
            } else {
                LOGGER.info("扣减失败，库存不足");
            }
        } finally {
            if (clientId.equals(stringRedisTemplate.opsForValue().get(lockKey))) {
                stringRedisTemplate.delete(lockKey);
            }
        }
        return new ApiResult();
    }

    @RequestMapping("/deduct_stock_2")
    @Transactional
    public ApiResult deductStock2() {
        String lockKey = "lockKey";
        RLock rLock = redissonClient.getLock(lockKey);
        try {
            rLock.lock();
            int stockNum = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stockNum > 0) {
                int realStockNum = stockNum - 1;
                stringRedisTemplate.opsForValue().set("stock", realStockNum + "");
                LOGGER.info("扣减成功，剩余库存：{}", realStockNum);
            } else {
                LOGGER.info("扣减失败，库存不足");
            }
        } finally {
            rLock.unlock();
        }
        return new ApiResult();
    }
}
