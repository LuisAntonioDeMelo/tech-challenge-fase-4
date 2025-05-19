package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.domain.port.PedidoPort;

import java.time.LocalDateTime;

public class AlterarPedidoUseCase {

    private final PedidoPort pedidoPort;

    public AlterarPedidoUseCase(PedidoPort pedidoGateway) {
        this.pedidoPort = pedidoGateway;
    }

    public Pedido alterarSituacaoPedido(Long id, String situacao) {
        if(situacao.equals(SituacaoPedido.FINALIZADO.name())){
            Pedido pedido = pedidoPort.alterarSituacaoPedido(id, situacao);
            pedido.setHorarioFinalizacao(LocalDateTime.now());
            pedidoPort.alterarPedido(pedido);
            return pedido;
        }
        return pedidoPort.alterarSituacaoPedido(id, situacao);
    }

}
