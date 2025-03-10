package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CupomValorMinimoTest {

    private CupomValorMinimo cupom;

    @BeforeEach
    public void setUp() {
        cupom = new CupomValorMinimo("CUPOM10", 10.0, 100.0);
    }

    @Test
    public void givenCupomValorMinimo_whenGetValorMinimo_thenReturnCorrectValorMinimo() {
        assertEquals(100.0, cupom.getValorMinimo());
    }

    @Test
    public void givenCupomValorMinimo_whenPodeSerAplicado_thenReturnTrueIfValorTotalMaiorOuIgualValorMinimo() {
        assertTrue(cupom.podeSerAplicado(100.0));
        assertFalse(cupom.podeSerAplicado(99.0));
    }
}
