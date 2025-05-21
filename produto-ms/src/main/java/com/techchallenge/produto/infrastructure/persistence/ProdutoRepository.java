package com.techchallenge.produto.infrastructure.persistence;

import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends CouchbaseRepository<ProdutoEntity,Long> {

    Optional<ProdutoEntity> findById(UUID id);
    List<ProdutoEntity> findAllByCategoriaProdutoEnum(CategoriaProdutoEnum categoriaProdutoEnum);
}
