package com.techchallenge.cliente.application.gateway;

import com.techchallenge.cliente.domain.Cliente;

import java.util.Optional;
import java.util.UUID;

public interface ClienteGateway {

    public Cliente criarCliente(Cliente cliente);

    public Optional<Cliente> obterClientePorCPF(String cliente);

    public Cliente editarCliente(Cliente cliente);

    public void deletarCliente(UUID id);

    public Optional<Cliente> obterPorId(UUID id);
}
