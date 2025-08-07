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
    @DisplayName("Deve alterar produto quando dados são válidos")
    void deveAlterarProdutoQuandoDadosValidos() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produtoExistente = new Produto();
        produtoExistente.setId(id);
        produtoExistente.setNomeProduto("Hambúrguer");
        produtoExistente.setDescricao("Hambúrguer com queijo");
        produtoExistente.setValor(new BigDecimal("25.90"));
        produtoExistente.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        Produto produtoAlterado = new Produto();
        produtoAlterado.setId(id);
        produtoAlterado.setNomeProduto("Hambúrguer Especial");
        produtoAlterado.setDescricao("Hambúrguer com queijo cheddar");
        produtoAlterado.setValor(new BigDecimal("29.90"));
        produtoAlterado.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        when(produtoGateway.obterPorId(id)).thenReturn(Optional.of(produtoExistente));
        when(produtoGateway.alterarProduto(produtoAlterado)).thenReturn(produtoAlterado);

        // Act
        Produto resultado = produtoInteractor.alterarProduto(produtoAlterado);

        // Assert
        assertNotNull(resultado);
        assertEquals("Hambúrguer Especial", resultado.getNomeProduto());
        assertEquals(new BigDecimal("29.90"), resultado.getValor());
        verify(produtoGateway, times(1)).alterarProduto(produtoAlterado);
    }

    @Test
    @DisplayName("Deve lançar exceção ao alterar produto quando ID não existe")
    void deveLancarExcecaoAoAlterarProdutoQuandoIdNaoExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNomeProduto("Hambúrguer Especial");
        when(produtoGateway.obterPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoInexistenteException.class, () -> {
            produtoInteractor.alterarProduto(produto);
        });
        verify(produtoGateway, never()).alterarProduto(any(Produto.class));
    }

    @Test
    @DisplayName("Deve listar produtos por categoria")
    void deveListarProdutosPorCategoria() {
        // Arrange
        String categoria = "LANCHE";
        Produto produto1 = new Produto();
        produto1.setId(UUID.randomUUID());
        produto1.setNomeProduto("Hambúrguer");
        produto1.setDescricao("Hambúrguer com queijo");
        produto1.setValor(new BigDecimal("25.90"));
        produto1.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        Produto produto2 = new Produto();
        produto2.setId(UUID.randomUUID());
        produto2.setNomeProduto("X-Bacon");
        produto2.setDescricao("Hambúrguer com bacon");
        produto2.setValor(new BigDecimal("27.90"));
        produto2.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        List<Produto> produtosEsperados = Arrays.asList(produto1, produto2);
        when(produtoGateway.listarProdutosPorCategoria(categoria)).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtosObtidos = produtoInteractor.obterProdutosPorCategoria(categoria);

        // Assert
        assertNotNull(produtosObtidos);
        assertEquals(2, produtosObtidos.size());
        assertEquals(produtosEsperados.get(0).getNomeProduto(), produtosObtidos.get(0).getNomeProduto());
        verify(produtoGateway, times(1)).listarProdutosPorCategoria(categoria);
    }

    @Test
    @DisplayName("Deve deletar produto quando ID existe")
    void deveDeletarProdutoQuandoIdExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        Produto produto = new Produto();
        produto.setId(id);
        produto.setNomeProduto("Hambúrguer");
        produto.setDescricao("Hambúrguer com queijo");
        produto.setValor(new BigDecimal("25.90"));
        produto.setCategoriaProduto(CategoriaProduto.LANCHE);
        
        when(produtoGateway.obterPorId(id)).thenReturn(Optional.of(produto));
        doNothing().when(produtoGateway).deletarProduto(id);

        // Act
        produtoInteractor.deletarProduto(id);

        // Assert
        verify(produtoGateway, times(1)).deletarProduto(id);
    }

    @Test
    @DisplayName("Deve lançar exceção ao deletar quando ID não existe")
    void deveLancarExcecaoAoDeletarQuandoIdNaoExiste() {
        // Arrange
        UUID id = UUID.randomUUID();
        when(produtoGateway.obterPorId(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ProdutoInexistenteException.class, () -> {
            produtoInteractor.deletarProduto(id);
        });
        verify(produtoGateway, never()).deletarProduto(id);
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
        when(produtoGateway.listarProdutos()).thenReturn(produtosEsperados);

        // Act
        List<Produto> produtosObtidos = produtoInteractor.obterProdutos();

        // Assert
        assertNotNull(produtosObtidos);
        assertEquals(2, produtosObtidos.size());
        verify(produtoGateway, times(1)).listarProdutos();
    }
}
