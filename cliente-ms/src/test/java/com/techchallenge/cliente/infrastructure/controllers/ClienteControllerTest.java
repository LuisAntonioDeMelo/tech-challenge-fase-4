package com.techchallenge.cliente.infrastructure.controllers;

import com.techchallenge.cliente.application.usecases.ClienteUseCase;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteExistenteException;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteNotFoundException;
import com.techchallenge.cliente.application.usecases.exceptions.InvalidCpfException;
import com.techchallenge.cliente.domain.Cliente;
import com.techchallenge.cliente.infrastructure.controllers.inputs.ClienteRequest;
import com.techchallenge.cliente.infrastructure.controllers.outputs.ClienteResponseDTO;
import com.techchallenge.cliente.infrastructure.converters.ClienteConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteControllerTest {

    @Mock
    private ClienteUseCase clienteUseCase;
    
    @Mock
    private ClienteConverter clienteConverter;

    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        clienteController = new ClienteController(clienteUseCase, clienteConverter);
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void deveCriarClienteComSucesso() {
        // Arrange
        ClienteRequest request = new ClienteRequest("86599902062", "João Silva", "joao@example.com", "11999999999");

        UUID clienteId = UUID.randomUUID();
        Cliente clienteDomain = new Cliente(null, "86599902062", "João Silva", "joao@example.com", "11999999999");
        Cliente clienteSalvo = new Cliente(clienteId, "86599902062", "João Silva", "joao@example.com", "11999999999");
        
        ClienteResponseDTO responseDTO = ClienteResponseDTO.builder()
            .codigoCliente(clienteId)
            .cpf("86599902062")
            .nomeCliente("João Silva")
            .emailCliente("joao@example.com")
            .numeroTelefone("11999999999")
            .build();
        
        when(clienteConverter.toDomain(request)).thenReturn(clienteDomain);
        when(clienteUseCase.criarCliente(clienteDomain)).thenReturn(clienteSalvo);
        when(clienteConverter.toDto(clienteSalvo)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClienteResponseDTO> response = clienteController.criarCliente(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNomeCliente());
        assertEquals("86599902062", response.getBody().getCpf());
    }

    @Test
    @DisplayName("Deve obter cliente por CPF com sucesso")
    void deveObterClientePorCpfComSucesso() {
        // Arrange
        String cpf = "86599902062";
        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteId, cpf, "João Silva", "joao@example.com", "11999999999");
        
        ClienteResponseDTO responseDTO = ClienteResponseDTO.builder()
            .codigoCliente(clienteId)
            .cpf(cpf)
            .nomeCliente("João Silva")
            .emailCliente("joao@example.com")
            .numeroTelefone("11999999999")
            .build();
        
        when(clienteUseCase.obterCliente(cpf)).thenReturn(cliente);
        when(clienteConverter.toDto(cliente)).thenReturn(responseDTO);

        // Act
        ResponseEntity<ClienteResponseDTO> response = clienteController.identificarCliente(cpf);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNomeCliente());
        assertEquals(cpf, response.getBody().getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente já existe")
    void deveLancarExcecaoQuandoClienteJaExiste() {
        // Arrange
        ClienteRequest request = new ClienteRequest("86599902062", "João Silva", "joao@example.com", "11999999999");
        Cliente clienteDomain = new Cliente(null, "86599902062", "João Silva", "joao@example.com", "11999999999");

        when(clienteConverter.toDomain(request)).thenReturn(clienteDomain);
        when(clienteUseCase.criarCliente(clienteDomain)).thenThrow(new ClienteExistenteException("Cliente já existe"));

        // Act & Assert
        assertThrows(ClienteExistenteException.class, () -> {
            clienteController.criarCliente(request);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF é inválido")
    void deveLancarExcecaoQuandoCpfInvalido() {
        // Arrange
        ClienteRequest request = new ClienteRequest("11111111111", "João Silva", "joao@example.com", "11999999999");
        Cliente clienteDomain = new Cliente(null, "11111111111", "João Silva", "joao@example.com", "11999999999");

        when(clienteConverter.toDomain(request)).thenReturn(clienteDomain);
        when(clienteUseCase.criarCliente(clienteDomain)).thenThrow(new InvalidCpfException("CPF inválido"));

        // Act & Assert
        assertThrows(InvalidCpfException.class, () -> {
            clienteController.criarCliente(request);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não é encontrado")
    void deveLancarExcecaoQuandoClienteNaoEncontrado() {
        // Arrange
        String cpf = "86599902062";
        when(clienteUseCase.obterCliente(cpf)).thenThrow(new ClienteNotFoundException("Cliente não encontrado"));

        // Act & Assert
        assertThrows(ClienteNotFoundException.class, () -> {
            clienteController.identificarCliente(cpf);
        });
    }
}
