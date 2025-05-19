package com.techchallenge.produto.domain;

import java.util.Arrays;

public enum CategoriaProduto {
    LANCHE,
    ACOMPANHAMENTO,
    BEBIDA,
    SOBREMESA;

    public static CategoriaProduto obterPorNome(String nome){
        return Arrays.stream(CategoriaProduto.values())
                .filter(ct -> ct.name().equalsIgnoreCase(nome))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada para o nome: " + nome));
    }
}
