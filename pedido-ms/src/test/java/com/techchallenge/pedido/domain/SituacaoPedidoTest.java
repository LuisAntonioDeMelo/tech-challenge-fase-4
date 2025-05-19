package com.techchallenge.pedido.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class SituacaoPedidoTest {

    @Test
    void deveRetornarSituacaoPorNome() {
        // Act
        SituacaoPedido situacao = SituacaoPedido.obter("CRIADO");
        
        // Assert
        assertEquals(SituacaoPedido.CRIADO, situacao);
    }

    @Test
    void deveRetornarSituacaoPorNomeCaseInsensitive() {
        // Act
        SituacaoPedido situacao = SituacaoPedido.obter("em_preparacao");
        
        // Assert
        assertEquals(SituacaoPedido.EM_PREPARACAO, situacao);
    }

    @Test
    void deveLancarExcecaoParaSituacaoInvalida() {
        // Act
        Executable executable = () -> SituacaoPedido.obter("INVALIDO");
        
        // Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
        assertEquals("Situação não existe : INVALIDO", exception.getMessage());
    }

    @Test
    void deveRetornarEstrategiaPedidoCriado() {
        // Act
        String mensagem = SituacaoPedido.CRIADO.getStrategy().notificar(new Pedido());
        
        // Assert
        assertEquals("Pedido Criado", mensagem);
    }

    @Test
    void deveRetornarEstrategiaPedidoEmPreparacao() {
        // Act
        String mensagem = SituacaoPedido.EM_PREPARACAO.getStrategy().notificar(new Pedido());
        
        // Assert
        assertEquals("Pedido está em preparação", mensagem);
    }

    @Test
    void deveRetornarEstrategiaPedidoPronto() {
        // Act
        String mensagem = SituacaoPedido.PRONTO.getStrategy().notificar(new Pedido());
        
        // Assert
        assertEquals("Pedido está pronto para ser retirado!", mensagem);
    }
}
