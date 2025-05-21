package com.techchallenge.cliente.infrastructure.gateways;


import com.techchallenge.cliente.application.gateway.ClienteGateway;
import com.techchallenge.cliente.application.usecases.exceptions.ClientePersistenceException;
import com.techchallenge.cliente.domain.Cliente;
import com.techchallenge.cliente.infrastructure.persistence.cliente.ClienteEntity;
import com.techchallenge.cliente.infrastructure.persistence.cliente.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ClienteRepositoryGateway implements ClienteGateway {

    private final ClienteRepository clienteRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    @ExceptionHandler(ClientePersistenceException.class)
    public Cliente criarCliente(Cliente cliente) {
        cliente.setId(UUID.randomUUID());
        ClienteEntity clienteEntity= clienteRepository.save(modelMapper.map(cliente, ClienteEntity.class));
        return modelMapper.map(clienteEntity, Cliente.class);
    }

    @Override
    public Cliente editarCliente(Cliente cliente) {
        Optional<ClienteEntity> clienteEntityDb = clienteRepository.findById(cliente.getId());
        if(clienteEntityDb.isPresent()) {
            ClienteEntity clienteEntity =  clienteEntityDb.get();
            clienteEntity.setNome(cliente.getNome());
            clienteEntity.setCpf(cliente.getCpf());
            clienteEntity.setTelefone(cliente.getTelefone());
            return modelMapper.map(clienteRepository.save(clienteEntity), Cliente.class);
        }
        return modelMapper.map(clienteEntityDb, Cliente.class);
    }

    @Override
    public void deletarCliente(UUID id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Optional<Cliente> obterPorId(UUID id) {
        return Optional.of(modelMapper.map(clienteRepository.findById(id), Cliente.class));
    }

    @Override
    public Optional<Cliente> obterClientePorCPF(String cpf) {
        Optional<ClienteEntity> clienteEntity = clienteRepository.findByCpf(cpf);
        return clienteEntity.map(entity -> modelMapper.map(entity, Cliente.class));
    }

}
