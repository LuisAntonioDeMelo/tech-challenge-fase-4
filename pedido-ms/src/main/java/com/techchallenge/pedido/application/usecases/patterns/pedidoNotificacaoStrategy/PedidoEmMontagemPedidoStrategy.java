package com.techchallenge.pedido.application.usecases.patterns.pedidoNotificacaoStrategy;


import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.domain.Pedido;

public class PedidoEmMontagemPedidoStrategy implements NotificacaoPedidoStrategy {
    @Override
    public String notificar(Pedido pedido) {
        return "Pedido em está sendo montado";
    }
}
