package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EnderecoTest {

    private Endereco endereco;

    @BeforeEach
    public void setUp() {
        endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
    }

    @Test
    public void givenEndereco_whenGetRua_thenReturnCorrectRua() {
        assertEquals("Rua A", endereco.getRua());
    }

    @Test
    public void givenEndereco_whenGetNumero_thenReturnCorrectNumero() {
        assertEquals("123", endereco.getNumero());
    }

    @Test
    public void givenEndereco_whenGetComplemento_thenReturnCorrectComplemento() {
        assertEquals("Apto 101", endereco.getComplemento());
    }

    @Test
    public void givenEndereco_whenGetCidade_thenReturnCorrectCidade() {
        assertEquals("Cidade", endereco.getCidade());
    }

    @Test
    public void givenEndereco_whenGetEstado_thenReturnCorrectEstado() {
        assertEquals("Estado", endereco.getEstado());
    }

    @Test
    public void givenEndereco_whenGetCep_thenReturnCorrectCep() {
        assertEquals("12345-678", endereco.getCep());
    }
}