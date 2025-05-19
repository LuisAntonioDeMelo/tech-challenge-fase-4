package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.port.PedidoPort;

public class CheckoutPedidoUseCase {

    private final PedidoPort pedidoPort;

    public CheckoutPedidoUseCase(PedidoPort pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido checkoutPedido(Long id) {
        return pedidoPort.checkoutPedido(id);
    }

}
