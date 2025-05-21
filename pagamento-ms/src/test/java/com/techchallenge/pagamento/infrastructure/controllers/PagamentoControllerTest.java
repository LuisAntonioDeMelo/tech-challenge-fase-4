package com.techchallenge.pagamento.infrastructure.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PagamentoController.class)
class PagamentoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Dado que o serviço está em execução, quando chamar o endpoint de status, então deve retornar uma mensagem indicando que está em execução")
    void givenServiceIsRunning_whenCallStatusEndpoint_thenReturnRunningMessage() throws Exception {
        // When/Then
        mockMvc.perform(get("/api/pagamentos/status"))
                .andExpect(status().isOk())
                .andExpect(content().string("Pagamento Microservice is running!"));
    }
}
