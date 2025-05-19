package com.techchallenge.produto.application.usecases;


import com.techchallenge.produto.domain.Produto;

import java.util.List;
import java.util.UUID;

public interface ProdutoUseCase {

    Produto criarProduto(Produto produto);

    Produto alterarProduto(Produto produto);

    void deletarProduto(UUID id);

    List<Produto> obterProdutos();

    List<Produto> obterProdutosPorCategoria(String categoria);
}
