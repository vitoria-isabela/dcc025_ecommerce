package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.GestorVendas;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cupom;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.CupomQuantidadeLimitada;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Produto;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Venda;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes.CupomInvalidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GestorVendasTest {

    private GestorVendas gestorVendas;
    private ClienteRepository clienteRepository;
    private CupomRepository cupomRepository;
    private ProdutoRepository produtoRepository;
    private VendaRepository vendaRepository;

    @BeforeEach
    public void setUp() {
        clienteRepository = mock(ClienteRepository.class);
        cupomRepository = mock(CupomRepository.class);
        produtoRepository = mock(ProdutoRepository.class);
        vendaRepository = mock(VendaRepository.class);
        gestorVendas = GestorVendas.createForTest(produtoRepository, cupomRepository, vendaRepository, clienteRepository);
    }

    @Test
    public void givenGestorVendas_whenAdicionarCliente_thenClienteIsAdded() {
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        gestorVendas.adicionarCliente(cliente);
        verify(clienteRepository, times(1)).salvar(cliente);
        verify(clienteRepository, times(1)).salvarEmArquivo("clientes.json");
    }

    @Test
    public void givenGestorVendas_whenBuscarCliente_thenReturnCorrectCliente() {
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(cliente);
        assertEquals(cliente, gestorVendas.buscarCliente("12345678901"));
    }

    @Test
    public void givenGestorVendas_whenEditarCliente_thenClienteIsEdited() {
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        when(clienteRepository.buscarPorCpf("12345678901")).thenReturn(cliente);

        Endereco newEndereco = new Endereco("Rua B", "456", "Apto 202", "Outra Cidade", "Outro Estado", "54321-876");
        Cliente clienteAtualizado = new Cliente("12345678901", "João Oliveira", "joao.oliveira@example.com", "9876543210", newEndereco);
        List<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        when(clienteRepository.listarTodos()).thenReturn(clientes);

        gestorVendas.editarCliente(clienteAtualizado);

        verify(clienteRepository, times(1)).buscarPorCpf("12345678901");
        verify(clienteRepository).editar(cliente);
        verify(clienteRepository).salvarEmArquivo("clientes.json");
    }

    @Test
    public void givenGestorVendas_whenExcluirCliente_thenClienteIsExcluded() {
        String cpf = "12345678901";
        gestorVendas.excluirCliente(cpf);

        verify(clienteRepository, times(1)).excluirByCpf(cpf);
        verify(clienteRepository, times(1)).salvarEmArquivo("clientes.json");
    }

    @Test
    public void givenGestorVendas_whenAdicionarCupom_thenCupomIsAdded() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        gestorVendas.adicionarCupom(cupom);
        verify(cupomRepository, times(1)).salvar(cupom);
        verify(cupomRepository, times(1)).salvarEmArquivo("cupons.json");
    }

    @Test
    public void givenGestorVendas_whenBuscarCupom_thenReturnCorrectCupom() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        when(cupomRepository.buscarPorCodigo("CUPOM10")).thenReturn(cupom);
        assertEquals(cupom, gestorVendas.buscarCupom("CUPOM10"));
    }

    @Test
    public void givenGestorVendas_whenEditarCupom_thenCupomIsEdited() {
        CupomQuantidadeLimitada cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        CupomQuantidadeLimitada cupomAtualizado = new CupomQuantidadeLimitada("CUPOM10", 15.0, 10);
        when(cupomRepository.buscarPorCodigo("CUPOM10")).thenReturn(cupom);

        gestorVendas.editarCupom(cupomAtualizado);

        verify(cupomRepository, times(1)).buscarPorCodigo("CUPOM10");
        verify(cupomRepository).editar(cupomAtualizado);
        verify(cupomRepository).salvarEmArquivo("cupons.json");
    }

    @Test
    public void givenGestorVendas_whenExcluirCupom_thenCupomIsExcluded() {
        String codigo = "CUPOM10";
        gestorVendas.excluirCupom(codigo);

        verify(cupomRepository, times(1)).excluir(codigo);
        verify(cupomRepository, times(1)).salvarEmArquivo("cupons.json");
    }

    @Test
    public void givenGestorVendas_whenAdicionarProduto_thenProdutoIsAdded() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        gestorVendas.adicionarProduto(produto);
        verify(produtoRepository, times(1)).salvar(produto);
        verify(produtoRepository, times(1)).salvarEmArquivo("produtos.json");
    }

    @Test
    public void givenGestorVendas_whenBuscarProduto_thenReturnCorrectProduto() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        when(produtoRepository.buscarPorCodigo("001")).thenReturn(produto);
        assertEquals(produto, gestorVendas.buscarProduto("001"));
    }

    @Test
    public void givenGestorVendas_whenEditarProduto_thenProdutoIsEdited() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Produto produtoAtualizado = new Produto("001", "Notebook Novo", 3000.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        when(produtoRepository.buscarPorCodigo("001")).thenReturn(produto);
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        when(produtoRepository.listarTodos()).thenReturn(produtos);

        gestorVendas.editarProduto(produtoAtualizado);

        verify(produtoRepository, times(1)).buscarPorCodigo("001");
        verify(produtoRepository).salvar(produtoAtualizado);
        verify(produtoRepository).salvarEmArquivo("produtos.json");
    }

    @Test
    public void givenGestorVendas_whenExcluirProduto_thenProdutoIsExcluded() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        when(produtoRepository.buscarPorCodigo("001")).thenReturn(produto);
        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        when(produtoRepository.listarTodos()).thenReturn(produtos);

        gestorVendas.excluirProduto("001");

        verify(produtoRepository, times(1)).buscarPorCodigo("001");
        verify(produtoRepository).salvarEmArquivo("produtos.json");
    }

   @Test
    public void givenGestorVendas_whenRealizarVenda_thenVendaIsAdded() throws CupomInvalidoException {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(LocalDate.now());
        venda.adicionarItem(produto, 2);
        gestorVendas.realizarVenda(venda);
        verify(vendaRepository, times(1)).salvar(venda);
        verify(vendaRepository, times(1)).salvarEmArquivo("vendas.json");

    }
    @Test
    public void givenGestorVendas_whenListarVendasPorData_thenReturnVendasDaData() {
        LocalDate data = LocalDate.now();
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(data);
        venda.adicionarItem(produto, 2);
        when(vendaRepository.listarTodos()).thenReturn(List.of(venda));

        List<Venda> vendas = gestorVendas.listarVendasPorData(data.toString());

        assertEquals(1, vendas.size());
        assertEquals(data, vendas.get(0).getData());
    }


    @Test
    public void givenGestorVendas_whenCalcularTotalVendas_thenReturnCorrectTotal() {
        LocalDate data = LocalDate.now();
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        Venda venda = new Venda();
        venda.setCliente(cliente);
        venda.setData(data);
        venda.adicionarItem(produto, 2);

        List<Venda> vendas = new ArrayList<>();
        vendas.add(venda);

        when(vendaRepository.listarTodos()).thenReturn(vendas);

        double total = gestorVendas.calcularTotalVendas(data.toString());

        assertEquals(5000.0, total);
    }

    @Test
    public void givenGestorVendas_whenListarCuponsAtivos_thenReturnActiveCupons() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        List<Cupom> activeCupons = new ArrayList<>();
        activeCupons.add(cupom);
        when(cupomRepository.listarAtivos()).thenReturn(activeCupons);

        gestorVendas.listarCuponsAtivos();

        verify(cupomRepository, times(1)).listarAtivos();
    }

    @Test
    public void givenGestorVendas_whenListarCupons_thenReturnAllCupons() {
        Cupom cupom = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        List<Cupom> allCupons = new ArrayList<>();
        allCupons.add(cupom);
        when(cupomRepository.listarTodos()).thenReturn(allCupons);

        List<Cupom> result = gestorVendas.listarCupons();

        assertEquals(1, result.size());
        assertEquals(cupom, result.get(0));
    }

    @Test
    public void givenGestorVendas_whenListarProdutos_thenReturnAllProdutos() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        List<Produto> allProducts = new ArrayList<>();
        allProducts.add(produto);
        when(produtoRepository.listarTodos()).thenReturn(allProducts);

        List<Produto> result = gestorVendas.listarProdutos();

        assertEquals(1, result.size());
        assertEquals(produto, result.get(0));
    }

    @Test
    public void givenGestorVendas_whenListarClientes_thenReturnAllClientes() {
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        List<Cliente> allClientes = new ArrayList<>();
        allClientes.add(cliente);
        when(clienteRepository.listarTodos()).thenReturn(allClientes);

        List<Cliente> result = gestorVendas.listarClientes();

        assertEquals(1, result.size());
        assertEquals(cliente, result.get(0));
    }

    @Test
    public void givenGestorVendas_whenBuscarProdutoPorNome_thenReturnCorrectProduto() {
        Produto produto = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto);
        when(produtoRepository.listarTodos()).thenReturn(produtos);

        Produto foundProduto = gestorVendas.buscarProdutoPorNome("Notebook");

        assertEquals(produto, foundProduto);
    }
}