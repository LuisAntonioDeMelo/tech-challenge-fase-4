package com.techchallenge.cliente.infrastructure.controllers;

import com.techchallenge.cliente.application.usecases.ClienteUseCase;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteExistenteException;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteNotFoundException;
import com.techchallenge.cliente.application.usecases.exceptions.InvalidCpfException;
import com.techchallenge.cliente.domain.Cliente;
import com.techchallenge.cliente.infrastructure.controllers.inputs.ClienteRequest;
import com.techchallenge.cliente.infrastructure.controllers.outputs.ClienteResponseDTO;
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

    private ClienteController clienteController;

    @BeforeEach
    void setUp() {
        clienteController = new ClienteController(clienteUseCase);
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void deveCriarClienteComSucesso() {
        // Arrange
        ClienteRequest request = new ClienteRequest();
        request.setCpf("86599902062");
        request.setNome("João Silva");
        request.setEmail("joao@example.com");
        request.setTelefone("11999999999");

        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteId, "86599902062", "João Silva", "joao@example.com", "11999999999");
        
        when(clienteUseCase.criarCliente(any(Cliente.class))).thenReturn(cliente);

        // Act
        ResponseEntity<ClienteResponseDTO> response = clienteController.criarCliente(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals("86599902062", response.getBody().getCpf());
    }

    @Test
    @DisplayName("Deve obter cliente por CPF com sucesso")
    void deveObterClientePorCpfComSucesso() {
        // Arrange
        String cpf = "86599902062";
        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteId, cpf, "João Silva", "joao@example.com", "11999999999");
        
        when(clienteUseCase.obterCliente(cpf)).thenReturn(cliente);

        // Act
        ResponseEntity<ClienteResponseDTO> response = clienteController.obterClientePorCPF(cpf);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("João Silva", response.getBody().getNome());
        assertEquals(cpf, response.getBody().getCpf());
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente já existe")
    void deveLancarExcecaoQuandoClienteJaExiste() {
        // Arrange
        ClienteRequest request = new ClienteRequest();
        request.setCpf("86599902062");
        request.setNome("João Silva");
        request.setEmail("joao@example.com");
        request.setTelefone("11999999999");

        when(clienteUseCase.criarCliente(any(Cliente.class))).thenThrow(new ClienteExistenteException("Cliente já existe"));

        // Act & Assert
        assertThrows(ClienteExistenteException.class, () -> {
            clienteController.criarCliente(request);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF é inválido")
    void deveLancarExcecaoQuandoCpfInvalido() {
        // Arrange
        ClienteRequest request = new ClienteRequest();
        request.setCpf("11111111111");
        request.setNome("João Silva");
        request.setEmail("joao@example.com");
        request.setTelefone("11999999999");

        when(clienteUseCase.criarCliente(any(Cliente.class))).thenThrow(new InvalidCpfException("CPF inválido"));

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
        when(clienteUseCase.obterCliente(anyString())).thenThrow(new ClienteNotFoundException("Cliente não encontrado"));

        // Act & Assert
        assertThrows(ClienteNotFoundException.class, () -> {
            clienteController.obterClientePorCPF(cpf);
        });
    }
}
