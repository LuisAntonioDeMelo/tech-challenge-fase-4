package com.techchallenge.cliente.infrastructure.controllers;

import com.techchallenge.cliente.application.usecases.ClienteUseCase;
import com.techchallenge.cliente.domain.Cliente;
import com.techchallenge.cliente.infrastructure.controllers.inputs.ClienteRequest;
import com.techchallenge.cliente.infrastructure.controllers.outputs.ClienteResponseDTO;
import com.techchallenge.cliente.infrastructure.converters.ClienteConverter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/clientes")
@Tag(name = "Clientes", description = "Operações para gerenciamento de clientes")
public class ClienteController {

    private final ClienteUseCase clienteUseCase;
    private final ClienteConverter clienteConverter;

    public ClienteController(ClienteUseCase clienteUseCase, ClienteConverter clienteConverter) {
        this.clienteUseCase = clienteUseCase;
        this.clienteConverter = clienteConverter;
    }

    @Operation(summary = "Buscar cliente pelo CPF", description = "Retorna os dados do cliente correspondente ao CPF informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @GetMapping("/cliente")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteResponseDTO> identificarCliente(
            @RequestParam("cpf")
            @Parameter(description = "CPF do cliente (somente números)") String cpf) {
        Cliente cliente = clienteUseCase.obterCliente(cpf);
        return ResponseEntity.ok(clienteConverter.toDto(cliente));
    }

    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação.")
    })
    @PostMapping("/novo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteResponseDTO> criarCliente(
            @RequestBody
            @Parameter(description = "Dados do cliente a ser criado") ClienteRequest clienteRequest) {
        Cliente cliente = clienteUseCase.criarCliente(clienteConverter.toDomain(clienteRequest));
        return ResponseEntity.ok(clienteConverter.toDto(cliente));
    }

    @Operation(summary = "Editar cliente", description = "Atualiza os dados de um cliente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ClienteResponseDTO> editarCliente(
            @PathVariable
            @Parameter(description = "ID do cliente a ser editado") UUID id,
            @RequestBody
            @Parameter(description = "Novos dados do cliente") ClienteRequest clienteRequest) {
        Cliente cliente = clienteUseCase.editarCliente(clienteConverter.toDomain(id, clienteRequest));
        return ResponseEntity.ok(clienteConverter.toDto(cliente));
    }

    @Operation(summary = "Deletar cliente", description = "Remove um cliente com base no ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso."),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado.")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deletarCliente(
            @PathVariable
            @Parameter(description = "ID do cliente a ser deletado") UUID id) {
        clienteUseCase.deletarCliente(id);
        return ResponseEntity.ok().build();
    }
}
