package com.riemann.springbootdemo.customize.redis;

/**
 * API层 RiemannRedisClient
 * 顶层API，供用户使用。
 */
public class RiemannRedisClient {

    private RiemannRedisConnection connection;

    public RiemannRedisClient(String host, Integer port) {
        connection = new RiemannRedisConnection(host, port);
    }

    public String set(String key, String value) {
        connection.sendCommand(RiemannCommand.SET, key.getBytes(), value.getBytes());
        return connection.response();
    }

    public String get(String key) {
        connection.sendCommand(RiemannCommand.GET, key.getBytes());
        return connection.response();
    }

    public String ping() {
        connection.sendCommand(RiemannCommand.PING);
        return connection.response();
    }
}
