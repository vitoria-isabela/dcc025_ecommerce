package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.util.List;

public class VendaTest {

    private Venda venda;
    private Produto produto;
    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDate.now());
    }

    @Test
    public void givenVenda_whenAdicionarItem_thenItemIsAdded() {
        venda.adicionarItem(produto, 2);
        List<ItemVenda> itens = venda.getItens();
        assertEquals(1, itens.size());
        assertEquals(produto, itens.get(0).getProduto());
        assertEquals(2, itens.get(0).getQuantidade());
    }

    @Test
    public void givenVenda_whenRemoverItem_thenItemIsRemoved() {
        venda.adicionarItem(produto, 2);
        venda.removerItem(produto);
        assertEquals(0, venda.getItens().size());
    }

    @Test
    public void givenVenda_whenCalcularTotal_thenReturnCorrectTotal() {
        venda.adicionarItem(produto, 2);
        assertEquals(5000.0, venda.getTotalSemDesconto());
    }

    @Test
    public void givenVendaComCupom_whenCalcularTotal_thenReturnCorrectTotalComDesconto() {
        venda.adicionarItem(produto, 2);
        Cupom cupom = new CupomValorMinimo("CUPOM10", 10.0, 100.0);
        venda.setCupom(cupom);
        assertEquals(4500.0, venda.getTotal());
    }

    @Test
    public void givenVenda_whenGetId_thenReturnCorrectId() {
        assertNotNull(venda.getId());
    }

    @Test
    public void givenVenda_whenGetData_thenReturnCorrectData() {
        assertEquals(LocalDate.now(), venda.getData());
    }

    @Test
    public void givenVenda_whenSetData_thenDataIsUpdated() {
        LocalDate novaData = LocalDate.of(2023, 12, 31);
        venda.setData(novaData);
        assertEquals(novaData, venda.getData());
    }

    @Test
    public void givenVenda_whenGetCliente_thenReturnCorrectCliente() {
        assertEquals(cliente, venda.getCliente());
    }

    @Test
    public void givenVenda_whenSetCliente_thenClienteIsUpdated() {
        Endereco endereco = new Endereco("Rua B", "456", "Apto 202", "Cidade", "Estado", "54321-876");
        Cliente novoCliente = new Cliente("98765432109", "Maria Oliveira", "maria@example.com", "0987654321", endereco);
        venda.setCliente(novoCliente);
        assertEquals(novoCliente, venda.getCliente());
    }
}