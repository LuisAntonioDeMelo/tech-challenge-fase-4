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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CheckoutPedidoUseCaseTest {

    @Mock
    private PedidoPort pedidoPort;

    private CheckoutPedidoUseCase checkoutPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        checkoutPedidoUseCase = new CheckoutPedidoUseCase(pedidoPort);
    }

    @Test
    void deveRealizarCheckoutDoPedido() {
        // Arrange
        Long pedidoId = 1L;
        
        Pedido pedidoEsperado = new Pedido();
        pedidoEsperado.setIdPedido(pedidoId);
        pedidoEsperado.setCodigoPedido("P001");
        pedidoEsperado.setSituacaoPedido(SituacaoPedido.CRIADO);
        pedidoEsperado.setValorTotalPedido(BigDecimal.valueOf(49.90));
        
        when(pedidoPort.checkoutPedido(pedidoId)).thenReturn(pedidoEsperado);
        
        // Act
        Pedido resultado = checkoutPedidoUseCase.checkoutPedido(pedidoId);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(pedidoEsperado.getIdPedido(), resultado.getIdPedido());
        assertEquals(pedidoEsperado.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pedidoEsperado.getSituacaoPedido(), resultado.getSituacaoPedido());
        assertEquals(pedidoEsperado.getValorTotalPedido(), resultado.getValorTotalPedido());
        verify(pedidoPort).checkoutPedido(pedidoId);
    }
}
