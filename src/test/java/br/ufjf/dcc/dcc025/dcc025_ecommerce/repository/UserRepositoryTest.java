package br.ufjf.dcc.dcc025.dcc025_ecommerce.repository;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.ClientUser;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Cliente;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Endereco;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Manager;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.Seller;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = UserRepository.getInstance();
        userRepository.limparUsers();
    }

    @Test
    public void givenNewUser_whenSalvar_thenUserIsAdded() {
        Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        assertEquals(1, userRepository.listarTodos().size());
    }

    @Test
    public void givenExistingUser_whenSalvar_thenThrowsException() {
         Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        assertThrows(IllegalArgumentException.class, () -> userRepository.salvar(user));
    }

    @Test
    public void givenUserList_whenListarTodos_thenReturnsAllUsers() {
        Manager user1 = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        Seller user2 = new Seller("maria", "senha456", "Maria Souza", "maria@email.com");
        userRepository.salvar(user1);
        userRepository.salvar(user2);
        assertEquals(2, userRepository.listarTodos().size());
    }

    @Test
    public void givenUsername_whenBuscarPorUsername_thenReturnsUser() {
         Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        User foundUser = userRepository.buscarPorUsername("joao");
        assertEquals(user, foundUser);
    }

    @Test
    public void givenNonExistingUsername_whenBuscarPorUsername_thenReturnsNull() {
        User foundUser = userRepository.buscarPorUsername("joao");
        assertNull(foundUser);
    }

    @Test
    public void givenCorrectCredentials_whenLogin_thenReturnsUser() {
        Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        User loggedInUser = userRepository.login("joao", "senha123");
        assertEquals(user, loggedInUser);
    }

    @Test
    public void givenIncorrectPassword_whenLogin_thenReturnsNull() {
       Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        User loggedInUser = userRepository.login("joao", "senhaErrada");
        assertNull(loggedInUser);
    }

    @Test
    public void givenIncorrectUsername_whenLogin_thenReturnsNull() {
         Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        User loggedInUser = userRepository.login("usuarioInexistente", "senha123");
        assertNull(loggedInUser);
    }

    @Test
    public void givenUser_whenEditar_thenUserIsUpdated() {
         Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);

        Manager userAtualizado = new Manager("joao", "novaSenha", "João Oliveira", "joao.o@email.com");
        userRepository.editar(userAtualizado);

        User foundUser = userRepository.buscarPorUsername("joao");
        assertEquals("novaSenha", foundUser.getPassword());
        assertEquals("João Oliveira", foundUser.getName());
        assertEquals("joao.o@email.com", foundUser.getEmail());
    }

    @Test
    public void givenNonExistingUser_whenEditar_thenNoExceptionIsThrown() {
       Manager userAtualizado = new Manager("joao", "novaSenha", "João Oliveira", "joao.o@email.com");
        assertDoesNotThrow(() -> userRepository.editar(userAtualizado));
    }

    @Test
    public void givenUsername_whenExcluir_thenUserIsRemoved() {
         Manager user = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user);
        userRepository.excluir("joao");
        assertNull(userRepository.buscarPorUsername("joao"));
    }

    @Test
    public void givenEmptyRepository_whenListarTodos_thenReturnsEmptyList() {
        List<User> users = userRepository.listarTodos();
        assertTrue(users.isEmpty());
    }

    @Test
    public void givenMultipleUsers_whenListarTodos_thenReturnsAllUsers() {
        Manager user1 = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        Seller user2 = new Seller("maria", "senha456", "Maria Souza", "maria@email.com");
        userRepository.salvar(user1);
        userRepository.salvar(user2);

        List<User> users = userRepository.listarTodos();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }

    @Test
    public void whenLimparUsers_thenRepositoryIsEmpty() {
        Manager user1 = new Manager("joao", "senha123", "João da Silva", "joao@email.com");
        userRepository.salvar(user1);
        userRepository.limparUsers();
        assertTrue(userRepository.listarTodos().isEmpty());
    }

    @Test
    public void givenTipo_whenListarTipo_thenReturnsCorrectTypeUsers() {
        Manager manager1 = new Manager("manager1", "adminSenha", "Admin User", "admin@email.com");
        Seller seller1 = new Seller("seller1", "vendedorSenha", "Vendedor User", "vendedor@email.com");
        Endereco endereco = new Endereco("Rua A", "123", "Apto 101", "Cidade", "Estado", "12345-678");
        Cliente cliente = new Cliente("12345678901", "João Silva", "joao@example.com", "1234567890", endereco);

        ClientUser client1 = new ClientUser("client1", "clientSenha", "Client User", "client@email.com", cliente);

        userRepository.salvar(manager1);
        userRepository.salvar(seller1);
        userRepository.salvar(client1);

        List<Manager> managers = userRepository.listarTipo(Manager.class);
        assertEquals(1, managers.size());
        assertEquals("manager1", managers.get(0).getUsername());

        List<Seller> sellers = userRepository.listarTipo(Seller.class);
        assertEquals(1, sellers.size());
        assertEquals("seller1", sellers.get(0).getUsername());

        List<ClientUser> clients = userRepository.listarTipo(ClientUser.class);
        assertEquals(1, clients.size());
        assertEquals("client1", clients.get(0).getUsername());
    }

    @Test
    public void givenNoMatchingType_whenListarTipo_thenReturnsEmptyList() {
        Manager manager1 = new Manager("manager1", "adminSenha", "Admin User", "admin@email.com");
        userRepository.salvar(manager1);
        List<Seller> sellers = userRepository.listarTipo(Seller.class);
        assertTrue(sellers.isEmpty());
    }

    @Test
    public void givenUserType_whenBuscarPorUsername_thenReturnsCorrectType() {
       Manager manager1 = new Manager("manager1", "adminSenha", "Admin User", "admin@email.com");
        userRepository.salvar(manager1);

        User foundUser = userRepository.buscarPorUsername("manager1");
        assertNotNull(foundUser);
        assertTrue(foundUser instanceof Manager);
        assertEquals("manager", foundUser.getRole());
    }
}