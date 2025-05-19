package com.techchallenge.gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

@Configuration
public class RateLimitConfig {


    @Bean
    @Primary
    public RedisRateLimiter defaultRateLimiter() {
        return new RedisRateLimiter(20, 40);
    }

    @Bean
    public KeyResolver ipKeyResolver() {
        return exchange -> Mono.just(
                exchange.getRequest().getRemoteAddress() != null 
                        ? exchange.getRequest().getRemoteAddress().getHostString()
                        : "unknown-ip");
    }

//    @Bean
//    public KeyResolver pathKeyResolver() {
//        return exchange -> Mono.just(exchange.getRequest().getPath().value());
//    }

    @Bean
    public RedisRateLimiter orderServiceRateLimiter() {
        return new RedisRateLimiter(10, 20);
    }

    @Bean
    public RedisRateLimiter paymentServiceRateLimiter() {
        return new RedisRateLimiter(5, 10);
    }

}

