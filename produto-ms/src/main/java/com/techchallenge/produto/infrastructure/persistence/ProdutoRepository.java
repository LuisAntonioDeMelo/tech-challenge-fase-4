package com.techchallenge.produto.infrastructure.persistence;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface ProdutoRepository extends CouchbaseRepository<ProdutoEntity,Long> {
    List<ProdutoEntity> findAllByCategoriaProdutoEnum(CategoriaProdutoEnum categoriaProdutoEnum);
}
