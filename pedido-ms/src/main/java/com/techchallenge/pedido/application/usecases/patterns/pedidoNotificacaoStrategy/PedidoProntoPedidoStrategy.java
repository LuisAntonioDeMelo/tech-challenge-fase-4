package com.techchallenge.pedido.application.usecases.patterns.pedidoNotificacaoStrategy;


import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.domain.Pedido;

public class PedidoProntoPedidoStrategy implements NotificacaoPedidoStrategy {
    @Override
    public String notificar(Pedido pedido) {
        return "Pedido está pronto para ser retirado!";
    }
}
