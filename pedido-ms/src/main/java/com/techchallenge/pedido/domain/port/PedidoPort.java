package com.techchallenge.pedido.domain.port;


import com.techchallenge.pedido.domain.Pedido;

import java.util.List;

public interface PedidoPort {

    List<Pedido> listarPedidos();

    List<Pedido> listarPedidosPorSituacao(String situacao);

    Pedido criarPedido(Pedido pedido);

    Pedido alterarSituacaoPedido(Long idPedido, String situacaoPedido);

    Pedido buscarPedidoPorClienteId(String idPedido);

    Pedido checkoutPedido(Long idPedido);

    Pedido alterarPedido(Pedido pedido);

}
