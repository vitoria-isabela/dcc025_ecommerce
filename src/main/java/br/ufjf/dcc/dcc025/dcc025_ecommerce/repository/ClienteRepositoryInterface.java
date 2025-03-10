package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;

/**
 * Interface for managing Cliente objects.
 * Extends the Repositorio interface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface ClienteRepositoryInterface extends Repositorio<Cliente> {
    /**
     * Searches for a Cliente object by CPF.
     *
     * @param cpf The CPF to search for.
     * @return The Cliente object with the corresponding CPF, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Cliente buscarPorCpf(String cpf);

    /**
     * Excludes a Cliente object by CPF.
     *
     * @param cpf The CPF of the Cliente to exclude.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void excluirByCpf(String cpf);

    /**
     * Searches for a Cliente object by email.
     *
     * @param email The email to search for.
     * @return The Cliente object with the corresponding email, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    Cliente buscarPorEmail(String email);
}