package com.techchallenge.pagamento.application.usecases;


import com.techchallenge.pagamento.domain.Pagamento;

public interface CriarPagamentoUseCase {

    Pagamento criarPagamentoPedido(Long id, String tipoPagamento);
}
