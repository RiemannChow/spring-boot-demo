package com.riemann.springbootdemo.config;

import lombok.Data;

import java.util.List;

@Data
public class RedisClusterPoolProperties {

    private List<String> nodes;

    private int timeout;

    private int maxIdle;

    private long maxWaitMillis;

    private int maxTotal;

    private long minEvictableIdleTimeMillis;

}