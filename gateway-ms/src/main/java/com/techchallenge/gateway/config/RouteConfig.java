package com.techchallenge.gateway.config;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * Configuration for API Gateway routes
 * Defines the routing rules for forwarding requests to the appropriate microservices
 */
@Configuration
public class RouteConfig {

    /**
     * Defines routes for the gateway
     */
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("pedido-service", r -> r
                        .path("/api/pedidos/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("pedidoCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/pedido"))
                               //.rewritePath("/api/pedidos/(?<segment>.*)", "/api/orders/${segment}")
                                .addRequestHeader("X-Gateway-Request", "true"))
                        .uri("lb://pedido-ms"))

                .route("pagamento-service", r -> r
                        .path("/api/payments/**", "/api/pagamentos/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("pagamentoCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/pagamento"))
                               // .rewritePath("/api/pagamentos/(?<segment>.*)", "/api/payments/${segment}")
                                .addRequestHeader("X-Gateway-Request", "true"))
                        .uri("lb://pagamento-ms"))
                

                .route("cliente-service", r -> r
                        .path("/api/clientes/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("clienteCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/clientes"))
                                .addRequestHeader("X-Gateway-Request", "true"))
                        .uri("lb://cliente-ms"))

                .route("produto-service", r -> r
                        .path("/api/produtos/**")
                        .filters(f -> f
                                .circuitBreaker(config -> config
                                        .setName("produtosCircuitBreaker")
                                        .setFallbackUri("forward:/fallback/clientes"))
                                .addRequestHeader("X-Gateway-Request", "true"))
                        .uri("lb://produto-ms"))


                .route("eureka-admin", r -> r
                        .path("/eureka/**")
                        .filters(f -> f.setPath("/"))
                        .uri("lb://eureka-server"))
                .build();
    }

    /**
     * Circuit breaker configuration using Resilience4j
     */
    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.custom()
                        .slidingWindowSize(10)
                        .failureRateThreshold(50)
                        .waitDurationInOpenState(Duration.ofSeconds(10))
                        .permittedNumberOfCallsInHalfOpenState(5)
                        .slowCallRateThreshold(50)
                        .slowCallDurationThreshold(Duration.ofSeconds(2))
                        .build())
                .timeLimiterConfig(TimeLimiterConfig.custom()
                        .timeoutDuration(Duration.ofSeconds(3))
                        .build())
                .build());
    }
}

