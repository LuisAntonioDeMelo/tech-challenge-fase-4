package com.techchallenge.pedido.infrastructure.presenters.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProdutoDTO {
    private UUID id;
    private String EAN;
    private String nome;
    private BigDecimal valor;
    private String descricao;
    private Integer quantidade;
}
