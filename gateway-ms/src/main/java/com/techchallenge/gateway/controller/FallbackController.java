package com.techchallenge.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller to handle fallback responses when a service is unavailable
 * Used by the circuit breaker
 */
@RestController
@RequestMapping("/fallback")
@Slf4j
public class FallbackController {

    /**
     * Generic fallback method for all services
     * @param service the service name that failed
     * @return ResponseEntity with appropriate error details
     */
    @GetMapping("/{service}")
    public ResponseEntity<Map<String, Object>> serviceFallback(@PathVariable String service) {
        log.warn("Fallback triggered for service: {}", service);
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Service Unavailable");
        response.put("message", String.format("The %s service is currently unavailable. Please try again later.", service));
        response.put("service", service);
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
    
    /**
     * Specific fallback for order service
     * @return ResponseEntity with order service specific error details
     */
    @GetMapping("/pedido")
    public ResponseEntity<Map<String, Object>> orderFallback() {
        log.warn("Fallback triggered for Order Service");
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Order Service Unavailable");
        response.put("message", "The Order service is currently unavailable. Please try again later.");
        response.put("service", "Order Service");
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
    
    /**
     * Specific fallback for payment service
     * @return ResponseEntity with payment service specific error details
     */
    @GetMapping("/pagamento")
    public ResponseEntity<Map<String, Object>> paymentFallback() {
        log.warn("Fallback triggered for Payment Service");
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Payment Service Unavailable");
        response.put("message", "The Payment service is currently unavailable. Please try again later.");
        response.put("service", "Payment Service");
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
    
    /**
     * Specific fallback for production service
     * @return ResponseEntity with production service specific error details
     */
    @GetMapping("/producao")
    public ResponseEntity<Map<String, Object>> productionFallback() {
        log.warn("Fallback triggered for Production Service");
        
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("status", HttpStatus.SERVICE_UNAVAILABLE.value());
        response.put("error", "Production Service Unavailable");
        response.put("message", "The Production service is currently unavailable. Please try again later.");
        response.put("service", "Production Service");
        
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}

