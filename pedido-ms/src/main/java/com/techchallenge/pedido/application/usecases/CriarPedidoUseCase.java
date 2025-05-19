package com.techchallenge.pedido.application.usecases;


import com.techchallenge.pedido.domain.port.PedidoPort;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoRequest;

public class CriarPedidoUseCase {

    private final PedidoPort pedidoPort;

    public CriarPedidoUseCase(PedidoPort pedidoPort) {
        this.pedidoPort = pedidoPort;
    }

    public Pedido criarPedido(PedidoRequest pedidoRequest) {
        Pedido pedido = new Pedido();
        pedido.setCliente(pedidoRequest.idCliente());
        pedido.setSituacaoPedido(SituacaoPedido.obter(pedidoRequest.situcaoPedido()));
        pedido.setHorarioInicio(pedidoRequest.horario());
        pedido.setProdutos(pedidoRequest.produtos());
        return pedidoPort.criarPedido(pedido);
    }

}
