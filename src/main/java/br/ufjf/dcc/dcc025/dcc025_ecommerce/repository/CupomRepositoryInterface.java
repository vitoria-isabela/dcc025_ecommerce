package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import java.util.List;

/**
 * Interface for managing Cupom objects.
 * Extends the Repositorio interface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface CupomRepositoryInterface extends Repositorio<Cupom> {

    /**
     * Returns a list of all active Cupom objects.
     *
     * @return A list of all active Cupom objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    List<Cupom> listarAtivos();

    /**
     * Searches for a Cupom object by code.
     *
     * @param codigo The code to search for.
     * @return The Cupom object with the corresponding code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Cupom buscarPorCodigo(String codigo);
}