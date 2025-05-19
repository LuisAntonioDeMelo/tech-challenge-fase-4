package com.techchallenge.pedido.infrastructure.persistence;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum SituacaoPedidoEnum {
    CRIADO(1, "Criado"),
    INCIAR_PREPARACAO(2, "Iniciar Preparação"),
    EM_PREPARACAO(3, "Em Preparação"),
    MONTAGEM(4, "Montagem"),
    PRONTO(5,"Pronto"),
    RECEBIDO(6, "Recebido"),
    FINALIZADO(7, "Finalizado");

    private final Integer codigo;
    private final String descricao;

    SituacaoPedidoEnum(Integer codigo, String descricao) {
        this.codigo =  codigo;
        this.descricao = descricao;
    }

    public static SituacaoPedidoEnum obter(String name){
        return Arrays.stream(SituacaoPedidoEnum.values())
                .filter(ct -> ct.name().equalsIgnoreCase(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("Situação não encontrada:  " + name));
    }

}
