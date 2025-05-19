package com.techchallenge.pagamento.application.gateway;


import com.techchallenge.pagamento.domain.Pagamento;

public interface PagamentoGateway {
    Pagamento consultarStatusPagamento(Long idPedido);
    Pagamento aprovarPagamento(Long id, String aprovado);

    Pagamento criarPagamentoPedido(Long id, String tipoPagamento);
}
