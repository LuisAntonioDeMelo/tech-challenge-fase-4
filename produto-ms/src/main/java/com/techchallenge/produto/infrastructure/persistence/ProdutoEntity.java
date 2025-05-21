package com.techchallenge.produto.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Document
public class ProdutoEntity {
    @Id
    private UUID id;

    private String nomeProduto;
    private String descricao;
    private Double largura;
    private Double altura;
    private String EAN;
    private BigDecimal peso;
    private BigDecimal valor;
    private Integer quantidade;
    private CategoriaProdutoEnum categoriaProdutoEnum;
    private List<UUID> pedidos;
}
