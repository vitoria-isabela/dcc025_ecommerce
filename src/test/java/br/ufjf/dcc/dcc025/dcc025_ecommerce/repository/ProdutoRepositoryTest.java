package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Eletronico;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Roupa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ProdutoRepositoryTest {

    private ProdutoRepository produtoRepository;

    @BeforeEach
    public void setUp() {
        produtoRepository = ProdutoRepository.getInstance();
        produtoRepository.limparProdutos(); 
    }

    @Test
    public void givenNewProduto_whenSalvar_thenProdutoIsAdded() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);
        assertEquals(1, produtoRepository.listarTodos().size());
    }

    @Test
    public void givenExistingProduto_whenSalvar_thenThrowsException() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);
        assertThrows(IllegalArgumentException.class, () -> produtoRepository.salvar(produto));
    }

    @Test
    public void givenProdutoList_whenListarTodos_thenReturnsAllProdutos() {
        Produto produto1 = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        Produto produto2 = new Roupa("P002", "Camiseta", 50.0, "M", "Azul", "Algodão", "L001");
        produtoRepository.salvar(produto1);
        produtoRepository.salvar(produto2);
        assertEquals(2, produtoRepository.listarTodos().size());
    }

    @Test
    public void givenProdutoCodigo_whenBuscarPorCodigo_thenReturnsProduto() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);
        Produto foundProduto = produtoRepository.buscarPorCodigo("P001");
        assertEquals(produto, foundProduto);
    }

    @Test
    public void givenNonExistingProdutoCodigo_whenBuscarPorCodigo_thenReturnsNull() {
        Produto foundProduto = produtoRepository.buscarPorCodigo("P001");
        assertNull(foundProduto);
    }

    @Test
    public void givenProduto_whenEditar_thenProdutoIsUpdated() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);

        Produto produtoAtualizado = new Eletronico("P001", "Ultrabook", 3000.0, 24, "HP", "Intel i9, 32GB RAM");
        produtoRepository.editar(produtoAtualizado);

        Produto foundProduto = produtoRepository.buscarPorCodigo("P001");
        assertEquals("Ultrabook", foundProduto.getNome());
    }

    @Test
    public void givenNonExistingProduto_whenEditar_thenThrowsException() {
        Produto produtoAtualizado = new Eletronico("P001", "Ultrabook", 3000.0, 24, "HP", "Intel i9, 32GB RAM");
        assertThrows(IllegalArgumentException.class, () -> produtoRepository.editar(produtoAtualizado));
    }

    @Test
    public void givenProdutoCodigo_whenExcluir_thenProdutoIsRemoved() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);
        produtoRepository.excluir("P001");
        assertNull(produtoRepository.buscarPorCodigo("P001"));
    }

    @Test
    public void givenEmptyRepository_whenListarTodos_thenReturnsEmptyList() {
        List<Produto> produtos = produtoRepository.listarTodos();
        assertTrue(produtos.isEmpty());
    }

    @Test
    public void givenMultipleProdutos_whenListarTodos_thenReturnsAllProdutos() {
        Produto produto1 = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        Produto produto2 = new Roupa("P002", "Camiseta", 50.0, "M", "Azul", "Algodão", "L001");
        produtoRepository.salvar(produto1);
        produtoRepository.salvar(produto2);

        List<Produto> produtos = produtoRepository.listarTodos();
        assertEquals(2, produtos.size());
        assertTrue(produtos.contains(produto1));
        assertTrue(produtos.contains(produto2));
    }

    @Test
    public void whenLimparProdutos_thenRepositoryIsEmpty() {
        Produto produto1 = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto1);
        produtoRepository.limparProdutos();
        assertTrue(produtoRepository.listarTodos().isEmpty());
    }

     @Test
    public void givenProdutoCodigo_whenBuscarPorId_thenReturnsProduto() {
        Produto produto = new Eletronico("P001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
        produtoRepository.salvar(produto);
        Produto foundProduto = produtoRepository.buscarPorId("P001");
        assertEquals(produto, foundProduto);
    }

    @Test
    public void givenNonExistingProdutoCodigo_whenBuscarPorId_thenReturnsNull() {
        Produto foundProduto = produtoRepository.buscarPorId("P001");
        assertNull(foundProduto);
    }
}