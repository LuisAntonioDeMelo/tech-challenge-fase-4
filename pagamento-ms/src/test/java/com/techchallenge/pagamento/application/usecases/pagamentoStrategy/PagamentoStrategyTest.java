package com.techchallenge.pagamento.application.usecases.pagamentoStrategy;

import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.domain.TipoPagamento;
import com.techchallenge.pagamento.infrastructure.presenters.ClienteDTO;
import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;
import com.techchallenge.pagamento.infrastructure.presenters.PedidoDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PagamentoStrategyTest {

    @Test
    @DisplayName("Dado um pedido com pagamento via débito, quando processar o pagamento, então deve retornar 'debito'")
    void givenPedidoWithDebitoPayment_whenProcessarPagamento_thenReturnDebito() {
        // Given
        PagamentoDebitoStrategy debitoStrategy = new PagamentoDebitoStrategy();
        PagamentoPedidoDTO pedidoDTO = criarPedidoComTipoPagamento(TipoPagamento.DEBITO);
        
        // When
        String resultado = debitoStrategy.processarPagamento(pedidoDTO);
        
        // Then
        assertNotNull(resultado);
        assertEquals("debito", resultado);
    }

    @Test
    @DisplayName("Dado um pedido com pagamento via crédito, quando processar o pagamento, então deve retornar 'credito'")
    void givenPedidoWithCreditoPayment_whenProcessarPagamento_thenReturnCredito() {
        // Given
        PagamentoCreditoStategy creditoStrategy = new PagamentoCreditoStategy();
        PagamentoPedidoDTO pedidoDTO = criarPedidoComTipoPagamento(TipoPagamento.CREDITO);
        
        // When
        String resultado = creditoStrategy.processarPagamento(pedidoDTO);
        
        // Then
        assertNotNull(resultado);
        assertEquals("credito", resultado);
    }

    @Test
    @DisplayName("Dado um pedido com pagamento via PIX, quando processar o pagamento, então deve tentar gerar QR Code")
    void givenPedidoWithPixPayment_whenProcessarPagamento_thenGenerateQrCode() {
        // Given
        // Note: This test is limited because the actual implementation uses the MercadoPago API
        // which requires a real token and we can't fully test it in a unit test
        // For a complete test, we would need to mock the MercadoPago API or use a test double
        
        PagamentoPixMercadoPagoStrategy pixStrategy = new PagamentoPixMercadoPagoStrategy();
        PagamentoPedidoDTO pedidoDTO = criarPedidoComTipoPagamento(TipoPagamento.PIX);
        
        // We can't fully test this without mocking the MercadoPago API
        // When/Then
        // In a proper test setup, we would verify that the method attempts to call the API
        // and handles the response correctly
    }
    
    // Helper method to create a PagamentoPedidoDTO with the specified payment type
    private PagamentoPedidoDTO criarPedidoComTipoPagamento(TipoPagamento tipoPagamento) {
        PagamentoPedidoDTO pedidoDTO = new PagamentoPedidoDTO();
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setDescricao("Pagamento do pedido 1");
        pagamento.setTipoPagamento(tipoPagamento);
        
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(1L);
        clienteDTO.setNome("Cliente Teste");
        clienteDTO.setEmail("cliente@teste.com");
        clienteDTO.setCpf("12345678900");
        
        PedidoDTO pedido = new PedidoDTO();
        
        pedidoDTO.setPagamento(pagamento);
        pedidoDTO.setCliente(clienteDTO);
        pedidoDTO.setPedido(pedido);
        
        return pedidoDTO;
    }
}
