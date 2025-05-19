package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NotificaFilaUseCaseTest {

    private NotificaFilaUseCase notificaFilaUseCase;

    @BeforeEach
    void setUp() {
        notificaFilaUseCase = new NotificaFilaUseCase();
    }

    @Test
    void deveNotificarPedidoCriado() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setSituacaoPedido(SituacaoPedido.CRIADO);
        
        // Act
        String resultado = notificaFilaUseCase.notificaFila(pedido);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Pedido Criado", resultado);
    }

    @Test
    void deveNotificarPedidoEmPreparacao() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        // Act
        String resultado = notificaFilaUseCase.notificaFila(pedido);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Pedido está em preparação", resultado);
    }

    @Test
    void deveNotificarPedidoPronto() {
        // Arrange
        Pedido pedido = new Pedido();
        pedido.setIdPedido(1L);
        pedido.setSituacaoPedido(SituacaoPedido.PRONTO);
        
        // Act
        String resultado = notificaFilaUseCase.notificaFila(pedido);
        
        // Assert
        assertNotNull(resultado);
        assertEquals("Pedido está pronto para ser retirado!", resultado);
    }
}
