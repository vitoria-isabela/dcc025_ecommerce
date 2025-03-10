package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import java.util.List;

/**
 * Interface for managing Produto objects.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface ProdutoRepositoryInterface {
    /**
     * Searches for a Produto object by code.
     *
     * @param codigo The code to search for.
     * @return The Produto object with the corresponding code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Produto buscarPorCodigo(String codigo);

    /**
     * Searches for a Produto object by ID.
     *
     * @param id The ID to search for.
     * @return The Produto object with the corresponding ID, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Produto buscarPorId(String id);

    /**
     * Saves a Produto object to the repository.
     *
     * @param produto The Produto object to be saved.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void salvar(Produto produto);

    /**
     * Returns a list of all Produto objects in the repository.
     *
     * @return A list of all Produto objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    List<Produto> listarTodos();

    /**
     * Saves data to a JSON file.
     *
     * @param string the name of the file to save to
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void salvarEmArquivo(String string);
}