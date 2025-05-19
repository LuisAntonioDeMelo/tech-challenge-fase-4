package com.techchallenge.produto.infrastructure.converters;


import com.techchallenge.produto.domain.CategoriaProduto;
import com.techchallenge.produto.domain.Produto;
import com.techchallenge.produto.infrastructure.presenters.ProdutoRequest;
import com.techchallenge.produto.infrastructure.presenters.ProdutoResponseDTO;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProdutoConverter {

    public Produto toDomain(ProdutoRequest produtoRequest) {
        Produto produto = new Produto();
        produto.setNomeProduto(produtoRequest.nomeProduto());
        produto.setAltura(produtoRequest.altura());
        produto.setLargura(produtoRequest.largura());
        produto.setDescricao(produtoRequest.descricao());
        produto.setPeso(produtoRequest.peso());
        produto.setEAN(produtoRequest.EAN());
        produto.setValor(produtoRequest.valor());
        produto.setCategoriaProduto(CategoriaProduto.obterPorNome(produtoRequest.categoria()));
        return produto;
    }

    public Produto toDomain(String id, ProdutoRequest produtoRequest) {
        Produto produto =  toDomain(produtoRequest);
        produto.setId(UUID.fromString(id));
        return produto;
    }

    public ProdutoResponseDTO toDto(Produto produto) {
        return ProdutoResponseDTO
                .builder()
                .id(produto.getId())
                .categoria(produto.getCategoriaProduto().name())
                .nomeProduto(produto.getNomeProduto())
                .descricao(produto.getDescricao())
                .valor(produto.getValor())
                .peso(produto.getPeso())
                .altura(produto.getAltura())
                .largura(produto.getLargura())
                .EAN(produto.getEAN())
                .build();
    }
}
