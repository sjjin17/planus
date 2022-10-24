package com.planus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
public class RedisConfig {

//    @Value("${spring.redis.host}")    public String host;
//
//    @Value("${spring.redis.port}")    public int port;
//
//    @Bean    public RedisConnectionFactory connectionFactory() {
//        return new LettuceConnectionFactory(host, port);
//    }

}