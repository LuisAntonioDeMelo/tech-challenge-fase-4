package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.port.PedidoPort;

import java.util.List;

public class ListarPedidosUseCase {

    private final PedidoPort pedidoPort;

    public ListarPedidosUseCase(PedidoPort pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public List<Pedido> listarPedidos() {
        return pedidoPort.listarPedidos();
    }

    public List<Pedido> listarPedidosPorSituacao(String situacao) {
        return pedidoPort.listarPedidosPorSituacao(situacao);
    }
}
