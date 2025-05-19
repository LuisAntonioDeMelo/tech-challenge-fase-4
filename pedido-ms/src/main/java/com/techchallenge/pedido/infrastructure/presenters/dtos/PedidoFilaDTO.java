package com.techchallenge.pedido.infrastructure.presenters.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PedidoFilaDTO {
    private String cpf;
    private String codigoPedido;
    private String nomeCliente;
    private String statusPedido;
    private String horarioIniciou;
}
