package com.riemann.springbootdemo.customize.redis;

import redis.clients.jedis.Jedis;

public class RiemannRedisClientTest {
    public static void main(String[] args) {
        //Jedis jedis = new Jedis("192.168.0.105", 6380);
        Jedis jedis = new Jedis("192.168.0.105", 6379);
        jedis.set("riemann", "0328");
    }
}
