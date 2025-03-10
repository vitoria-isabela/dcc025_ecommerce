package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CupomQuantidadeLimitadaTest {

    private CupomQuantidadeLimitada cupom;

    @BeforeEach
    public void setUp() {
        cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
    }

    @Test
    public void givenCupomQuantidadeLimitada_whenGetMaximoUtilizacoes_thenReturnCorrectMaximo() {
        assertEquals(5, cupom.getMaximoUtilizacoes());
    }

    @Test
    public void givenCupomQuantidadeLimitada_whenPodeSerAplicado_thenReturnTrueIfUtilizacoesMenorQueMaximo() {
        assertTrue(cupom.podeSerAplicado(100.0));
        for (int i = 0; i < 5; i++) {
            cupom.aplicarDesconto(100.0);
        }
        assertFalse(cupom.podeSerAplicado(100.0));
    }

    @Test
    public void givenCupomQuantidadeLimitada_whenAplicarDesconto_thenIncrementaUtilizacoesAtuais() {
        assertTrue(cupom.podeSerAplicado(100.0));  
        cupom.aplicarDesconto(100.0);
        assertFalse(cupom.podeSerAplicado(100.0) && cupom.getMaximoUtilizacoes() == 4); 
    }
}
