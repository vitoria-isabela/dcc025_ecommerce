package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;

/**
 * Interface for managing Venda objects.
 * Extends the Repositorio interface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface VendaRepositoryInterface extends Repositorio<Venda> {

    /**
     * Searches for a Venda object by ID.
     *
     * @param id The ID to search for.
     * @return The Venda object with the corresponding ID, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Venda buscarPorId(String id);
}