package com.techchallenge.pedido.application.usecases;


import com.techchallenge.pedido.domain.port.PedidoPort;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoFilaDTO;

import java.util.List;


public class FilaPedidoUseCase {

    private final PedidoPort pedidoPort;
    private final NotificaFilaUseCase notificaFilaUseCase;

    public FilaPedidoUseCase(PedidoPort pedidoPort, NotificaFilaUseCase notificaFilaUseCase) {
        this.pedidoPort = pedidoPort;
        this.notificaFilaUseCase = notificaFilaUseCase;
    }

    public List<PedidoFilaDTO> listarPedidosNaFila() {
        List<Pedido> pedidosEmAndamento = pedidoPort.listarPedidos()
                .stream()
                .filter(pedido ->
                        !pedido.getSituacaoPedido().name().equals(SituacaoPedido.FINALIZADO.name()) &&
                                !pedido.getSituacaoPedido().name().equals(SituacaoPedido.PRONTO.name()))
                .toList();

        return pedidosEmAndamento.stream().map(pedido ->
                        PedidoFilaDTO.builder()
                                //.cpf(pedido.getCliente().getCpf())
                                //.nomeCliente(pedido.getCliente().getNome())
                                .statusPedido(notificaFilaUseCase.notificaFila(pedido))
                                .horarioIniciou(pedido.getHorarioInicio().toString())
                                .codigoPedido(pedido.getCodigoPedido())
                                .build())
                .toList();
    }

    public List<PedidoFilaDTO> listarPedidosProntos() {
        List<Pedido> pedidosRetirada = pedidoPort.listarPedidos().stream().filter(
                pedido -> pedido.getSituacaoPedido().equals(SituacaoPedido.PRONTO)).toList();

        return pedidosRetirada.stream().map(pedido ->
                        PedidoFilaDTO.builder()
                                //.cpf(pedido.getCliente().getCpf())
                                //.nomeCliente(pedido.getCliente().getNome())
                                .statusPedido(notificaFilaUseCase.notificaFila(pedido))
                                .horarioIniciou(pedido.getHorarioInicio().toString())
                                .codigoPedido(pedido.getCodigoPedido())
                                .build())
                .toList();
    }

}
