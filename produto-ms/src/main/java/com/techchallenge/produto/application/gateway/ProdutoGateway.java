package com.techchallenge.produto.application.gateway;


import com.techchallenge.produto.domain.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoGateway {

    Produto criarProduto(Produto produto);

    Produto alterarProduto(Produto produto);

    void deletarProduto(UUID id);

    List<Produto> listarProdutos();

    List<Produto> listarProdutosPorCategoria(String categoria);

    Optional<Produto> obterPorId(UUID id);
}
