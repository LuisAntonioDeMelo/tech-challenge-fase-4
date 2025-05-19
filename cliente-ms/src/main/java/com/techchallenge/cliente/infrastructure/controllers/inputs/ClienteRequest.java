package com.techchallenge.cliente.infrastructure.controllers.inputs;

public record ClienteRequest(
        String cpf,
        String nome,
        String email,
        String telefone) {
}
