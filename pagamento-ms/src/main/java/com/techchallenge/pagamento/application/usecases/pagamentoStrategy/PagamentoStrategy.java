package com.techchallenge.pagamento.application.usecases.pagamentoStrategy;


import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;


public interface PagamentoStrategy {
    public String processarPagamento(PagamentoPedidoDTO pedido);
}
