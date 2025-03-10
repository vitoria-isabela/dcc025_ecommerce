package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing Produto objects.
 * Implements the ProdutoRepositoryInterface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class ProdutoRepository implements ProdutoRepositoryInterface {
    private static ProdutoRepository instance;
    private final List<Produto> produtos = new ArrayList<>();

    public ProdutoRepository() {}

    /**
     * Returns the singleton instance of ProdutoRepository.
     *
     * @return The singleton instance of ProdutoRepository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static ProdutoRepository getInstance() {
        if (instance == null) {
            instance = new ProdutoRepository();
        }
        return instance;
    }

    /**
     * Saves a Produto object to the repository.
     *
     * @param produto The Produto object to be saved.
     * @throws IllegalArgumentException if a Produto with the same code already exists.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvar(Produto produto) {
        if (buscarPorCodigo(produto.getCodigo()) != null) {
            throw new IllegalArgumentException("Produto com código já existente!");
        }
        produtos.add(produto);
    }

    /**
     * Edits an existing Produto object in the repository.
     *
     * @param produto The Produto object with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void editar(Produto produto) {
        for (int i = 0; i < produtos.size(); i++) {
            if (produtos.get(i).getCodigo().equals(produto.getCodigo())) {
                produtos.set(i, produto);
                return;
            }
        }
        throw new IllegalArgumentException("Produto não encontrado para edição.");
    }

    /**
     * Deletes a Produto object from the repository based on the code.
     *
     * @param codigo The code of the Produto object to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluir(String codigo) {
        produtos.removeIf(produto -> produto.getCodigo().equals(codigo));
    }

    /**
     * Returns a list of all Produto objects in the repository.
     *
     * @return A list of all Produto objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<Produto> listarTodos() {
        return new ArrayList<>(produtos);
    }

    /**
     * Searches for a Produto object by code.
     *
     * @param codigo The code to search for.
     * @return The Produto object with the corresponding code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Produto buscarPorCodigo(String codigo) {
        return produtos.stream()
                .filter(p -> p.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

     /**
      * Busca por um objeto Produto por ID.
      *
      * @param id O ID a ser buscado.
      * @return O objeto Produto com o ID correspondente, ou null se não encontrado.
      */
    @Override
    public Produto buscarPorId(String id) {
        return produtos.stream()
            .filter(p -> p.getCodigo().equals(id))
            .findFirst()
            .orElse(null);
    }

    /**
     * Saves the Produto objects to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void salvarEmArquivo(String arquivo) {
        Persistencia.salvarEmArquivo(arquivo, produtos);
    }

    /**
     * Loads Produto objects from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void carregarDeArquivo(String arquivo) {
        Type tipo = new TypeToken<List<Produto>>() {}.getType();
        List<Produto> dados = Persistencia.carregarDeArquivo(arquivo, tipo);
        if (dados != null) {
            produtos.clear();
            produtos.addAll(dados);
        }
    }

    /**
     * Clears all Produto objects from the repository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void limparProdutos() {
        produtos.clear();
    }
}