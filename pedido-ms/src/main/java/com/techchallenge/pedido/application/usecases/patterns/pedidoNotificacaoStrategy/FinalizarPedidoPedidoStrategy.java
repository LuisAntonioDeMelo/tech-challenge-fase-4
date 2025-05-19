package com.techchallenge.pedido.application.usecases.patterns.pedidoNotificacaoStrategy;


import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.domain.Pedido;

public class FinalizarPedidoPedidoStrategy implements NotificacaoPedidoStrategy {
    @Override
    public String notificar(Pedido pedido) {
        return "Finalizar Pedido";
    }
}
