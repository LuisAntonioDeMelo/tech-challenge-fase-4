package com.techchallenge.pedido.application.usecases.patterns;


import com.techchallenge.pedido.domain.Pedido;

public interface NotificacaoPedidoStrategy {
    String notificar(Pedido pedido);
}
