package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import java.util.List;

/**
 * Interface for managing User objects.
 * Extends the Repositorio interface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface UserRepositoryInterface extends Repositorio<User> {

    /**
     * Searches for a User object by username.
     *
     * @param username The username to search for.
     * @return The User object with the corresponding username, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    User buscarPorUsername(String username);

    /**
     * Performs a login attempt with the provided username and password.
     *
     * @param username The username to log in with.
     * @param password The password to log in with.
     * @return The User object if the login is successful, or null if not.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    User login(String username, String password);

    /**
     * Returns a list of User objects of a specific type.
     *
     * @param type The class of the User objects to be listed.
     * @param <T> The type of the User objects.
     * @return A list of User objects of the specified type.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    <T extends User> List<T> listarTipo(Class<T> type);
}