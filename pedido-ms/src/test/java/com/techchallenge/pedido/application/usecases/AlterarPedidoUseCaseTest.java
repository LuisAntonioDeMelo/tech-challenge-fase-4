package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.domain.port.PedidoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AlterarPedidoUseCaseTest {

    @Mock
    private PedidoPort pedidoPort;

    private AlterarPedidoUseCase alterarPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        alterarPedidoUseCase = new AlterarPedidoUseCase(pedidoPort);
    }

    @Test
    void deveAlterarSituacaoPedido() {
        // Arrange
        Long pedidoId = 1L;
        String novaSituacao = "EM_PREPARACAO";
        
        Pedido pedidoAlterado = new Pedido();
        pedidoAlterado.setIdPedido(pedidoId);
        pedidoAlterado.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        when(pedidoPort.alterarSituacaoPedido(eq(pedidoId), eq(novaSituacao))).thenReturn(pedidoAlterado);
        
        // Act
        Pedido resultado = alterarPedidoUseCase.alterarSituacaoPedido(pedidoId, novaSituacao);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(SituacaoPedido.EM_PREPARACAO, resultado.getSituacaoPedido());
        verify(pedidoPort).alterarSituacaoPedido(pedidoId, novaSituacao);
        verify(pedidoPort, never()).alterarPedido(any());
    }

    @Test
    void deveFinalizarPedido() {
        // Arrange
        Long pedidoId = 1L;
        String novaSituacao = "FINALIZADO";
        
        Pedido pedidoAlterado = new Pedido();
        pedidoAlterado.setIdPedido(pedidoId);
        pedidoAlterado.setSituacaoPedido(SituacaoPedido.FINALIZADO);
        
        when(pedidoPort.alterarSituacaoPedido(eq(pedidoId), eq(novaSituacao))).thenReturn(pedidoAlterado);
        when(pedidoPort.alterarPedido(any(Pedido.class))).thenReturn(pedidoAlterado);
        
        // Act
        Pedido resultado = alterarPedidoUseCase.alterarSituacaoPedido(pedidoId, novaSituacao);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(SituacaoPedido.FINALIZADO, resultado.getSituacaoPedido());
        assertNotNull(resultado.getHorarioFinalizacao());
        verify(pedidoPort).alterarSituacaoPedido(pedidoId, novaSituacao);
        verify(pedidoPort).alterarPedido(any(Pedido.class));
    }
}
