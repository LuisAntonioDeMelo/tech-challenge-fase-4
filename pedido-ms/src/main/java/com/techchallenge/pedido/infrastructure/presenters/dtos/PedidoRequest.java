package com.techchallenge.pedido.infrastructure.presenters.dtos;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record PedidoRequest(
        UUID idCliente,
        List<ProdutoDTO> produtos,
        String situcaoPedido,
        LocalDateTime horario
) {
}
