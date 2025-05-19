package com.techchallenge.gateway.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

import java.util.concurrent.TimeUnit;

/**
 * Configuration for gateway metrics
 * Configures and provides custom metrics for monitoring the gateway performance
 */
@Configuration
public class MetricsConfig {
    

    @Bean
    public WebFilter requestTimingMetricsFilter(MeterRegistry registry) {
        return (exchange, chain) -> {
            long start = System.currentTimeMillis();
            
            return chain.filter(exchange)
                    .doFinally(signalType -> {
                        long duration = System.currentTimeMillis() - start;
                        
                        String path = exchange.getRequest().getPath().value();
                        String method = exchange.getRequest().getMethod().name();
                        
                        // Record basic timing metric
                        Timer.builder("gateway.request.duration")
                                .description("Request duration through the gateway")
                                .tag("path", path)
                                .tag("method", method)
                                .tag("status", exchange.getResponse().getStatusCode() != null ? 
                                        exchange.getResponse().getStatusCode().toString() : "unknown")
                                .register(registry)
                                .record(duration, TimeUnit.MILLISECONDS);
                        
                        // Extract service name from path for service-specific metrics
                        String service = extractServiceFromPath(path);
                        
                        // Record service-specific timing metric
                        Timer.builder("gateway.service.request.duration")
                                .description("Request duration by service")
                                .tag("service", service)
                                .tag("method", method)
                                .tag("status", exchange.getResponse().getStatusCode() != null ? 
                                        exchange.getResponse().getStatusCode().toString() : "unknown")
                                .register(registry)
                                .record(duration, TimeUnit.MILLISECONDS);
                    });
        };
    }

    private String extractServiceFromPath(String path) {
        if (path.startsWith("/api/")) {
            String[] parts = path.split("/");
            if (parts.length > 2) {
                return parts[2]; // Extract service name from path
            }
        }
        return "unknown";
    }
}

