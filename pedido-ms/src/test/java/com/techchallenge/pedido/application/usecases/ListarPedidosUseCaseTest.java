package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.domain.port.PedidoPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListarPedidosUseCaseTest {

    @Mock
    private PedidoPort pedidoPort;

    private ListarPedidosUseCase listarPedidosUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listarPedidosUseCase = new ListarPedidosUseCase(pedidoPort);
    }

    @Test
    void deveListarTodosPedidos() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1L);
        pedido1.setCodigoPedido("P001");
        pedido1.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2L);
        pedido2.setCodigoPedido("P002");
        pedido2.setSituacaoPedido(SituacaoPedido.PRONTO);
        
        List<Pedido> pedidosEsperados = Arrays.asList(pedido1, pedido2);
        
        when(pedidoPort.listarPedidos()).thenReturn(pedidosEsperados);
        
        // Act
        List<Pedido> resultado = listarPedidosUseCase.listarPedidos();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(pedidosEsperados, resultado);
        verify(pedidoPort).listarPedidos();
    }

    @Test
    void deveListarPedidosPorSituacao() {
        // Arrange
        String situacao = "EM_PREPARACAO";
        
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1L);
        pedido1.setCodigoPedido("P001");
        pedido1.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        List<Pedido> pedidosEsperados = List.of(pedido1);
        
        when(pedidoPort.listarPedidosPorSituacao(situacao)).thenReturn(pedidosEsperados);
        
        // Act
        List<Pedido> resultado = listarPedidosUseCase.listarPedidosPorSituacao(situacao);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(pedidosEsperados, resultado);
        verify(pedidoPort).listarPedidosPorSituacao(situacao);
    }
}
