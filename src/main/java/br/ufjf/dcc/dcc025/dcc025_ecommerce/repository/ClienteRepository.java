package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing Cliente objects.
 * Implements the ClienteRepositoryInterface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class ClienteRepository implements ClienteRepositoryInterface {
    private static ClienteRepository instance;
    private final List<Cliente> clientes = new ArrayList<>();

    private ClienteRepository() {}

    /**
     * Returns the singleton instance of ClienteRepository.
     *
     * @return The singleton instance of ClienteRepository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static ClienteRepository getInstance() {
        if (instance == null) {
            instance = new ClienteRepository();
        }
        return instance;
    }

    /**
     * Saves a Cliente object to the repository.
     *
     * @param cliente The Cliente object to be saved.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvar(Cliente cliente) {
        if (buscarPorCpf(cliente.getCpf()) == null) {
          clientes.add(cliente);
        }
    }

    /**
     * Edits an existing Cliente object in the repository.
     *
     * @param cliente The Cliente object with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void editar(Cliente cliente) {
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getCpf().equals(cliente.getCpf())) {
                clientes.set(i, cliente);
                return;
            }
        }
        throw new IllegalArgumentException("Cliente não encontrado para edição.");
    }

    /**
     * Deletes a Cliente object from the repository based on the CPF.
     *
     * @param cpf The CPF of the Cliente object to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluir(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }

    /**
     * Excludes a Cliente object from the repository by CPF.
     *
     * @param cpf The CPF of the Cliente to exclude.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluirByCpf(String cpf) {
        clientes.removeIf(cliente -> cliente.getCpf().equals(cpf));
    }
    
    /**
     * Returns a list of all Cliente objects in the repository.
     *
     * @return A list of all Cliente objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<Cliente> listarTodos() {
        return new ArrayList<>(clientes);
    }

    /**
     * Searches for a Cliente object by CPF.
     *
     * @param cpf The CPF to search for.
     * @return The Cliente object with the corresponding CPF, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Cliente buscarPorCpf(String cpf) {
        return clientes.stream()
                .filter(c -> c.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }

    /**
     * Searches for a Cliente object by email.
     *
     * @param email The email to search for.
     * @return The Cliente object with the corresponding email, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Cliente buscarPorEmail(String email) {
        return clientes.stream()
            .filter(c -> c.getEmail().equalsIgnoreCase(email))
            .findFirst()
            .orElse(null);
    }

    /**
     * Saves the Cliente objects to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void salvarEmArquivo(String arquivo) {
        Persistencia.salvarEmArquivo(arquivo, clientes);
    }

    /**
     * Loads Cliente objects from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void carregarDeArquivo(String arquivo) {
        Type tipo = new TypeToken<List<Cliente>>() {}.getType();
        List<Cliente> dados = Persistencia.carregarDeArquivo(arquivo, tipo);
        if (dados != null) {
            clientes.clear();
            clientes.addAll(dados);
        }
    }

    /**
     * Clears all Cliente objects from the repository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void limparClientes() {
        clientes.clear();
    }
}