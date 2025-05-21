package com.techchallenge.gateway.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class MetricsConfigTest {

    private MetricsConfig metricsConfig;
    private MeterRegistry meterRegistry;
    private WebFilterChain filterChain;
    private MockServerWebExchange exchange;

    @BeforeEach
    void setUp() {
        metricsConfig = new MetricsConfig();
        meterRegistry = new SimpleMeterRegistry();
        filterChain = mock(WebFilterChain.class);
        
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.GET, "/api/service/resource")
                .build();
        exchange = MockServerWebExchange.from(request);
        
        when(filterChain.filter(exchange)).thenReturn(Mono.empty());
    }

    @Test
    void requestTimingMetricsFilter_shouldMeasureRequestDuration() {
        // Arrange
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        // Act
        var filter = metricsConfig.requestTimingMetricsFilter(meterRegistry);
        filter.filter(exchange, filterChain).block();
        
        // Assert
        verify(filterChain, times(1)).filter(exchange);
        assertNotNull(meterRegistry.find("gateway.request.duration").timer());
        assertNotNull(meterRegistry.find("gateway.service.request.duration").timer());
    }
    
    @Test
    void extractServiceFromPath_shouldReturnServiceNameFromPath() {
        // Arrange
        var request = MockServerHttpRequest
                .method(HttpMethod.GET, "/api/product/items")
                .build();
        var exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        // Act
        var filter = metricsConfig.requestTimingMetricsFilter(meterRegistry);
        filter.filter(exchange, filterChain).block();
        
        // Assert
        verify(filterChain, times(1)).filter(exchange);
        assertNotNull(meterRegistry.find("gateway.service.request.duration")
                .tag("service", "product")
                .timer());
    }
}
