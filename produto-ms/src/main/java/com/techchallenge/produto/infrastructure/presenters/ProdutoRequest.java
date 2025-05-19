package com.techchallenge.produto.infrastructure.presenters;

import java.math.BigDecimal;

public record ProdutoRequest(
        String nomeProduto,
        String descricao,
        Double largura,
        Double altura,
        String EAN,
        BigDecimal peso,
        BigDecimal valor,
        String categoria,
        Integer quantidade
) {
}
