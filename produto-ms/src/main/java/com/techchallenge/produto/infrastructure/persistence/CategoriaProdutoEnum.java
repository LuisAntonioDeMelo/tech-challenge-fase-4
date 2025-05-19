package com.techchallenge.produto.infrastructure.persistence;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CategoriaProdutoEnum {
    LANCHE(1, "Lanche"),
    ACOMPANHAMENTO(2, "Acompanhamento"),
    BEBIDA(3, "Bebida"),
    SOBREMESA(4, "Sobremesa");

    private final Integer codigo;
    private final String descricao;

    CategoriaProdutoEnum(Integer codigo, String descricao) {
        this.codigo = codigo;
        this.descricao =  descricao;
    }
    public static CategoriaProdutoEnum obter(String name){
        return Arrays.stream(CategoriaProdutoEnum.values())
                .filter(ct -> ct.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada:  " + name));
    }

    public static CategoriaProdutoEnum obterPorNome(String nome){
        return Arrays.stream(CategoriaProdutoEnum.values())
                .filter(ct -> ct.getDescricao().equalsIgnoreCase(nome))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada para o nome: " + nome));
    }
}
