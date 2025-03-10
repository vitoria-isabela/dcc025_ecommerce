package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RoupaTest {

    private Roupa roupa;

    @BeforeEach
    public void setUp() {
        roupa = new Roupa("002", "Camiseta", 50.0, "M", "Azul", "Algodão", "Lote123");
    }

    @Test
    public void givenRoupa_whenGetTamanho_thenReturnCorrectTamanho() {
        assertEquals("M", roupa.getTamanho());
    }

    @Test
    public void givenRoupa_whenGetCor_thenReturnCorrectCor() {
        assertEquals("Azul", roupa.getCor());
    }

    @Test
    public void givenRoupa_whenGetMaterial_thenReturnCorrectMaterial() {
        assertEquals("Algodão", roupa.getMaterial());
    }

    @Test
    public void givenRoupa_whenGetLote_thenReturnCorrectLote() {
        assertEquals("Lote123", roupa.getLote());
    }

    @Test
    public void givenRoupa_whenGetTipo_thenReturnCorrectTipo() {
        assertEquals("Roupa", roupa.getTipo());
    }
}