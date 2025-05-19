package com.techchallenge.produto.application.usecases.interactors;

import com.techchallenge.produto.application.gateway.ProdutoGateway;
import com.techchallenge.produto.application.usecases.exceptions.ProdutoExistenteException;
import com.techchallenge.produto.application.usecases.exceptions.ProdutoNotFoundException;
import com.techchallenge.produto.domain.Produto;
import com.techchallenge.produto.domain.enums.CategoriaProduto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoInteractorTest {

    @Mock
    private ProdutoGateway produtoGateway;

    private ProdutoInteractor produtoInteractor;

    @BeforeEach
    void setUp() {
        produtoInteractor = new ProdutoInteractor(produtoGateway);
    }

    @Test
    @DisplayName("Deve criar um produto quando dados são válidos")
    void deveCriarProdutoQuandoDadosValidos() {
        // Arrange
        Produto produto = new Produto(null, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), CategoriaProduto.LANCHE);
        when(produtoGateway.obterProdutoPorNome(produto.getNome())).thenReturn(Optional.empty());
        when(produtoGateway.criarProduto(produto)).thenReturn(produto);

        // Act
        Produto produtoCriado = produtoInteractor.criarProduto(produto);

        // Assert
        assertNotNull(produtoCriado);
        assertEquals(produto.getNome(), produtoCriado.getNome());
        verify(produtoGateway, times(1)).criarProduto(produto);
    }

    @Test
    @DisplayName("Deve lançar exceção quando nome do produto já existe")
    void deveLancarExcecaoQuandoNomeProdutoJaExiste() {
        // Arrange
        Produto produto = new Produto(null, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), CategoriaProduto.LANCHE);
        when(produtoGateway.obterProdutoPorNome(produto.getNome())).thenReturn(Optional.of(produto));

        // Act & Assert
        assertThrows(ProdutoExistenteException.class, () -> {
            produtoInteractor.criarProduto(produto);
        });
        verify(produtoGateway, never()).criarProduto(any(Produto.class));
    }

    @Test
    @DisplayName("Deve obter produto quando ID existe")
    void deveObterProdutoQuandoIdExiste() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto(id, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), CategoriaProduto.LANCHE);
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.of(produto));

        // Act
        Produto produtoObtido = produtoInteractor.obterProduto(id);

        // Assert
        assertNotNull(produtoObtido);
        assertEquals(produto.getNome(), produtoObtido.getNome());
        verify(produtoGateway, times(1)).obterProdutoPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do produto não existe")
    void deveLancarExcecaoQuandoIdProdutoNaoExiste() {
        // Arrange
        Long id = 1L;
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoInteractor.obterProduto(id);
        });
    }

    @Test
    @DisplayName("Deve listar produtos por categoria")
    void deveListarProdutosPorCategoria() {
        // Arrange
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        List<Produto> produtosEsperados = Arrays.asList(
            new Produto(1L, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), categoria),
            new Produto(2L, "X-Bacon", "Hambúrguer com bacon", new BigDecimal("27.90"), categoria)
        );
        when(produtoGateway.listarProdutosPorCategoria(categoria)).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtosObtidos = produtoInteractor.listarProdutosPorCategoria(categoria);

        // Assert
        assertNotNull(produtosObtidos);
        assertEquals(2, produtosObtidos.size());
        assertEquals(produtosEsperados.get(0).getNome(), produtosObtidos.get(0).getNome());
        verify(produtoGateway, times(1)).listarProdutosPorCategoria(categoria);
    }

    @Test
    @DisplayName("Deve atualizar produto quando ID existe")
    void deveAtualizarProdutoQuandoIdExiste() {
        // Arrange
        Long id = 1L;
        Produto produtoExistente = new Produto(id, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), CategoriaProduto.LANCHE);
        Produto produtoAtualizado = new Produto(id, "Hambúrguer Especial", "Hambúrguer com queijo cheddar", new BigDecimal("29.90"), CategoriaProduto.LANCHE);
        
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.of(produtoExistente));
        when(produtoGateway.atualizarProduto(produtoAtualizado)).thenReturn(produtoAtualizado);

        // Act
        Produto resultado = produtoInteractor.atualizarProduto(produtoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoAtualizado.getNome(), resultado.getNome());
        assertEquals(produtoAtualizado.getPreco(), resultado.getPreco());
        verify(produtoGateway, times(1)).atualizarProduto(produtoAtualizado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar quando ID não existe")
    void deveLancarExcecaoAoAtualizarQuandoIdNaoExiste() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto(id, "Hambúrguer Especial", "Hambúrguer com queijo cheddar", new BigDecimal("29.90"), CategoriaProduto.LANCHE);
        
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoInteractor.atualizarProduto(produto);
        });
        verify(produtoGateway, never()).atualizarProduto(any(Produto.class));
    }

    @Test
    @DisplayName("Deve remover produto quando ID existe")
    void deveRemoverProdutoQuandoIdExiste() {
        // Arrange
        Long id = 1L;
        Produto produto = new Produto(id, "Hambúrguer", "Hambúrguer com queijo", new BigDecimal("25.90"), CategoriaProduto.LANCHE);
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoGateway).removerProduto(id);

        // Act
        produtoInteractor.removerProduto(id);

        // Assert
        verify(produtoGateway, times(1)).removerProduto(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao remover quando ID não existe")
    void deveLancarExcecaoAoRemoverQuandoIdNaoExiste() {
        // Arrange
        Long id = 1L;
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoNotFoundException.class, () -> {
            produtoInteractor.removerProduto(id);
        });
        verify(produtoGateway, never()).removerProduto(id);
    }
}
