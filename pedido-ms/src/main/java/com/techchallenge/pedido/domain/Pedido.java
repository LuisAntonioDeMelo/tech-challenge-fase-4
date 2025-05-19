package com.techchallenge.pedido.domain;

import com.techchallenge.pedido.infrastructure.presenters.dtos.ProdutoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Long idPedido;

    private String codigoPedido;

    private UUID cliente;

    private List<ProdutoDTO> produtos;

    private LocalDateTime horarioInicio;

    private LocalDateTime horarioFinalizacao;

    private BigDecimal valorTotalPedido;

    private SituacaoPedido situacaoPedido;

    private UUID pagamento;
}