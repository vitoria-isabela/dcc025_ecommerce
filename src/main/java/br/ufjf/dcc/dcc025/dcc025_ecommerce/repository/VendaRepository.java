package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repository class responsible for managing Venda objects.
 * Implements the VendaRepositoryInterface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class VendaRepository implements VendaRepositoryInterface {
    private static VendaRepository instance;
    private final List<Venda> vendas = new ArrayList<>();

    private VendaRepository() {}

    /**
     * Returns the singleton instance of VendaRepository.
     *
     * @return The singleton instance of VendaRepository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static VendaRepository getInstance() {
        if (instance == null) {
            instance = new VendaRepository();
        }
        return instance;
    }

    /**
     * Saves a Venda object to the repository.
     *
     * @param venda The Venda object to be saved.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvar(Venda venda) {
        vendas.add(venda);
    }

    /**
     * Edits an existing Venda object in the repository.
     *
     * @param vendaAtualizada The Venda object with updated information.
     * @throws IllegalArgumentException if a Venda with the specified ID is not found for editing.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void editar(Venda vendaAtualizada) {
        for (int i = 0; i < vendas.size(); i++) {
            if (vendas.get(i).getId().equals(vendaAtualizada.getId())) {
                vendas.set(i, vendaAtualizada);
                return;
            }
        }
        throw new IllegalArgumentException("Venda não encontrada para edição com ID: " + vendaAtualizada.getId());
    }

    /**
     * Deletes a Venda object from the repository based on the ID.
     *
     * @param id The ID of the Venda object to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void excluir(String id) {
        vendas.removeIf(venda -> venda.getId().equals(id));
    }

    /**
     * Returns a list of all Venda objects in the repository.
     *
     * @return A list of all Venda objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<Venda> listarTodos() {
        return new ArrayList<>(vendas);
    }

    /**
     * Searches for a Venda object by ID.
     *
     * @param id The ID to search for.
     * @return The Venda object with the corresponding ID, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Venda buscarPorId(String id) {
        return vendas.stream()
                .filter(v -> v.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves the Venda objects to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void salvarEmArquivo(String arquivo) {
        Persistencia.salvarEmArquivo(arquivo, vendas);
    }

    /**
     * Loads Venda objects from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void carregarDeArquivo(String arquivo) {
        Type tipo = new TypeToken<List<Venda>>() {}.getType();
        List<Venda> dados = Persistencia.carregarDeArquivo(arquivo, tipo);
        if (dados != null) {
            vendas.clear();
            vendas.addAll(dados);
        }
    }

    /**
     * Returns a list of Venda objects for a specific date.
     *
     * @param data The date to filter by.
     * @return A list of Venda objects with the specified date.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public List<Venda> listarPorData(LocalDate data) {
        return vendas.stream()
                .filter(venda -> venda.getData().equals(data))
                .collect(Collectors.toList());
    }

    /**
     * Clears all Venda objects from the repository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void limparVendas() {
        vendas.clear();
    }
}