package com.techchallenge.cliente.application.usecases.interactors;


import com.techchallenge.cliente.application.gateway.ClienteGateway;
import com.techchallenge.cliente.application.usecases.ClienteUseCase;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteExistenteException;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteNotFoundException;
import com.techchallenge.cliente.application.usecases.exceptions.InvalidCpfException;
import com.techchallenge.cliente.domain.Cliente;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class ClienteInteractor implements ClienteUseCase {

    private final ClienteGateway clienteGateway;

    public ClienteInteractor(ClienteGateway clienteGateway) {
        this.clienteGateway = clienteGateway;
    }

    @Override
    public Cliente criarCliente(Cliente cliente) {
        if (isInValidCPF(cliente.getCpf())) {
            throw new InvalidCpfException("CPF Inserido não é válido");
        }
        Optional<Cliente> clienteExiste = clienteGateway.obterClientePorCPF(cliente.getCpf());
        if (clienteExiste.isPresent()) {
            throw new ClienteExistenteException("já existe um cliente com mesmo CPF:" + cliente.getCpf());

        }
        return clienteGateway.criarCliente(cliente);
    }

    @Override
    public Cliente obterCliente(String cpf) {
        if (cpf.isBlank() || cpf.isEmpty()) {
            throw new IllegalArgumentException("Cpf não pode ser vazio ou nulo!");
        }
        if (isInValidCPF(cpf)) {
            throw new InvalidCpfException("CPF Inserido não é válido");
        }
        return clienteGateway.obterClientePorCPF(cpf)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado: " + cpf));
    }

    @Override
    public Cliente editarCliente(Cliente cliente) {
        if (isInValidCPF(cliente.getCpf())) {
            throw new InvalidCpfException("CPF Inserido não é válido");
        }

        Cliente clienteEditado = clienteGateway.editarCliente(cliente);

        if (Objects.isNull(clienteEditado)) {
            throw new ClienteNotFoundException("Cliente não encontrado: " + cliente.getCpf());
        }
        return clienteEditado;
    }

    @Override
    public void deletarCliente(UUID id) {
        Optional<Cliente> cliente = clienteGateway.obterPorId(id);
        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException("Cliente não encontrado");
        }
        clienteGateway.deletarCliente(cliente.get().getId());
    }

    private boolean isInValidCPF(String cpf) {
        cpf = cpf.replaceAll("\\D", "");
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return true;
        }
        int firstDigit = calculateVerificationDigit(cpf, 10);
        int secondDigit = calculateVerificationDigit(cpf, 11);
        return !cpf.endsWith(String.valueOf(firstDigit) + secondDigit);
    }

    private int calculateVerificationDigit(String cpf, int weight) {
        int sum = IntStream.range(0, weight - 1)
                .map(i -> Character.getNumericValue(cpf.charAt(i)) * (weight - i))
                .sum();
        int result = 11 - (sum % 11);
        return result > 9 ? 0 : result;
    }
}
