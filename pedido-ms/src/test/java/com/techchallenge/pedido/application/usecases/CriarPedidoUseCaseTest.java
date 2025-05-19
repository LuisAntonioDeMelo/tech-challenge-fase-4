package com.techchallenge.pedido.application.usecases;

import com.techchallenge.pedido.domain.Pedido;
import com.techchallenge.pedido.domain.SituacaoPedido;
import com.techchallenge.pedido.domain.port.PedidoPort;
import com.techchallenge.pedido.infrastructure.presenters.dtos.PedidoRequest;
import com.techchallenge.pedido.infrastructure.presenters.dtos.ProdutoDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CriarPedidoUseCaseTest {

    @Mock
    private PedidoPort pedidoPort;

    private CriarPedidoUseCase criarPedidoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        criarPedidoUseCase = new CriarPedidoUseCase(pedidoPort);
    }

    @Test
    void deveCriarPedidoComSucesso() {
        // Arrange
        UUID clienteId = UUID.randomUUID();
        LocalDateTime horario = LocalDateTime.now();
        UUID produtoId = UUID.randomUUID();
        
        List<ProdutoDTO> produtos = List.of(
            ProdutoDTO.builder()
                .id(produtoId)
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
        
        Pedido pedidoEsperado = new Pedido();
        pedidoEsperado.setIdPedido(1L);
        pedidoEsperado.setCodigoPedido("P001");
        pedidoEsperado.setCliente(clienteId);
        pedidoEsperado.setProdutos(produtos);
        pedidoEsperado.setHorarioInicio(horario);
        pedidoEsperado.setSituacaoPedido(SituacaoPedido.CRIADO);
        pedidoEsperado.setValorTotalPedido(BigDecimal.valueOf(51.80));
        
        when(pedidoPort.criarPedido(any(Pedido.class))).thenReturn(pedidoEsperado);
        
        // Act
        Pedido resultado = criarPedidoUseCase.criarPedido(pedidoRequest);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(pedidoEsperado.getIdPedido(), resultado.getIdPedido());
        assertEquals(pedidoEsperado.getCodigoPedido(), resultado.getCodigoPedido());
        assertEquals(pedidoEsperado.getCliente(), resultado.getCliente());
        assertEquals(pedidoEsperado.getSituacaoPedido(), resultado.getSituacaoPedido());
        verify(pedidoPort).criarPedido(any(Pedido.class));
    }
}
