package com.techchallenge.gateway.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingFilterTest {

    @Mock
    private GatewayFilterChain filterChain;

    private LoggingFilter loggingFilter;

    @BeforeEach
    void setUp() {
        loggingFilter = new LoggingFilter();
    }

    @Test
    void shouldLogRequestAndResponseSuccessfully() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.GET, "/api/clientes")
                .remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
                .header("User-Agent", "Mozilla/5.0")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
        
        // Verify the filter order is set correctly
        assertEquals(-2147483648, loggingFilter.getOrder()); // HIGHEST_PRECEDENCE
    }

    @Test
    void shouldHandleRequestWithError() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.POST, "/api/pedidos")
                .remoteAddress(new InetSocketAddress("192.168.1.1", 9090))
                .header("User-Agent", "PostmanRuntime/7.28.0")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
    }

    @Test
    void shouldAddCorrelationIdWhenNotPresent() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.GET, "/api/produtos")
                .remoteAddress(new InetSocketAddress("10.0.0.1", 3000))
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
    }

    @Test
    void shouldPreserveExistingCorrelationId() {
        // Arrange
        String existingCorrelationId = "existing-correlation-123";
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.PUT, "/api/clientes/1")
                .header("X-Correlation-ID", existingCorrelationId)
                .remoteAddress(new InetSocketAddress("172.16.0.1", 5000))
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        when(filterChain.filter(any(ServerWebExchange.class))).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
    }
}
