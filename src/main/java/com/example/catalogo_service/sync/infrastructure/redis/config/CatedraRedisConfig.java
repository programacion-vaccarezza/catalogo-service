package com.example.catalogo_service.sync.infrastructure.redis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;

@Configuration
public class CatedraRedisConfig {

    @Bean
    public ReactiveRedisConnectionFactory catedraRedisConnectionFactory(
            @Value("${catedra.redis.host}") String host,
            @Value("${catedra.redis.port}") int port,
            @Value("${catedra.redis.username}") String username,
            @Value("${catedra.redis.password}") String password) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(host, port);
        configuration.setUsername(username);
        configuration.setPassword(password);
        return new LettuceConnectionFactory(configuration);
    }

    @Bean
    public ReactiveStringRedisTemplate catedraRedisTemplate(ReactiveRedisConnectionFactory catedraRedisConnectionFactory) {
        return new ReactiveStringRedisTemplate(catedraRedisConnectionFactory);
    }
}
