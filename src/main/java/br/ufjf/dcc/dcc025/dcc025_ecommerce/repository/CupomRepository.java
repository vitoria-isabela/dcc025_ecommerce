package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository class responsible for managing Cupom objects.
 * Implements the CupomRepositoryInterface.
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public class CupomRepository implements CupomRepositoryInterface {
    private static CupomRepository instance;
    private final List<Cupom> cupons = new ArrayList<>();

    private CupomRepository() {}

    /**
     * Returns the singleton instance of CupomRepository.
     *
     * @return The singleton instance of CupomRepository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public static CupomRepository getInstance() {
        if (instance == null) {
            instance = new CupomRepository();
        }
        return instance;
    }

    /**
     * Saves a Cupom object to the repository.
     *
     * @param cupom The Cupom object to be saved.
     * @throws IllegalArgumentException if a Cupom with the same code already exists.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void salvar(Cupom cupom) {
        if (buscarPorCodigo(cupom.getCodigo()) != null && !editingMode) {
            throw new IllegalArgumentException("Cupom com código já existente!");
        }
        cupons.add(cupom);
        editingMode = false;
    }

    private boolean editingMode = false;

    /**
     * Edits an existing Cupom object in the repository.
     *
     * @param cupomAtualizado The Cupom object with updated information.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public void editar(Cupom cupomAtualizado) {
        editingMode = true;
        Cupom cupomExistente = buscarPorCodigo(cupomAtualizado.getCodigo());
        if (cupomExistente != null) {
            int index = cupons.indexOf(cupomExistente);
            if (index != -1) {
                cupons.set(index, cupomAtualizado);
                salvarEmArquivo("cupons.json");
            } else {
                throw new IllegalStateException("Cupom inconsistência interna: Cupom encontrado por código, mas índice não localizado.");
            }
        } else {
            throw new IllegalArgumentException("Cupom não encontrado para edição com código: " + cupomAtualizado.getCodigo());
        }
        editingMode = false;
    }

    /**
     * Deletes a Cupom object from the repository based on the code.
     *
     * @param codigo The code of the Cupom object to be deleted.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void excluir(String codigo) {
        cupons.removeIf(cupom -> cupom.getCodigo().equals(codigo));
    }

    /**
     * Returns a list of all Cupom objects in the repository.
     *
     * @return A list of all Cupom objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<Cupom> listarTodos() {
        return new ArrayList<>(cupons);
    }

    /**
     * Returns a list of all active Cupom objects in the repository.
     *
     * @return A list of all active Cupom objects.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public List<Cupom> listarAtivos() {
        return cupons.stream()
                .filter(Cupom::isAtivo)
                .toList();
    }

    /**
     * Searches for a Cupom object by code.
     *
     * @param codigo The code to search for.
     * @return The Cupom object with the corresponding code, or null if not found.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    @Override
    public Cupom buscarPorCodigo(String codigo) {
        return cupons.stream()
                .filter(c -> c.getCodigo().equals(codigo))
                .findFirst()
                .orElse(null);
    }

    /**
     * Saves the Cupom objects to a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void salvarEmArquivo(String arquivo) {
        Persistencia.salvarEmArquivo(arquivo, cupons);
    }

    /**
     * Loads Cupom objects from a JSON file.
     *
     * @param arquivo The path to the JSON file.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void carregarDeArquivo(String arquivo) {
        Type tipo = new TypeToken<List<Cupom>>() {}.getType();
        List<Cupom> dados = Persistencia.carregarDeArquivo(arquivo, tipo);
        if (dados != null) {
            cupons.clear();
            cupons.addAll(dados);
        }
    }

    /**
     * Clears all Cupom objects from the repository.
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    public void limparCupons() {
        cupons.clear();
    }
}