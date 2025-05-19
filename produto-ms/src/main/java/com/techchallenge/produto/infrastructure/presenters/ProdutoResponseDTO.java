package com.techchallenge.produto.infrastructure.presenters;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProdutoResponseDTO {

    private UUID id;
    private String nomeProduto;
    private String descricao;
    private Double largura;
    private Double altura;
    private String EAN;
    private BigDecimal peso;
    private BigDecimal valor;
    private String categoria;
    private Long idPedido;
    private Integer quantidade;

}
