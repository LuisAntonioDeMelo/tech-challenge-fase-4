package com.techchallenge.pedido.infrastructure.controllers;

import com.techchallenge.pedido.application.usecases.FilaPedidoUseCase;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoFilaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FilaPedidosControllerTest {

    @Mock
    private FilaPedidoUseCase filaPedidoUseCase;

    private FilaPedidosController filaPedidosController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filaPedidosController = new FilaPedidosController(filaPedidoUseCase);
    }

    @Test
    void deveObterFilaDePedidos() {
        // Arrange
        PedidoFilaDTO pedidoFila1 = PedidoFilaDTO.builder()
                .codigoPedido("P001")
                .statusPedido("Pedido está em preparação")
                .horarioIniciou("2023-09-01T10:00:00")
                .build();
        
        PedidoFilaDTO pedidoFila2 = PedidoFilaDTO.builder()
                .codigoPedido("P002")
                .statusPedido("Pedido recebido!")
                .horarioIniciou("2023-09-01T10:15:00")
                .build();
        
        List<PedidoFilaDTO> filaEsperada = Arrays.asList(pedidoFila1, pedidoFila2);
        
        when(filaPedidoUseCase.listarPedidosNaFila()).thenReturn(filaEsperada);
        
        // Act
        ResponseEntity<List<PedidoFilaDTO>> resposta = filaPedidosController.obterFilaDePedidos();
        
        // Assert
        assertNotNull(resposta);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(filaEsperada, resposta.getBody());
        verify(filaPedidoUseCase).listarPedidosNaFila();
    }

    @Test
    void deveObterFilaDePedidosRetirada() {
        // Arrange
        PedidoFilaDTO pedidoFila = PedidoFilaDTO.builder()
                .codigoPedido("P001")
                .statusPedido("Pedido está pronto para ser retirado!")
                .horarioIniciou("2023-09-01T10:00:00")
                .build();
        
        List<PedidoFilaDTO> filaEsperada = List.of(pedidoFila);
        
        when(filaPedidoUseCase.listarPedidosProntos()).thenReturn(filaEsperada);
        
        // Act
        ResponseEntity<List<PedidoFilaDTO>> resposta = filaPedidosController.obterFilaDePedidosRetirada();
        
        // Assert
        assertNotNull(resposta);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(filaEsperada, resposta.getBody());
        verify(filaPedidoUseCase).listarPedidosProntos();
    }
}
