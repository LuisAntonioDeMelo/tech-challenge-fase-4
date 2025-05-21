package com.techchallenge.pagamento.infrastructure.gateways;

import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.domain.StatusPagamento;
import com.techchallenge.pagamento.domain.TipoPagamento;
import com.techchallenge.pagamento.infrastructure.persistence.PagamentoEntity;
import com.techchallenge.pagamento.infrastructure.persistence.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PagamentoRepositoryGatewayTest {

    @Mock
    private PagamentoRepository pagamentoRepository;

    @Mock
    private ModelMapper modelMapper;

    private PagamentoRepositoryGateway pagamentoRepositoryGateway;

    @BeforeEach
    void setUp() {
        pagamentoRepositoryGateway = new PagamentoRepositoryGateway(pagamentoRepository, modelMapper);
    }

    @Test
    @DisplayName("Dado um ID de pedido válido, quando consultar o status do pagamento, então deve retornar o pagamento correspondente")
    void givenValidPedidoId_whenConsultarStatusPagamento_thenReturnPagamento() {
        // Given
        Long idPedido = 1L;
        
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(idPedido);
        pagamentoEntity.setValor(new BigDecimal("100.00"));
        pagamentoEntity.setDescricao("Pagamento do pedido 1");
        pagamentoEntity.setTipoPagamento(TipoPagamento.PIX);
        pagamentoEntity.setStatusPagamento(StatusPagamento.PENDENTE);
        pagamentoEntity.setCodigoPedido(UUID.randomUUID());
        pagamentoEntity.setExpiracao(LocalDateTime.now().plusHours(1));
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(idPedido);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setDescricao("Pagamento do pedido 1");
        pagamento.setTipoPagamento(TipoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
        
        when(pagamentoRepository.obterPagamentoPorIdPedido(idPedido)).thenReturn(pagamentoEntity);
        when(modelMapper.map(pagamentoEntity, Pagamento.class)).thenReturn(pagamento);
        
        // When
        Pagamento result = pagamentoRepositoryGateway.consultarStatusPagamento(idPedido);
        
        // Then
        assertNotNull(result);
        assertEquals(idPedido, result.getId());
        assertEquals(new BigDecimal("100.00"), result.getValor());
        assertEquals("Pagamento do pedido 1", result.getDescricao());
        assertEquals(TipoPagamento.PIX, result.getTipoPagamento());
        assertEquals(StatusPagamento.PENDENTE, result.getStatusPagamento());
        
        verify(pagamentoRepository, times(1)).obterPagamentoPorIdPedido(idPedido);
        verify(modelMapper, times(1)).map(pagamentoEntity, Pagamento.class);
    }

    @Test
    @DisplayName("Dado um ID de pagamento válido, quando aprovar o pagamento, então deve atualizar o status para PAGO")
    void givenValidPaymentId_whenAprovarPagamento_thenUpdateStatusToPago() {
        // Given
        Long idPagamento = 1L;
        String aprovado = "APROVADO";
        
        PagamentoEntity pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.setId(idPagamento);
        pagamentoEntity.setValor(new BigDecimal("100.00"));
        pagamentoEntity.setDescricao("Pagamento do pedido 1");
        pagamentoEntity.setTipoPagamento(TipoPagamento.PIX);
        pagamentoEntity.setStatusPagamento(StatusPagamento.PENDENTE);
        
        PagamentoEntity pagamentoSalvo = new PagamentoEntity();
        pagamentoSalvo.setId(idPagamento);
        pagamentoSalvo.setValor(new BigDecimal("100.00"));
        pagamentoSalvo.setDescricao("Pagamento do pedido 1");
        pagamentoSalvo.setTipoPagamento(TipoPagamento.PIX);
        pagamentoSalvo.setStatusPagamento(StatusPagamento.PAGO);
        
        Pagamento pagamento = new Pagamento();
        pagamento.setId(idPagamento);
        pagamento.setValor(new BigDecimal("100.00"));
        pagamento.setDescricao("Pagamento do pedido 1");
        pagamento.setTipoPagamento(TipoPagamento.PIX);
        pagamento.setStatusPagamento(StatusPagamento.PAGO);
        
        when(pagamentoRepository.obterPagamentoPorIdPedido(idPagamento)).thenReturn(pagamentoEntity);
        when(pagamentoRepository.save(pagamentoEntity)).thenReturn(pagamentoSalvo);
        when(modelMapper.map(pagamentoSalvo, Pagamento.class)).thenReturn(pagamento);
        
        // When
        Pagamento result = pagamentoRepositoryGateway.aprovarPagamento(idPagamento, aprovado);
        
        // Then
        assertNotNull(result);
        assertEquals(idPagamento, result.getId());
        assertEquals(StatusPagamento.PAGO, result.getStatusPagamento());
        
        verify(pagamentoRepository, times(1)).obterPagamentoPorIdPedido(idPagamento);
        verify(pagamentoRepository, times(2)).save(pagamentoEntity);
        verify(modelMapper, times(1)).map(pagamentoSalvo, Pagamento.class);
    }
}
