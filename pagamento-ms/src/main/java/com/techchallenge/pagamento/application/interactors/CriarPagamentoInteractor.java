package com.techchallenge.pagamento.application.interactors;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.application.usecases.CriarPagamentoUseCase;
import com.techchallenge.pagamento.domain.Pagamento;

public class CriarPagamentoInteractor implements CriarPagamentoUseCase {

    private PagamentoGateway pedidoGateway;

    public CriarPagamentoInteractor(PagamentoGateway pedidoGateway) {
        this.pedidoGateway = pedidoGateway;
    }

    @Override
    public Pagamento criarPagamentoPedido(Long id, String tipoPagamento) {
        return pedidoGateway.criarPagamentoPedido(id, tipoPagamento);
    }
}
