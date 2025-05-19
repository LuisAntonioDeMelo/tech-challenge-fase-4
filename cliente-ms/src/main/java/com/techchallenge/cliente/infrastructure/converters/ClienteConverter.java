package com.techchallenge.cliente.infrastructure.converters;



import com.techchallenge.cliente.domain.Cliente;
import com.techchallenge.cliente.infrastructure.controllers.inputs.ClienteRequest;
import com.techchallenge.cliente.infrastructure.controllers.outputs.ClienteResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter {


    public Cliente toDomain(ClienteRequest clienteRequest) {
       return new Cliente( null ,clienteRequest.cpf(),clienteRequest.nome(), clienteRequest.email(), clienteRequest.telefone());
    }

    public Cliente toDomain(Long id, ClienteRequest clienteRequest) {
        return new Cliente(id, clienteRequest.cpf(),clienteRequest.nome(), clienteRequest.email(), clienteRequest.telefone());
    }

    public ClienteResponseDTO toDto(Cliente cliente) {
        return ClienteResponseDTO
                .builder()
                .codigoCliente(cliente.getId())
                .cpf(cliente.getCpf())
                .nomeCliente(cliente.getNome())
                .emailCliente(cliente.getEmail())
                .numeroTelefone(cliente.getTelefone())
                .build();
    }
}
