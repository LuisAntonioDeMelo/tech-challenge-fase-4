package com.techchallenge.produto.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Produto {

    private UUID id;
    private String nomeProduto;
    private String descricao;
    private Double largura;
    private Double altura;
    private String EAN;
    private BigDecimal peso;
    private BigDecimal valor;
    private CategoriaProduto categoriaProduto;
    private Integer quantidade;
    //private List<Pedido> pedidos;

    public Produto(String id, Integer quantidade, String ean) {
        this.id = UUID.fromString(id);
        this.EAN = ean;
        this.quantidade = quantidade;
    }
}
