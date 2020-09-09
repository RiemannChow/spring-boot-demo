package com.riemann.springbootdemo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@Slf4j
@ConfigurationProperties(prefix = "spring.redis.cluster.pool1")
public class UserProfileRedisConfig extends RedisClusterPoolProperties {

    @Bean(name = "userProfileRedisConnectionFactory")
    @Primary
    public RedisConnectionFactory connectionFactory() {
        log.info("************初始化UserProfile集群开始************");
        JedisConnectionFactory factory = new JedisConnectionFactory(new RedisClusterConfiguration(this.getNodes()));
        JedisPoolConfig config = (JedisPoolConfig) factory.getPoolConfig();
        config.setMaxIdle(this.getMaxIdle());
        config.setMaxWaitMillis(this.getMaxWaitMillis());
        config.setMaxTotal(this.getMaxTotal());
        config.setMinEvictableIdleTimeMillis(this.getMinEvictableIdleTimeMillis());
        log.info("************初始化UserProfile集群结束************");
        return factory;
    }

}