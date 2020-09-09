package com.riemann.springbootdemo.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.Resource;

@Configuration
public class MultiRedisClusterConfig {

    @Resource(name = "userProfileRedisConnectionFactory")
    private RedisConnectionFactory userProfileRedisConnectionFactory;

    @Resource(name = "dataStreamRedisConnectionFactory")
    private RedisConnectionFactory dataStreamRedisConnectionFactory;

    @Bean(name = "userProfileRedisTemplate")
    @Primary
    public RedisTemplate<Object, Object> userProfileRedisTemplate() {
        return getRedisTemplate(userProfileRedisConnectionFactory);
    }

    @Bean(name = "userProfileStringRedisTemplate")
    @Primary
    public StringRedisTemplate userProfileStringRedisTemplate() {
        return getStringRedisTemplate(userProfileRedisConnectionFactory);
    }

    @Bean(name = "dataStreamRedisTemplate")
    public RedisTemplate<Object, Object> dataStreamRedisTemplate() {
        return getRedisTemplate(dataStreamRedisConnectionFactory);
    }

    @Bean(name = "dataStreamStringRedisTemplate")
    public StringRedisTemplate dataStreamStringRedisTemplate() {
        return getStringRedisTemplate(dataStreamRedisConnectionFactory);
    }

    private RedisTemplate<Object,Object> getRedisTemplate(RedisConnectionFactory connectionFactory){
        RedisTemplate<Object,Object> template = new RedisTemplate<Object, Object>();
        template.setConnectionFactory(connectionFactory);
        setSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    private StringRedisTemplate getStringRedisTemplate(RedisConnectionFactory connectionFactory){
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(connectionFactory);
        setStringSerializer(template);
        template.afterPropertiesSet();
        return template;
    }

    private void setStringSerializer(StringRedisTemplate template){
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        template.setKeySerializer(stringSerializer);
        template.setValueSerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer );
        template.setHashValueSerializer(stringSerializer );
        template.afterPropertiesSet();
    }

    private void setSerializer(RedisTemplate<Object, Object> template) {
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setKeySerializer(jackson2JsonRedisSerializer);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.setHashValueSerializer(jackson2JsonRedisSerializer);
    }

}

