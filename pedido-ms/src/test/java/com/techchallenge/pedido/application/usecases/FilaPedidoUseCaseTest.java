package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.domain.port.PedidoPort;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoFilaDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class FilaPedidoUseCaseTest {

    @Mock
    private PedidoPort pedidoPort;
    
    @Mock
    private NotificaFilaUseCase notificaFilaUseCase;

    private FilaPedidoUseCase filaPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        filaPedidoUseCase = new FilaPedidoUseCase(pedidoPort, notificaFilaUseCase);
    }

    @Test
    void deveListarPedidosNaFila() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1L);
        pedido1.setCodigoPedido("P001");
        pedido1.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        pedido1.setHorarioInicio(LocalDateTime.now());
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2L);
        pedido2.setCodigoPedido("P002");
        pedido2.setSituacaoPedido(SituacaoPedido.RECEBIDO);
        pedido2.setHorarioInicio(LocalDateTime.now());
        
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
        
        when(pedidoPort.listarPedidos()).thenReturn(pedidos);
        when(notificaFilaUseCase.notificaFila(pedido1)).thenReturn("Pedido está em preparação");
        when(notificaFilaUseCase.notificaFila(pedido2)).thenReturn("Pedido recebido!");
        
        // Act
        List<PedidoFilaDTO> resultado = filaPedidoUseCase.listarPedidosNaFila();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Pedido está em preparação", resultado.get(0).getStatusPedido());
        assertEquals("Pedido recebido!", resultado.get(1).getStatusPedido());
        assertEquals("P001", resultado.get(0).getCodigoPedido());
        assertEquals("P002", resultado.get(1).getCodigoPedido());
    }

    @Test
    void deveListarPedidosProntos() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1L);
        pedido1.setCodigoPedido("P001");
        pedido1.setSituacaoPedido(SituacaoPedido.PRONTO);
        pedido1.setHorarioInicio(LocalDateTime.now());
        
        List<Pedido> pedidos = List.of(pedido1);
        
        when(pedidoPort.listarPedidos()).thenReturn(pedidos);
        when(notificaFilaUseCase.notificaFila(pedido1)).thenReturn("Pedido está pronto para ser retirado!");
        
        // Act
        List<PedidoFilaDTO> resultado = filaPedidoUseCase.listarPedidosProntos();
        
        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedido está pronto para ser retirado!", resultado.get(0).getStatusPedido());
        assertEquals("P001", resultado.get(0).getCodigoPedido());
    }
}
