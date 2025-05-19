package com.techchallenge.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * Global filter to log request and response details
 * Adds correlation IDs to facilitate request tracking across microservices
 */
@Component
public class LoggingFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);
    private static final String CORRELATION_ID_HEADER = "X-Correlation-ID";
    private static final String START_TIME = "startTime";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // Record start time
        exchange.getAttributes().put(START_TIME, System.currentTimeMillis());
        
        // Add correlation ID if not present
        ServerHttpRequest request = exchange.getRequest();
        String correlationId = request.getHeaders().getFirst(CORRELATION_ID_HEADER);
        if (correlationId == null) {
            correlationId = UUID.randomUUID().toString();
            request = exchange.getRequest().mutate()
                    .header(CORRELATION_ID_HEADER, correlationId)
                    .build();
            exchange = exchange.mutate().request(request).build();
        }
        
        // Log the request
        log.info("Request: {} {} [{}], Client IP: {}, User-Agent: {}",
                request.getMethod(), 
                request.getPath(),
                correlationId,
                request.getRemoteAddress() != null ? request.getRemoteAddress().getHostString() : "unknown",
                request.getHeaders().getFirst("User-Agent"));
        
        // Continue the filter chain
        ServerWebExchange finalExchange = exchange;
        String finalCorrelationId = correlationId;
        ServerHttpRequest finalRequest = request;
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            // Log the response
            Long startTime = finalExchange.getAttribute(START_TIME);
            long executionTime = System.currentTimeMillis() - startTime;
            
            log.info("Response: {} {} [{}], Status: {}, Time: {}ms",
                    finalRequest.getMethod(),
                    finalRequest.getPath(),
                    finalCorrelationId,
                    finalExchange.getResponse().getStatusCode(),
                    executionTime);
        }));
    }

    @Override
    public int getOrder() {
        // Set the highest precedence
        return Ordered.HIGHEST_PRECEDENCE;
    }
}

