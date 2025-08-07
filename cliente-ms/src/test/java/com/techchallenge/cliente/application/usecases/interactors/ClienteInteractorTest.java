package com.techchallenge.cliente.application.usecases.interactors;

import com.techchallenge.cliente.application.gateway.ClienteGateway;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteExistenteException;
import com.techchallenge.cliente.application.usecases.exceptions.ClienteNotFoundException;
import com.techchallenge.cliente.application.usecases.exceptions.InvalidCpfException;
import com.techchallenge.cliente.domain.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteInteractorTest {

    @Mock
    private ClienteGateway clienteGateway;

    private ClienteInteractor clienteInteractor;

    @BeforeEach
    void setUp() {
        clienteInteractor = new ClienteInteractor(clienteGateway);
    }

    @Test
    @DisplayName("Deve criar um cliente quando dados são válidos")
    void deveCriarClienteQuandoDadosValidos() {
        // Arrange
        Cliente cliente = new Cliente(null, "86599902062", "João Silva", "joao@example.com", "11999999999");
        when(clienteGateway.obterClientePorCPF(cliente.getCpf())).thenReturn(Optional.empty());
        when(clienteGateway.criarCliente(cliente)).thenReturn(cliente);

        // Act
        Cliente clienteCriado = clienteInteractor.criarCliente(cliente);

        // Assert
        assertNotNull(clienteCriado);
        assertEquals(cliente.getNome(), clienteCriado.getNome());
        verify(clienteGateway, times(1)).criarCliente(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF já existe")
    void deveLancarExcecaoQuandoCpfJaExiste() {
        // Arrange
        Cliente cliente = new Cliente(null, "86599902062", "João Silva", "joao@example.com", "11999999999");
        when(clienteGateway.obterClientePorCPF(cliente.getCpf())).thenReturn(Optional.of(cliente));

        // Act & Assert
        assertThrows(ClienteExistenteException.class, () -> {
            clienteInteractor.criarCliente(cliente);
        });
        verify(clienteGateway, never()).criarCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF é inválido")
    void deveLancarExcecaoQuandoCpfInvalido() {
        // Arrange
        Cliente cliente = new Cliente(null, "11111111111", "João Silva", "joao@example.com", "11999999999");

        // Act & Assert
        assertThrows(InvalidCpfException.class, () -> {
            clienteInteractor.criarCliente(cliente);
        });
        verify(clienteGateway, never()).criarCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve obter cliente quando CPF existe")
    void deveObterClienteQuandoCpfExiste() {
        // Arrange
        String cpf = "86599902062";
        UUID U = UUID.randomUUID();
        Cliente cliente = new Cliente(U, cpf, "João Silva", "joao@example.com", "11999999999");
        when(clienteGateway.obterClientePorCPF(cpf)).thenReturn(Optional.of(cliente));

        // Act
        Cliente clienteObtido = clienteInteractor.obterCliente(cpf);

        // Assert
        assertNotNull(clienteObtido);
        assertEquals(cliente.getNome(), clienteObtido.getNome());
        verify(clienteGateway, times(1)).obterClientePorCPF(cpf);
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF não existe")
    void deveLancarExcecaoQuandoCpfNaoExiste() {
        // Arrange
        String cpf = "86599902062";
        when(clienteGateway.obterClientePorCPF(cpf)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNotFoundException.class, () -> {
            clienteInteractor.obterCliente(cpf);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF está vazio")
    void deveLancarExcecaoQuandoCpfVazio() {
        // Arrange
        String cpf = "";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            clienteInteractor.obterCliente(cpf);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF é nulo no método obterCliente")
    void deveLancarExcecaoQuandoCpfNuloNoObterCliente() {
        // Arrange
        String cpf = "   "; // blank

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            clienteInteractor.obterCliente(cpf);
        });
    }

    @Test
    @DisplayName("Deve lançar exceção quando CPF é inválido no método obterCliente")
    void deveLancarExcecaoQuandoCpfInvalidoNoObterCliente() {
        // Arrange
        String cpf = "12345678901"; // CPF inválido

        // Act & Assert
        assertThrows(InvalidCpfException.class, () -> {
            clienteInteractor.obterCliente(cpf);
        });
    }

    @Test
    @DisplayName("Deve editar cliente com sucesso")
    void deveEditarClienteComSucesso() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteId, "86599902062", "João Silva Editado", "joao.editado@example.com", "11999999999");
        when(clienteGateway.editarCliente(cliente)).thenReturn(cliente);

        // Act
        Cliente clienteEditado = clienteInteractor.editarCliente(cliente);

        // Assert
        assertNotNull(clienteEditado);
        assertEquals("João Silva Editado", clienteEditado.getNome());
        verify(clienteGateway, times(1)).editarCliente(cliente);
    }

    @Test
    @DisplayName("Deve lançar exceção ao editar cliente com CPF inválido")
    void deveLancarExcecaoAoEditarClienteComCpfInvalido() {
        // Arrange
        Cliente cliente = new Cliente(null, "11111111111", "João Silva", "joao@example.com", "11999999999");

        // Act & Assert
        assertThrows(InvalidCpfException.class, () -> {
            clienteInteractor.editarCliente(cliente);
        });
        verify(clienteGateway, never()).editarCliente(any(Cliente.class));
    }

    @Test
    @DisplayName("Deve lançar exceção ao editar cliente inexistente")
    void deveLancarExcecaoAoEditarClienteInexistente() {
        // Arrange
        Cliente cliente = new Cliente(null, "86599902062", "João Silva", "joao@example.com", "11999999999");
        when(clienteGateway.editarCliente(cliente)).thenReturn(null);

        // Act & Assert
        assertThrows(ClienteNotFoundException.class, () -> {
            clienteInteractor.editarCliente(cliente);
        });
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void deveDeletarClienteComSucesso() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        Cliente cliente = new Cliente(clienteId, "86599902062", "João Silva", "joao@example.com", "11999999999");
        when(clienteGateway.obterPorId(clienteId)).thenReturn(Optional.of(cliente));
        doNothing().when(clienteGateway).deletarCliente(clienteId);

        // Act
        clienteInteractor.deletarCliente(clienteId);

        // Assert
        verify(clienteGateway, times(1)).deletarCliente(clienteId);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar cliente inexistente")
    void deveLancarExcecaoAoDeletarClienteInexistente() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        when(clienteGateway.obterPorId(clienteId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ClienteNotFoundException.class, () -> {
            clienteInteractor.deletarCliente(clienteId);
        });
        verify(clienteGateway, never()).deletarCliente(any(UUID.class));
    }
}
