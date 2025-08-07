package com.techchallenge.produto.application.usecases.interactors;

import com.techchallenge.produto.application.exceptions.ProdutoInexistenteException;
import com.techchallenge.produto.application.gateway.ProdutoGateway;
import com.techchallenge.produto.application.interactors.ProdutoInteractor;
import com.techchallenge.produto.domain.CategoriaProduto;
import com.techchallenge.produto.domain.Produto;
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
import java.util.UUID;

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
        Produto produto = new Produto();
        produto.setNomeProduto("Hambúrguer");
        produto.setDescricao("Hambúrguer com queijo");
        produto.setValor(new BigDecimal("25.90"));
        produto.setCategoriaProduto(CategoriaProduto.LANCHE);
        when(produtoGateway.criarProduto(produto)).thenReturn(produto);

        // Act
        Produto produtoCriado = produtoInteractor.criarProduto(produto);

        // Assert
        assertNotNull(produtoCriado);
        assertEquals(produto.getNomeProduto(), produtoCriado.getNomeProduto());
        verify(produtoGateway, times(1)).criarProduto(produto);
    }

    @Test
    @DisplayName("Deve obter produto quando ID existe")
    void deveObterProdutoQuandoIdExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNomeProduto("Hambúrguer");
        produto.setDescricao("Hambúrguer com queijo");
        produto.setValor(new BigDecimal("25.90"));
        produto.setCategoriaProduto(CategoriaProduto.LANCHE);
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.of(produto));

        // Act
        Produto produtoObtido = produtoInteractor.obterProdutoPorId(id);

        // Assert
        assertNotNull(produtoObtido);
        assertEquals(produto.getNomeProduto(), produtoObtido.getNomeProduto());
        verify(produtoGateway, times(1)).obterProdutoPorId(id);
    }

    @Test
    @DisplayName("Deve lançar exceção quando ID do produto não existe")
    void deveLancarExcecaoQuandoIdProdutoNaoExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoInexistenteException.class, () -> {
            produtoInteractor.obterProdutoPorId(id);
        });
    }

    @Test
    @DisplayName("Deve listar produtos por categoria")
    void deveListarProdutosPorCategoria() {
        // Arrange
        CategoriaProduto categoria = CategoriaProduto.LANCHE;
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setNomeProduto("Hambúrguer");
        produto1.setDescricao("Hambúrguer com queijo");
        produto1.setValor(new BigDecimal("25.90"));
        produto1.setCategoriaProduto(categoria);
        
        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setNomeProduto("X-Bacon");
        produto2.setDescricao("Hambúrguer com bacon");
        produto2.setValor(new BigDecimal("27.90"));
        produto2.setCategoriaProduto(categoria);
        
        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
        when(produtoGateway.listarPorCategoria(categoria)).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtosObtidos = produtoInteractor.obterProdutosPorCategoria(categoria);

        // Assert
        assertNotNull(produtosObtidos);
        assertEquals(2, produtosObtidos.size());
        assertEquals(produtosEsperados.get(0).getNomeProduto(), produtosObtidos.get(0).getNomeProduto());
        verify(produtoGateway, times(1)).listarPorCategoria(categoria);
    }

    @Test
    @DisplayName("Deve atualizar produto quando ID existe")
    void deveAtualizarProdutoQuandoIdExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produtoExistente = new Produto();
        produtoExistente.setId(id);
        produtoExistente.setNomeProduto("Hambúrguer");
        produtoExistente.setDescricao("Hambúrguer com queijo");
        produtoExistente.setValor(new BigDecimal("25.90"));
        produtoExistente.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        Produto produtoAtualizado = new Produto();
        produtoAtualizado.setId(id);
        produtoAtualizado.setNomeProduto("Hambúrguer Especial");
        produtoAtualizado.setDescricao("Hambúrguer com queijo cheddar");
        produtoAtualizado.setValor(new BigDecimal("29.90"));
        produtoAtualizado.setCategoriaProduto(CategoriaProduto.LANCHE);

        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.of(produtoExistente));
        when(produtoGateway.atualizarProduto(produtoAtualizado)).thenReturn(produtoAtualizado);

        // Act
        Produto resultado = produtoInteractor.atualizarProduto(produtoAtualizado);

        // Assert
        assertNotNull(resultado);
        assertEquals(produtoAtualizado.getNomeProduto(), resultado.getNomeProduto());
        assertEquals(produtoAtualizado.getValor(), resultado.getValor());
        verify(produtoGateway, times(1)).atualizarProduto(produtoAtualizado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao atualizar quando ID não existe")
    void deveLancarExcecaoAoAtualizarQuandoIdNaoExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNomeProduto("Hambúrguer Especial");
        produto.setDescricao("Hambúrguer com queijo cheddar");
        produto.setValor(new BigDecimal("29.90"));
        produto.setCategoriaProduto(CategoriaProduto.LANCHE);

        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoInexistenteException.class, () -> {
            produtoInteractor.atualizarProduto(produto);
        });
        verify(produtoGateway, never()).atualizarProduto(any(Produto.class));
    }

    @Test
    @DisplayName("Deve remover produto quando ID existe")
    void deveRemoverProdutoQuandoIdExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNomeProduto("Hambúrguer");
        produto.setDescricao("Hambúrguer com queijo");
        produto.setValor(new BigDecimal("25.90"));
        produto.setCategoriaProduto(CategoriaProduto.LANCHE);
        
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
        UUID id = UUID.randomUUID();
        when(produtoGateway.obterProdutoPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoInexistenteException.class, () -> {
            produtoInteractor.removerProduto(id);
        });
        verify(produtoGateway, never()).removerProduto(id);
    }

    @Test
    @DisplayName("Deve listar todos os produtos")
    void deveListarTodosProdutos() {
        // Arrange
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setNomeProduto("Hambúrguer");
        produto1.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setNomeProduto("Refrigerante");
        produto2.setCategoriaProduto(CategoriaProduto.BEBIDA);
        
        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
        when(produtoGateway.listarTodos()).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtosObtidos = produtoInteractor.listarTodosProdutos();

        // Assert
        assertNotNull(produtosObtidos);
        assertEquals(2, produtosObtidos.size());
        verify(produtoGateway, times(1)).listarTodos();
    }
}
