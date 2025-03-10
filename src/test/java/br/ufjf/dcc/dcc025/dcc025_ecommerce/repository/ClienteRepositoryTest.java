package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


public class ClienteRepositoryTest {

    private ClienteRepository clienteRepository;

    @BeforeEach
    public void setUp() {
        clienteRepository = ClienteRepository.getInstance();
        clienteRepository.limparClientes(); 
    }

    @Test
    public void givenNewCliente_whenSalvar_thenClienteIsAdded() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        assertEquals(1, clienteRepository.listarTodos().size());
    }

    @Test
    public void givenExistingCliente_whenSalvar_thenThrowsException() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        clienteRepository.salvar(cliente); 
        assertEquals(1, clienteRepository.listarTodos().size());

    }

    @Test
    public void givenClienteList_whenListarTodos_thenReturnsAllClientes() {
        Cliente cliente1 = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        Cliente cliente2 = new Cliente("98765432109", "Maria Oliveira", "maria@example.com", "0987654321", new Endereco("Rua B", "456", "Apto 202", "Cidade", "Estado", "54321-876"));
        clienteRepository.salvar(cliente1);
        clienteRepository.salvar(cliente2);
        assertEquals(2, clienteRepository.listarTodos().size());
    }

    @Test
    public void givenClienteCpf_whenBuscarPorCpf_thenReturnsCliente() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        Cliente foundCliente = clienteRepository.buscarPorCpf("12345678901");
        assertEquals(cliente, foundCliente);
    }

    @Test
    public void givenNonExistingClienteCpf_whenBuscarPorCpf_thenReturnsNull() {
        Cliente foundCliente = clienteRepository.buscarPorCpf("12345678901");
        assertNull(foundCliente);
    }

    @Test
    public void givenClienteEmail_whenBuscarPorEmail_thenReturnsCliente() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        Cliente foundCliente = clienteRepository.buscarPorEmail("joao@example.com");
        assertEquals(cliente, foundCliente);
    }

     @Test
    public void givenNonExistingClienteEmail_whenBuscarPorEmail_thenReturnsNull() {
        Cliente foundCliente = clienteRepository.buscarPorEmail("nonexistent@example.com");
        assertNull(foundCliente);
    }

    @Test
    public void givenCliente_whenEditar_thenClienteIsUpdated() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);

        Cliente clienteAtualizado = new Cliente("12345678901", "João Oliveira", "joao.oliveira@example.com", "9876543210", new Endereco("Rua B", "456", "Apto 202", "Outra Cidade", "Outro Estado", "54321-876"));
       clienteRepository.editar(clienteAtualizado);

        Cliente foundCliente = clienteRepository.buscarPorCpf("12345678901");
        assertEquals("João Oliveira", foundCliente.getNome());
        assertEquals("joao.oliveira@example.com", foundCliente.getEmail());
    }

     @Test
    public void givenNonExistingCliente_whenEditar_thenThrowsException() {
        Cliente clienteAtualizado = new Cliente("12345678901", "João Oliveira", "joao.oliveira@example.com", "9876543210", new Endereco("Rua B", "456", "Apto 202", "Outra Cidade", "Outro Estado", "54321-876"));
        assertThrows(IllegalArgumentException.class, () -> clienteRepository.editar(clienteAtualizado));
    }

   @Test
    public void givenClienteCpf_whenExcluir_thenClienteIsRemoved() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        clienteRepository.excluir("12345678901");
        assertNull(clienteRepository.buscarPorCpf("12345678901"));
    }

    @Test
    public void givenClienteCpf_whenExcluirByCpf_thenClienteIsRemoved() {
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente);
        clienteRepository.excluirByCpf("12345678901");
        assertNull(clienteRepository.buscarPorCpf("12345678901"));
    }

    @Test
    public void givenEmptyRepository_whenListarTodos_thenReturnsEmptyList() {
        List<Cliente> clientes = clienteRepository.listarTodos();
        assertTrue(clientes.isEmpty());
    }

    @Test
    public void givenMultipleClientes_whenListarTodos_thenReturnsAllClientes() {
        Cliente cliente1 = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        Cliente cliente2 = new Cliente("98765432109", "Maria Oliveira", "maria@example.com", "0987654321", new Endereco("Rua B", "456", "Apto 202", "Cidade", "Estado", "54321-876"));
        clienteRepository.salvar(cliente1);
        clienteRepository.salvar(cliente2);

        List<Cliente> clientes = clienteRepository.listarTodos();
        assertEquals(2, clientes.size());
        assertTrue(clientes.contains(cliente1));
        assertTrue(clientes.contains(cliente2));
    }

    @Test
    public void whenLimparClientes_thenRepositoryIsEmpty() {
        Cliente cliente1 = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678"));
        clienteRepository.salvar(cliente1);
        clienteRepository.limparClientes();
        assertTrue(clienteRepository.listarTodos().isEmpty());
    }
}