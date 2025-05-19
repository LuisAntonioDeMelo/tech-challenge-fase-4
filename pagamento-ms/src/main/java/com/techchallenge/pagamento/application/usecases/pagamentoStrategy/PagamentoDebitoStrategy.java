package com.techchallenge.pagamento.application.usecases.pagamentoStrategy;

import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;
import org.springframework.stereotype.Component;


@Component("debito")
public class PagamentoDebitoStrategy implements PagamentoStrategy {
    @Override
    public String processarPagamento(PagamentoPedidoDTO pedido) {
        return "debito";
    }
}
