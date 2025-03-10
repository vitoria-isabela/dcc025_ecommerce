package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.*;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.Persistencia;

import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersistenciaTest {

    private String testDirectory = "test-data";
    private String clientesFile = testDirectory + "/clientes.json";
    private String cuponsFile = testDirectory + "/cupons.json";
    private String produtosFile = testDirectory + "/produtos.json";
    private String vendasFile = testDirectory + "/vendas.json";

    @BeforeEach
    void setUp() throws IOException {
        File dir = new File(testDirectory);
        if (!dir.exists()) {
            dir.mkdir();
        }

        Files.write(Paths.get(clientesFile), "".getBytes());
        Files.write(Paths.get(cuponsFile), "".getBytes());
        Files.write(Paths.get(produtosFile), "".getBytes());
        Files.write(Paths.get(vendasFile), "".getBytes());
    }

    @Test
    void givenClientes_whenSalvarEmArquivo_thenFileIsCreatedAndContainsData() {
        List<Cliente> clientes = new ArrayList<>();
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente1 = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        clientes.add(cliente1);

        Persistencia.salvarEmArquivo(clientesFile, clientes);

        File file = new File(clientesFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void givenCupons_whenSalvarEmArquivo_thenFileIsCreatedAndContainsData() {
        List<Cupom> cupons = new ArrayList<>();
        CupomQuantidadeLimitada cupom1 = new CupomQuantidadeLimitada("CUPOM10", 10.0, 5);
        cupons.add(cupom1);

        Persistencia.salvarEmArquivo(cuponsFile, cupons);

        File file = new File(cuponsFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void givenProdutos_whenSalvarEmArquivo_thenFileIsCreatedAndContainsData() {
        List<Produto> produtos = new ArrayList<>();
        Produto produto1 = new Produto("001", "Notebook", 2500.0) {
            @Override
            public String getTipo() {
                return "Eletrônico";
            }
        };
        produtos.add(produto1);

        Persistencia.salvarEmArquivo(produtosFile, produtos);

        File file = new File(produtosFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void givenVendas_whenSalvarEmArquivo_thenFileIsCreatedAndContainsData() {
        List<Venda> vendas = new ArrayList<>();
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
        vendas.add(venda);

        Persistencia.salvarEmArquivo(vendasFile, vendas);

        File file = new File(vendasFile);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    void givenClientesFile_whenCarregarDeArquivo_thenClientesAreLoaded() {
        List<Cliente> clientes = new ArrayList<>();
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente1 = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);
        clientes.add(cliente1);
        Persistencia.salvarEmArquivo(clientesFile, clientes);

        Type type = new TypeToken<List<Cliente>>() {}.getType();
        List<Cliente> loadedClientes = Persistencia.carregarDeArquivo(clientesFile, type);

        assertNotNull(loadedClientes);
        assertEquals(1, loadedClientes.size());
        assertEquals(cliente1.getCpf(), loadedClientes.get(0).getCpf());
    }
    
    @Test
    void givenNoFile_whenCarregarDeArquivo_thenReturnsNullAndLogsError() {
         String nonExistentFile = testDirectory + "/nonexistent.json";
          Type type = new TypeToken<List<Cliente>>() {}.getType();
        List<Cliente> loadedClientes = Persistencia.carregarDeArquivo(nonExistentFile, type);
        assertNull(loadedClientes);
    }

}