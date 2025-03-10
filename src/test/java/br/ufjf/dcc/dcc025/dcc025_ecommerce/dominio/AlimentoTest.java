package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class AlimentoTest {

    private Alimento alimento;

    @BeforeEach
    public void setUp() {
        LocalDate dataValidade = LocalDate.of(2023, 12, 31);
        LocalDate dataFabricacao = LocalDate.of(2023, 1, 1);
        alimento = new Alimento("003", "Arroz", 10.0, dataValidade, dataFabricacao, "Lote456", false);
    }

    @Test
    public void givenAlimento_whenGetDataValidade_thenReturnCorrectDataValidade() {
        assertEquals(LocalDate.of(2023, 12, 31), alimento.getDataValidade());
    }

    @Test
    public void givenAlimento_whenGetDataFabricacao_thenReturnCorrectDataFabricacao() {
        assertEquals(LocalDate.of(2023, 1, 1), alimento.getDataFabricacao());
    }

    @Test
    public void givenAlimento_whenGetLote_thenReturnCorrectLote() {
        assertEquals("Lote456", alimento.getLote());
    }

    @Test
    public void givenAlimento_whenIsOrganico_thenReturnCorrectOrganico() {
        assertFalse(alimento.isOrganico());
    }

    @Test
    public void givenAlimento_whenGetTipo_thenReturnCorrectTipo() {
        assertEquals("Alimento", alimento.getTipo());
    }
}