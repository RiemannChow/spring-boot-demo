package com.riemann.springbootdemo.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MultiRedisClusterUtil {
    @Resource(name = "userProfileStringRedisTemplate")
    private StringRedisTemplate stringRedisTemplate;

    @Resource(name = "userProfileRedisTemplate")
    private RedisTemplate redisTemplate;

    @Resource(name = "dataStreamStringRedisTemplate")
    private StringRedisTemplate dataStreamStringRedisTemplate;

    @Resource(name = "dataStreamRedisTemplate")
    private RedisTemplate dataStreamRedisTemplate;
}
