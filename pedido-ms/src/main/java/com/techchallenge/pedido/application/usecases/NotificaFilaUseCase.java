package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;

public class NotificaFilaUseCase {

    public String notificaFila(Pedido pedido) {
        SituacaoPedido situacao = pedido.getSituacaoPedido();
        NotificacaoPedidoStrategy strategy = situacao.getStrategy();
        return strategy.notificar(pedido);
    }
}
