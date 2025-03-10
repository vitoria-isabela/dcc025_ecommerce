package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProdutoTest {

    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
    }

    @Test
    public void givenProduto_whenGetCodigo_thenReturnCorrectCodigo() {
        assertEquals("001", produto.getCodigo());
    }

    @Test
    public void givenProduto_whenGetNome_thenReturnCorrectNome() {
        assertEquals("Notebook", produto.getNome());
    }

    @Test
    public void givenProduto_whenGetPreco_thenReturnCorrectPreco() {
        assertEquals(2500.0, produto.getPreco());
    }

    @Test
    public void givenProduto_whenSetNome_thenNomeIsUpdated() {
        produto.setNome("Desktop");
        assertEquals("Desktop", produto.getNome());
    }

    @Test
    public void givenProduto_whenSetPreco_thenPrecoIsUpdated() {
        produto.setPreco(3000.0);
        assertEquals(3000.0, produto.getPreco());
    }

    @Test
    public void givenProduto_whenToString_thenReturnCorrectString() {
        assertEquals("Notebook (R$2500.0)", produto.toString());
    }
}
