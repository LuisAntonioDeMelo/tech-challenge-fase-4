package com.techchallenge.pagamento.application.usecases.pagamentoStrategy;


import com.techchallenge.pagamento.infrastructure.presenters.PagamentoPedidoDTO;
import org.springframework.stereotype.Component;


@Component("credito")
public class PagamentoCreditoStategy implements PagamentoStrategy {

    @Override
    public String processarPagamento(PagamentoPedidoDTO pedido) {
        return "credito";
    }


}
