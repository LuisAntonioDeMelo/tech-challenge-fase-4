package com.techchallenge.pedido.domain;



import com.techchallenge.pedido.application.usecases.patterns.NotificacaoPedidoStrategy;
import com.techchallenge.pedido.application.usecases.patterns.pedidoNotificacaoStrategy.*;

import java.util.Arrays;

public enum SituacaoPedido {
    CRIADO(new CriarPedidoPedidoStrategy()),
    INCIAR_PREPARACAO(new InciarPreparacaoPedidoStrategy()),
    EM_PREPARACAO(new PedidoEmPreparacaoPedidoStrategy()),
    MONTAGEM(new PedidoEmMontagemPedidoStrategy()),
    PRONTO(new PedidoProntoPedidoStrategy()),
    RECEBIDO(new PedidoRecebidoPedidoStrategy()),
    FINALIZADO(new FinalizarPedidoPedidoStrategy());

    private final NotificacaoPedidoStrategy notificacaoPedidoStrategy;

    SituacaoPedido(NotificacaoPedidoStrategy notificacaoPedidoStrategy) {
        this.notificacaoPedidoStrategy = notificacaoPedidoStrategy;
    }

    public NotificacaoPedidoStrategy getStrategy() {
        return notificacaoPedidoStrategy;
    }

    public static SituacaoPedido obter(String situacao) {
        return Arrays.stream(SituacaoPedido.values())
                .filter(ct -> ct.name().equalsIgnoreCase(situacao))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Situação não existe : " + situacao));
    }
}
