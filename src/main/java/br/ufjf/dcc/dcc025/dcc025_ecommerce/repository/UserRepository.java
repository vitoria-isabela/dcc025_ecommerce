package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Iterator;

/**
 * Repository class responsible for managing User objects.
 * Implements the UserRepositoryInterface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class UserRepository implements UserRepositoryInterface {
    private static UserRepository instance;
    private final List<User> users = new ArrayList<>();

    private UserRepository() { }

    /**
     * Returns the singleton instance of UserRepository.
     *
     * @return The singleton instance of UserRepository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    /**
     * Saves a User object to the repository.
     *
     * @param user The User object to be saved.
     * @throws IllegalArgumentException if a User with the same username already exists.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvar(User user) {
        if (buscarPorUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Usuário com username já existente!");
        }
        users.add(user);
        salvarEmArquivo("users.json");
    }

    /**
     * Edits an existing User object in the repository.
     *
     * @param userAtualizado The User object with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void editar(User userAtualizado) {
        User userExistente = buscarPorUsername(userAtualizado.getUsername());
        if (userExistente != null) {
            users.remove(userExistente);
            users.add(userAtualizado);
            salvarEmArquivo("users.json");
        }

    }

    /**
     * Deletes a User object from the repository based on the username.
     *
     * @param username The username of the User object to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void excluir(String username) {
       Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove();
                break;
            }
        }
        salvarEmArquivo("users.json");
    }

    /**
     * Returns a list of all User objects in the repository.
     *
     * @return A list of all User objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<User> listarTodos() {
        return new ArrayList<>(users);
    }

    /**
     * Returns a list of User objects of a specific type.
     *
     * @param type The class of the User objects to be listed.
     * @param <T> The type of the User objects.
     * @return A list of User objects of the specified type.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public <T extends User> List<T> listarTipo(Class<T> type) {
        return users.stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    /**
     * Searches for a User object by username.
     *
     * @param username The username to search for.
     * @return The User object with the corresponding username, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public User buscarPorUsername(String username) {
        return users.stream()
                .filter(user -> user.getUsername().equalsIgnoreCase(username))
                .findFirst()
                .orElse(null);
    }

    /**
     * Performs a login attempt with the provided username and password.
     *
     * @param username The username to log in with.
     * @param password The password to log in with.
     * @return The User object if the login is successful, or null if not.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public User login(String username, String password) {
        User user = buscarPorUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * Saves the User objects to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvarEmArquivo(String arquivo) {
        Persistencia.salvarEmArquivo(arquivo, users);
    }

    /**
     * Loads User objects from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void carregarDeArquivo(String arquivo) {
        Type tipo = new TypeToken<List<User>>() {}.getType();
        List<User> dados = Persistencia.carregarDeArquivo(arquivo, tipo);
        if (dados != null) {
            users.clear();
            users.addAll(dados);
        }
    }

    /**
     * Clears all User objects from the repository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void limparUsers() {
        users.clear();
    }
}