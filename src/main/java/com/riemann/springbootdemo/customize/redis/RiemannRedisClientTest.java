package com.riemann.springbootdemo.customize.redis;

public class RiemannRedisClientTest {
    public static void main(String[] args) {
//        Jedis jedis = new Jedis("192.168.0.105", 6379);
//        jedis.set("riemann", "0328");

        RiemannRedisClient client = new RiemannRedisClient("192.168.0.105", 6379);
        client.set("riemann", "0328");
        System.out.println(client.get("riemann"));
    }
}
