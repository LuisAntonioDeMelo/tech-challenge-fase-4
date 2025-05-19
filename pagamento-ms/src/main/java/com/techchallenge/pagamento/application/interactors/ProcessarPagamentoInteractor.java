package com.techchallenge.pagamento.application.interactors;


import com.techchallenge.pagamento.application.gateway.PagamentoGateway;
import com.techchallenge.pagamento.application.usecases.ProcessarPagamentoUseCase;
import com.techchallenge.pagamento.application.usecases.pagamentoStrategy.PagamentoStrategy;
import com.techchallenge.pagamento.domain.Pagamento;
import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;

import java.util.Map;

public class ProcessarPagamentoInteractor implements ProcessarPagamentoUseCase {

    private final Map<String, PagamentoStrategy> strategies;
    private final PagamentoGateway pagamentoGateway;

    public ProcessarPagamentoInteractor(Map<String, PagamentoStrategy> strategies, PagamentoGateway pagamentoGateway) {
        this.strategies = strategies;
        this.pagamentoGateway = pagamentoGateway;
    }

    @Override
    public String processarPagamento(Long idPedido) {
        //Pedido pedido = pedidoGateway.obterPedidoPorId(idPedido);
        PagamentoStrategy strategy = verificaPagamentoPedido(null);

        return strategy.processarPagamento(null);
    }

    private PagamentoStrategy verificaPagamentoPedido(PagamentoPedidoDTO pedido) {
        if(pedido.getPagamento() == null) {
            throw new IllegalArgumentException("Pedido não possui pagamento");
        }
        String tipoPagamento = pedido.getPagamento().getTipoPagamento().name();
        PagamentoStrategy strategy = strategies.get(tipoPagamento);
        if (strategy == null) {
            throw new IllegalArgumentException("Tipo de pagamento não suportado: " + tipoPagamento);
        }
        if(pedido.getPagamento().getValor() == null) {
            throw new IllegalArgumentException("Pedido não possui valor de pagamento");
        }

        return strategy;
    }


}
