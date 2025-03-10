package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import java.util.List;

/**
 * Generic repository interface for managing entities.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface Repositorio<T> {
    /**
     * Saves an entity to the repository.
     *
     * @param entidade The entity to be saved.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void salvar(T entidade);

    /**
     * Edits an entity in the repository.
     *
     * @param entidade The entity to be edited.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void editar(T entidade);

    /**
     * Deletes an entity from the repository.
     *
     * @param id The id of the entity to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void excluir(String id);

    /**
     * Returns a list of all entities in the repository.
     *
     * @return A list of all entities.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    List<T> listarTodos();

    /**
     * Saves the entities to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void salvarEmArquivo(String arquivo);

    /**
     * Loads entities from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void carregarDeArquivo(String arquivo);
}