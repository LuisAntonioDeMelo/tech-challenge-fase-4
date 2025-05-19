package com.techchallenge.cliente.application.usecases;


import com.techchallenge.cliente.domain.Cliente;

import java.util.UUID;

public interface ClienteUseCase {

    public Cliente criarCliente(Cliente cliente);

    public Cliente obterCliente(String cpf);

    public Cliente editarCliente(Cliente cliente);

    public void deletarCliente(UUID id);
}
