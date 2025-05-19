package com.techchallenge.pagamento.infrastructure.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @GetMapping("/status")
    public String getStatus() {
        return "Pagamento Microservice is running!";
    }
}

