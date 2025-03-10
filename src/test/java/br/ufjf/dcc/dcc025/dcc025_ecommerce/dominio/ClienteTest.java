package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClienteTest {

    private Cliente cliente;

    @BeforeEach
    public void setUp() {
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
    }

    @Test
    public void givenCliente_whenGetCpf_thenReturnCorrectCpf() {
        assertEquals("12345678901", cliente.getCpf());
    }

    @Test
    public void givenCliente_whenGetNome_thenReturnCorrectNome() {
        assertEquals("João Silva", cliente.getNome());
    }

    @Test
    public void givenCliente_whenGetEmail_thenReturnCorrectEmail() {
        assertEquals("joao@example.com", cliente.getEmail());
    }

    @Test
    public void givenCliente_whenGetTelefone_thenReturnCorrectTelefone() {
        assertEquals("1234567890", cliente.getTelefone());
    }

    @Test
    public void givenCliente_whenGetEndereco_thenReturnCorrectEndereco() {
        Endereco endereco = cliente.getEndereco();
        assertNotNull(endereco);
        assertEquals("Rua A", endereco.getRua());
        assertEquals("123", endereco.getNumero());
        assertEquals("Apto 101", endereco.getComplemento());
        assertEquals("Cidade", endereco.getCidade());
        assertEquals("Estado", endereco.getEstado());
        assertEquals("12345-678", endereco.getCep());
    }
}