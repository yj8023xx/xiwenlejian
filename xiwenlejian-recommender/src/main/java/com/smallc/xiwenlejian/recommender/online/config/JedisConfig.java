package com.smallc.xiwenlejian.recommender.online.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yj8023xx
 * @version 1.0
 * @date 2023/7/19
 * @since com.smallc.xiwenlejian.recommender.online.config
 */
@Configuration
public class JedisConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private int redisPort;

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 配置连接池属性，如最大连接数、最大空闲连接数等
        return new JedisPool(poolConfig, redisHost, redisPort);
    }

}