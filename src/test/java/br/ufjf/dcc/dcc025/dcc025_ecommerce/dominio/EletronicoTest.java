package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EletronicoTest {

    private Eletronico eletronico;

    @BeforeEach
    public void setUp() {
        eletronico = new Eletronico("001", "Notebook", 2500.0, 12, "Dell", "Intel i7, 16GB RAM");
    }

    @Test
    public void givenEletronico_whenGetGarantiaMeses_thenReturnCorrectGarantia() {
        assertEquals(12, eletronico.getGarantiaMeses());
    }

    @Test
    public void givenEletronico_whenGetMarca_thenReturnCorrectMarca() {
        assertEquals("Dell", eletronico.getMarca());
    }

    @Test
    public void givenEletronico_whenGetEspecificacoesTecnicas_thenReturnCorrectEspecificacoes() {
        assertEquals("Intel i7, 16GB RAM", eletronico.getEspecificacoesTecnicas());
    }

    @Test
    public void givenEletronico_whenGetTipo_thenReturnCorrectTipo() {
        assertEquals("Eletrônico", eletronico.getTipo());
    }
}