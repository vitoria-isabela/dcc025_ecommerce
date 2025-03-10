package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemVendaTest {

    private ItemVenda itemVenda;
    private Produto produto;

    @BeforeEach
    public void setUp() {
        produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        itemVenda = new ItemVenda(produto, 2);
    }

    @Test
    public void givenItemVenda_whenGetProduto_thenReturnCorrectProduto() {
        assertEquals(produto, itemVenda.getProduto());
    }

    @Test
    public void givenItemVenda_whenGetQuantidade_thenReturnCorrectQuantidade() {
        assertEquals(2, itemVenda.getQuantidade());
    }

    @Test
    public void givenItemVenda_whenSetQuantidade_thenQuantidadeIsUpdated() {
        itemVenda.setQuantidade(3);
        assertEquals(3, itemVenda.getQuantidade());
    }

    @Test
    public void givenItemVenda_whenGetTotal_thenReturnCorrectTotal() {
        assertEquals(5000.0, itemVenda.getTotal());
    }

    @Test
    public void givenItemVenda_whenToString_thenReturnCorrectString() {
        assertEquals("Notebook (Quantidade: 2, Total: R$5000.0)", itemVenda.toString());
    }
}