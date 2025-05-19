//package com.techchallenge.gateway.config;
//
//import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
//import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
//import io.github.resilience4j.timelimiter.TimeLimiterConfig;
//import io.micrometer.core.instrument.MeterRegistry;
//import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
//import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
//import org.springframework.cloud.client.circuitbreaker.Customizer;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//
///**
// * Configuration for circuit breakers in the API Gateway
// * Defines circuit breaker properties for each microservice to handle failures gracefully
// */
//@Configuration
//public class CircuitBreakerConfig {
//
//    /**
//     * Configures default circuit breaker properties for all services
//     * @return Customizer for ReactiveResilience4JCircuitBreakerFactory
//     */
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
//        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
//                .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                        .slidingWindowSize(10)
//                        .failureRateThreshold(50)
//                        .waitDurationInOpenState(Duration.ofSeconds(10))
//                        .permittedNumberOfCallsInHalfOpenState(5)
//                        .slowCallRateThreshold(50)
//                        .slowCallDurationThreshold(Duration.ofSeconds(2))
//                        .build())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(3))
//                        .build())
//                .build());
//    }
//
//    /**
//     * Configures circuit breaker properties for the Order service (pedido-ms)
//     * @return Customizer for ReactiveResilience4JCircuitBreakerFactory
//     */
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> pedidoCustomizer() {
//        return factory -> factory.configure(builder -> builder
//                .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                        .slidingWindowSize(10)
//                        .failureRateThreshold(40)  // More sensitive threshold
//                        .waitDurationInOpenState(Duration.ofSeconds(15))
//                        .permittedNumberOfCallsInHalfOpenState(3)
//                        .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                        .build())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(4))  // Longer timeout
//                        .build()), "pedidoCircuitBreaker");
//    }
//
//    /**
//     * Configures circuit breaker properties for the Payment service (pagamento-ms)
//     * @return Customizer for ReactiveResilience4JCircuitBreakerFactory
//     */
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> pagamentoCustomizer() {
//        return factory -> factory.configure(builder -> builder
//                .circuitBreakerConfig(CircuitBreakerConfig.custom()
//                        .slidingWindowSize(10)
//                        .failureRateThreshold(30)  // Even more sensitive - payment is critical
//                        .waitDurationInOpenState(Duration.ofSeconds(20))
//                        .permittedNumberOfCallsInHalfOpenState(3)
//                        .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                        .build())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(5))  // Longer timeout for payments
//                        .build()), "pagamentoCircuitBreaker");
//    }
//
//    /**
//     * Configures circuit breaker properties for the Production service (producao-ms)
//     * @return Customizer for ReactiveResilience4JCircuitBreakerFactory
//     */
//    @Bean
//    public Customizer<ReactiveResilience4JCircuitBreakerFactory> producaoCustomizer() {
//        return factory -> factory.configure(builder -> builder
//                .circuitBreakerConfig(CircuitBreakerConfig
//                        .slidingWindowSize(10)
//                        .failureRateThreshold(45)
//                        .waitDurationInOpenState(Duration.ofSeconds(15))
//                        .permittedNumberOfCallsInHalfOpenState(4)
//                        .automaticTransitionFromOpenToHalfOpenEnabled(true)
//                        .build())
//                .timeLimiterConfig(TimeLimiterConfig.custom()
//                        .timeoutDuration(Duration.ofSeconds(4))
//                        .build()), "producaoCircuitBreaker");
//    }
//
//    /**
//     * Configures metrics for circuit breakers
//     * @param meterRegistry the meter registry for metrics
//     * @return CircuitBreakerRegistry with metrics
//     */
//    @Bean
//    public CircuitBreakerRegistry circuitBreakerRegistry(MeterRegistry meterRegistry) {
//        CircuitBreakerRegistry circuitBreakerRegistry = CircuitBreakerRegistry.ofDefaults();
//        circuitBreakerRegistry.getEventPublisher()
//                .onEntryAdded(event -> meterRegistry.gauge(
//                        "circuit_breaker.state",
//                        event.getAddedEntry(),
//                        cb -> cb.getState().getOrder()
//                ));
//
//        return circuitBreakerRegistry;
//    }
//
//
//}
//
