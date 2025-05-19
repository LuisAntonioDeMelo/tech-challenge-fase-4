package com.techchallenge.pagamento.infrastructure.presenters;

import com.techchallenge.pagamento.domain.Pagamento;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDTO {
    private Long idPedido;
    private String codigoPedido;
   // private Cliente cliente;
    //private List<Produto> produtos;
    private LocalDateTime horarioInicio;
    private LocalDateTime horarioFinalizacao;
    private BigDecimal valorTotalPedido;
   // private SituacaoPedido situacaoPedido;
    private Pagamento pagamento;
}
