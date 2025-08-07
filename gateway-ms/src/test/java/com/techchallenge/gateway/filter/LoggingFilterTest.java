package com.techchallenge.gateway.filter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoggingFilterTest {

    @Mock
    private WebFilterChain filterChain;

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
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.OK);
        
        when(filterChain.filter(exchange)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
    }

    @Test
    void shouldHandleRequestWithError() {
        // Arrange
        MockServerHttpRequest request = MockServerHttpRequest
                .method(HttpMethod.POST, "/api/pedidos")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        
        when(filterChain.filter(exchange)).thenReturn(Mono.empty());

        // Act & Assert
        StepVerifier.create(loggingFilter.filter(exchange, filterChain))
                .verifyComplete();
    }
}
