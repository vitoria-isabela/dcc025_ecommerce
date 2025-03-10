package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VendaRepositoryTest {

    private VendaRepository vendaRepository;
    private Venda venda;

    @BeforeEach
    public void setUp() {
        vendaRepository = VendaRepository.getInstance();
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        vendaRepository.limparVendas();
        venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDate.now());
        venda.adicionarItem(produto, 2);
    }

    @Test
    public void givenVendaRepository_whenSalvarVenda_thenVendaIsAdded() {
        vendaRepository.salvar(venda);
        Venda foundVenda = vendaRepository.buscarPorId(venda.getId());
        assertNotNull(foundVenda);  
        assertEquals(venda.getId(), foundVenda.getId()); 
    }

    @Test
    public void givenVendaRepository_whenListarTodos_thenReturnAllVendas() {
        vendaRepository.salvar(venda);
        List<Venda> vendas = vendaRepository.listarTodos();
        assertEquals(1, vendas.size());
        assertEquals(venda.getId(), vendas.get(0).getId()); 
    }

    @Test
    public void givenVendaRepository_whenBuscarPorId_thenReturnCorrectVenda() {
        vendaRepository.salvar(venda);
        Venda foundVenda = vendaRepository.buscarPorId(venda.getId());
         assertNotNull(foundVenda); 
        assertEquals(venda.getId(), foundVenda.getId());
    }

    @Test
    public void givenVendaRepository_whenBuscarPorIdInexistente_thenReturnNull() {
        assertNull(vendaRepository.buscarPorId("VENDA-999"));
    }

    @Test
    public void givenVendaRepository_whenListarPorData_thenReturnVendasDaData() {
        vendaRepository.salvar(venda);
        List<Venda> vendas = vendaRepository.listarPorData(LocalDate.now());
        assertEquals(1, vendas.size());
        assertEquals(venda.getId(), vendas.get(0).getId());
    }

    @Test
    public void givenVendaRepository_whenEditarVenda_thenVendaIsUpdated() {
         vendaRepository.salvar(venda);
        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId(venda.getId()); 
        vendaAtualizada.setCliente(venda.getCliente());
        vendaAtualizada.setData(LocalDate.now().plusDays(1));
        vendaAtualizada.adicionarItem(venda.getItens().get(0).getProduto(), 3);

        vendaRepository.editar(vendaAtualizada);

        Venda foundVenda = vendaRepository.buscarPorId(venda.getId());
        assertNotNull(foundVenda);
        assertEquals(LocalDate.now().plusDays(1), foundVenda.getData());
    }

    @Test
    public void givenVendaRepository_whenEditarVendaInexistente_thenThrowsException() {
        Venda vendaAtualizada = new Venda();
        vendaAtualizada.setId("VENDA-999");

        assertThrows(IllegalArgumentException.class, () -> vendaRepository.editar(vendaAtualizada));
    }

    @Test
    public void givenVendaRepository_whenExcluirVenda_thenVendaIsRemoved() {
        vendaRepository.salvar(venda);
        vendaRepository.excluir(venda.getId());
        assertNull(vendaRepository.buscarPorId(venda.getId()));
    }

     @Test
    public void givenVendaRepository_whenLimparVendas_thenRepositoryIsEmpty() {
        vendaRepository.salvar(venda);
        vendaRepository.limparVendas();
        assertTrue(vendaRepository.listarTodos().isEmpty());
    }
}