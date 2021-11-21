package com.felixklemke.stocks.components.shared;

import com.felixklemke.stocks.model.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;


@Configuration
@EnableRedisRepositories
public class RedisConfig {


    @Bean
    public RedisTemplate<Long, Stock> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Long, Stock> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
