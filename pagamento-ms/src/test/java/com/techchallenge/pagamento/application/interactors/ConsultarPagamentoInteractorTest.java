package com.techchallenge.pagamento.application.interactors;

import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.domain.StatusPagamento;
import com.techchallenge.pagamento.domain.TipoPagamento;
import com.techchallenge.pagamento.infrastructure.presenters.StatusPagamentoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConsultarPagamentoInteractorTest {

    @Mock
    private PagamentoGateway pagamentoGateway;

    private ConsultarPagamentoInteractor consultarPagamentoInteractor;

    @BeforeEach
    void setUp() {
        consultarPagamentoInteractor = new ConsultarPagamentoInteractor(pagamentoGateway);
    }

    @Test
    @DisplayName("Dado um ID de pedido válido, quando consultar o status do pagamento, então deve retornar o status corretamente")
    void givenValidPedidoId_whenConsultarStatusPagamento_thenReturnStatusPagamento() {
        // Given
        Long idPedido = 1L;
        Pagamento pagamento = new Pagamento();
        pagamento.setId(idPedido);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setDescricao("Pagamento do pedido 1");
        pagamento.setTipoPagamento(TipoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);

        when(pagamentoGateway.consultarStatusPagamento(idPedido)).thenReturn(pagamento);

        // When
        StatusPagamentoDTO result = consultarPagamentoInteractor.consultarStatusPagamento(idPedido);

        // Then
        assertNotNull(result);
        assertEquals(idPedido, result.getId());
        assertEquals(new BigDecimal("100.00"), result.getValor());
        assertEquals("Pagamento do pedido 1", result.getDescricao());
        assertEquals(TipoPagamento.PIX, result.getTipoPagamento());
        assertEquals("PENDENTE", result.getStatus());
        verify(pagamentoGateway, times(1)).consultarStatusPagamento(idPedido);
    }

    @Test
    @DisplayName("Dado um ID de pedido sem pagamento, quando consultar o status do pagamento, então deve lançar uma exceção")
    void givenPedidoIdWithoutPayment_whenConsultarStatusPagamento_thenThrowException() {
        // Given
        Long idPedido = 2L;
        when(pagamentoGateway.consultarStatusPagamento(idPedido)).thenReturn(null);

        // When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultarPagamentoInteractor.consultarStatusPagamento(idPedido);
        });
        
        assertEquals("Pedido não possui pagamento", exception.getMessage());
        verify(pagamentoGateway, times(1)).consultarStatusPagamento(idPedido);
    }
}
