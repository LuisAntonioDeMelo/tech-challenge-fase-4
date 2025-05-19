package com.techchallenge.pedido.infrastructure.controllers;

import com.techchallenge.pedido.application.usecases.AlterarPedidoUseCase;
import com.techchallenge.pedido.application.usecases.CheckoutPedidoUseCase;
import com.techchallenge.pedido.application.usecases.CriarPedidoUseCase;
import com.techchallenge.pedido.application.usecases.ListarPedidosUseCase;
import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.infrastructure.presenters.PedidoPresenter;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoRequest;
import com.techchallenge.pedido.infrastructure.presenters.dtos.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PedidoControllerTest {

    @Mock
    private CriarPedidoUseCase criarPedidoUseCase;
    
    @Mock
    private AlterarPedidoUseCase alterarPedidoUseCase;
    
    @Mock
    private ListarPedidosUseCase listarPedidosUseCase;
    
    @Mock
    private CheckoutPedidoUseCase checkoutPedidoUseCase;
    
    @Mock
    private PedidoPresenter pedidoPresenter;

    private PedidoController pedidoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pedidoController = new PedidoController(
                checkoutPedidoUseCase,
                criarPedidoUseCase,
                alterarPedidoUseCase,
                listarPedidosUseCase,
                pedidoPresenter
        );
    }

    @Test
    void deveObterTodosPedidos() {
        // Arrange
        Pedido pedido1 = new Pedido();
        pedido1.setIdPedido(1L);
        pedido1.setCodigoPedido("P001");
        pedido1.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        Pedido pedido2 = new Pedido();
        pedido2.setIdPedido(2L);
        pedido2.setCodigoPedido("P002");
        pedido2.setSituacaoPedido(SituacaoPedido.PRONTO);
        
        List<Pedido> pedidos = Arrays.asList(pedido1, pedido2);
        
        PedidoPresenter.PedidoViewModel viewModel1 = new PedidoPresenter.PedidoViewModel(
                1L, "P001", "EM_PREPARACAO", LocalDateTime.now(), null, BigDecimal.valueOf(25.90)
        );
        
        PedidoPresenter.PedidoViewModel viewModel2 = new PedidoPresenter.PedidoViewModel(
                2L, "P002", "PRONTO", LocalDateTime.now(), null, BigDecimal.valueOf(35.50)
        );
        
        List<PedidoPresenter.PedidoViewModel> viewModels = Arrays.asList(viewModel1, viewModel2);
        
        when(listarPedidosUseCase.listarPedidos()).thenReturn(pedidos);
        when(pedidoPresenter.apresentar(pedidos)).thenReturn(viewModels);
        
        // Act
        ResponseEntity<List<PedidoPresenter.PedidoViewModel>> resposta = pedidoController.obterPedidos();
        
        // Assert
        assertNotNull(resposta);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(viewModels, resposta.getBody());
        verify(listarPedidosUseCase).listarPedidos();
        verify(pedidoPresenter).apresentar(pedidos);
    }

    @Test
    void deveCriarPedido() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        LocalDateTime horario = LocalDateTime.now();
        List<ProdutoDTO> produtos = List.of(
            ProdutoDTO.builder()
                .id(UUID.randomUUID())
                .nome("Hamburguer")
                .valor(BigDecimal.valueOf(25.90))
                .quantidade(2)
                .build()
        );
        
        PedidoRequest pedidoRequest = new PedidoRequest(
            clienteId,
            produtos,
            "CRIADO",
            horario
        );
        
        Pedido pedidoCriado = new Pedido();
        pedidoCriado.setIdPedido(1L);
        pedidoCriado.setCodigoPedido("P001");
        pedidoCriado.setCliente(clienteId);
        pedidoCriado.setProdutos(produtos);
        pedidoCriado.setSituacaoPedido(SituacaoPedido.CRIADO);
        
        PedidoPresenter.PedidoViewModel viewModel = new PedidoPresenter.PedidoViewModel(
                1L, "P001", "CRIADO", horario, null, BigDecimal.valueOf(51.80)
        );
        
        when(criarPedidoUseCase.criarPedido(pedidoRequest)).thenReturn(pedidoCriado);
        when(pedidoPresenter.apresentar(pedidoCriado)).thenReturn(viewModel);
        
        // Act
        ResponseEntity<PedidoPresenter.PedidoViewModel> resposta = pedidoController.criarPedido(pedidoRequest);
        
        // Assert
        assertNotNull(resposta);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(viewModel, resposta.getBody());
        verify(criarPedidoUseCase).criarPedido(pedidoRequest);
        verify(pedidoPresenter).apresentar(pedidoCriado);
    }

    @Test
    void deveAlterarSituacaoPedido() {
        // Arrange
        Long pedidoId = 1L;
        String novaSituacao = "EM_PREPARACAO";
        
        Pedido pedidoAlterado = new Pedido();
        pedidoAlterado.setIdPedido(pedidoId);
        pedidoAlterado.setCodigoPedido("P001");
        pedidoAlterado.setSituacaoPedido(SituacaoPedido.EM_PREPARACAO);
        
        PedidoPresenter.PedidoViewModel viewModel = new PedidoPresenter.PedidoViewModel(
                1L, "P001", "EM_PREPARACAO", LocalDateTime.now(), null, BigDecimal.valueOf(25.90)
        );
        
        when(alterarPedidoUseCase.alterarSituacaoPedido(eq(pedidoId), eq(novaSituacao))).thenReturn(pedidoAlterado);
        when(pedidoPresenter.apresentar(pedidoAlterado)).thenReturn(viewModel);
        
        // Act
        ResponseEntity<PedidoPresenter.PedidoViewModel> resposta = pedidoController.alterarSituacaoPedido(pedidoId, novaSituacao);
        
        // Assert
        assertNotNull(resposta);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        assertEquals(viewModel, resposta.getBody());
        verify(alterarPedidoUseCase).alterarSituacaoPedido(pedidoId, novaSituacao);
        verify(pedidoPresenter).apresentar(pedidoAlterado);
    }
}
