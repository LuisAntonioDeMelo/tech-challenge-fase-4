package com.techchallenge.cliente.infrastructure.persistence.cliente;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ClienteRepository extends MongoRepository<ClienteEntity,Long> {
    Optional<ClienteEntity> findByCpf(String cpf);
}
