package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomQuantidadeLimitada;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomValorMinimo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CupomRepositoryTest {

    private CupomRepository cupomRepository;

    @BeforeEach
    void setUp() {
        cupomRepository = CupomRepository.getInstance();
        cupomRepository.limparCupons();
    }

    @Test
    void givenNewCupom_whenSalvar_thenCupomIsAdded() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom);
        assertEquals(1, cupomRepository.listarTodos().size());
    }

    @Test
    void givenExistingCupom_whenSalvar_thenThrowsException() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom);
        assertThrows(IllegalArgumentException.class, () -> cupomRepository.salvar(cupom));
    }

    @Test
    void givenCupomList_whenListarTodos_thenReturnsAllCupons() {
        Cupom cupom1 = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        Cupom cupom2 = new CupomValorMinimo("CUPOM20", 20.0, 100.0);
        cupomRepository.salvar(cupom1);
        cupomRepository.salvar(cupom2);
        assertEquals(2, cupomRepository.listarTodos().size());
    }

    @Test
    void givenCupomCodigo_whenBuscarPorCodigo_thenReturnsCupom() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom);
        Cupom foundCupom = cupomRepository.buscarPorCodigo("CUPOM10");
        assertEquals(cupom, foundCupom);
    }

    @Test
    void givenNonExistingCupomCodigo_whenBuscarPorCodigo_thenReturnsNull() {
        Cupom foundCupom = cupomRepository.buscarPorCodigo("CUPOM10");
        assertNull(foundCupom);
    }

    @Test
    void givenCupom_whenEditar_thenCupomIsUpdated_quantidadeLimitada() {
        CupomQuantidadeLimitada cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom);

        CupomQuantidadeLimitada cupomAtualizado = new CupomQuantidadeLimitada("CUPOM10", 15.0, 10);
        cupomAtualizado.setAtivo(false);
        cupomRepository.editar(cupomAtualizado);

        Cupom foundCupom = cupomRepository.buscarPorCodigo("CUPOM10");
        assertTrue(foundCupom instanceof CupomQuantidadeLimitada);
        assertEquals(15.0, foundCupom.getPercentualDesconto());
        assertFalse(foundCupom.isAtivo());
        assertEquals(10, ((CupomQuantidadeLimitada) foundCupom).getMaximoUtilizacoes());
    }

    @Test
    void givenCupom_whenEditar_thenCupomIsUpdated_valorMinimo() {
        CupomValorMinimo cupom = new CupomValorMinimo("CUPOM10", 10.0, 50.0);
        cupomRepository.salvar(cupom);

        CupomValorMinimo cupomAtualizado = new CupomValorMinimo("CUPOM10", 15.0, 100.0);
        cupomAtualizado.setAtivo(false);
        cupomRepository.editar(cupomAtualizado);

        Cupom foundCupom = cupomRepository.buscarPorCodigo("CUPOM10");
        assertTrue(foundCupom instanceof CupomValorMinimo);
        assertEquals(15.0, foundCupom.getPercentualDesconto());
        assertFalse(foundCupom.isAtivo());
        assertEquals(100.0, ((CupomValorMinimo) foundCupom).getValorMinimo());
    }

    @Test
    void givenNonExistingCupom_whenEditar_thenThrowsException() {
        CupomQuantidadeLimitada cupomAtualizado = new CupomQuantidadeLimitada("CUPOM10", 15.0, 10);
        assertThrows(IllegalArgumentException.class, () -> cupomRepository.editar(cupomAtualizado));
    }

    @Test
    void givenCupomCodigo_whenExcluir_thenCupomIsRemoved() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom);
        cupomRepository.excluir("CUPOM10");
        assertNull(cupomRepository.buscarPorCodigo("CUPOM10"));
    }

    @Test
    void givenEmptyRepository_whenListarTodos_thenReturnsEmptyList() {
        List<Cupom> cupons = cupomRepository.listarTodos();
        assertTrue(cupons.isEmpty());
    }

    @Test
    void givenMultipleCupons_whenListarTodos_thenReturnsAllCupons() {
        Cupom cupom1 = new CupomQuantidadeLimitada("CUPON10",10.0,5);
        Cupom cupom2 = new CupomValorMinimo("CUPON20",20.0,100.0);
        cupomRepository.salvar(cupom1);
        cupomRepository.salvar(cupom2);

        List<Cupom> cupons = cupomRepository.listarTodos();
        assertEquals(2, cupons.size());
        assertTrue(cupons.contains(cupom1));
        assertTrue(cupons.contains(cupom2));
    }

    @Test
    void whenLimparCupons_thenRepositoryIsEmpty() {
        Cupom cupom1 = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupomRepository.salvar(cupom1);
        cupomRepository.limparCupons();
        assertTrue(cupomRepository.listarTodos().isEmpty());
    }

    @Test
    void givenActiveCupons_whenListarAtivos_thenReturnsOnlyActiveCupons() {
        Cupom cupom1 = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupom1.setAtivo(true);
        Cupom cupom2 = new CupomValorMinimo("CUPOM20", 20.0, 100.0);
        cupom2.setAtivo(false);
        cupomRepository.salvar(cupom1);
        cupomRepository.salvar(cupom2);

        List<Cupom> activeCupons = cupomRepository.listarAtivos();
        assertEquals(1, activeCupons.size());
        assertTrue(activeCupons.contains(cupom1));
        assertFalse(activeCupons.contains(cupom2));
    }

    @Test
    void givenNoActiveCupons_whenListarAtivos_thenReturnsEmptyList() {
        Cupom cupom1 = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupom1.setAtivo(false);
        Cupom cupom2 = new CupomValorMinimo("CUPOM20", 20.0, 100.0);
        cupom2.setAtivo(false);
        cupomRepository.salvar(cupom1);
        cupomRepository.salvar(cupom2);

        List<Cupom> activeCupons = cupomRepository.listarAtivos();
        assertTrue(activeCupons.isEmpty());
    }

    @Test
    void givenNonExistingCupom_whenExcluir_thenNoExceptionIsThrown() {
        assertDoesNotThrow(() -> cupomRepository.excluir("NON_EXISTENT_COUPON"));
    }
}