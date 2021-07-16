package com.example.demo.redis;

import java.nio.charset.StandardCharsets;

import io.lettuce.core.resource.DefaultClientResources;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.data.redis.connection.PoolConfig;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author lh0
 * @date 2021/7/6
 * @desc
 */
public class RedisClient {

    public static void main(String[] args) {

        GenericObjectPoolConfig<?> poolConfig = new GenericObjectPoolConfig<>();
        poolConfig.setMaxTotal(5);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(3);
        poolConfig.setMaxWaitMillis(1000);
        poolConfig.setTimeBetweenEvictionRunsMillis(60 * 30 * 1000);
        
        // 1. 创建configuration
        LettuceClientConfiguration lettuceClientConfiguration = LettucePoolingClientConfiguration.builder()
            // 1.0 配置连接池
            .poolConfig(poolConfig)
            // 1.1 配置clientResource
            .clientResources(DefaultClientResources.create())
            .build();

        //LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
        //    redisStandaloneConfiguration(), lettuceClientConfiguration);


        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(
            redisSentinelConfiguration(), lettuceClientConfiguration);

        lettuceConnectionFactory.afterPropertiesSet();

        RedisTemplate<String,String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.afterPropertiesSet();

        System.out.println(redisTemplate.opsForValue().get("single"));
        System.out.println("end---------");
    }

    private static RedisSentinelConfiguration redisSentinelConfiguration(){
        RedisSentinelConfiguration redisSentinelConfiguration = new RedisSentinelConfiguration();
        redisSentinelConfiguration
            .sentinel("127.0.0.1", 26379)
            .sentinel("127.0.0.1", 26380)
            .sentinel("127.0.0.1", 26381)
            .setDatabase(2);
        ;

        redisSentinelConfiguration.setMaster("mymaster");

        return redisSentinelConfiguration;
    }


    private static RedisStandaloneConfiguration redisStandaloneConfiguration(){
        RedisStandaloneConfiguration standaloneConfiguration = new RedisStandaloneConfiguration();
        standaloneConfiguration.setHostName("127.0.0.1");
        standaloneConfiguration.setPort(6379);
        standaloneConfiguration.setDatabase(1);
        //standaloneConfiguration.setPassword();
        return standaloneConfiguration;
    }


}
