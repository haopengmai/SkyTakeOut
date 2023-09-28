package com.sky.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//该类是配置类，用于配置Redis的对象
@Configuration
@Slf4j
public class RedisConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        log.info("RedisConfiguration init...");
        RedisTemplate redisTemplate = new RedisTemplate();
        //设置redis连接工厂的对象
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        //设置redis的序列化器  ,主要为了干这件事情
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}
