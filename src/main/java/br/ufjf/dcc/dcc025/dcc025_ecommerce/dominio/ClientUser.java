package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a client user in the e-commerce system.
 * Extends the User class and associates it with a Cliente object.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ClientUser extends User {

    private Cliente cliente;

    /**
     * Constructs a ClientUser object.
     *
     * @param username User's username.
     * @param password User's password.
     * @param name     User's name.
     * @param email    User's email.
     * @param cliente  The associated Cliente object.
     */
    public ClientUser(String username, String password, String name, String email, Cliente cliente) {
        super(username, password, name, email, "client");
        this.cliente = cliente;
    }

    /**
     * Returns the associated Cliente object.
     *
     * @return The Cliente object.
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Sets the associated Cliente object.
     *
     * @param cliente New Cliente object.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Returns the user type (Client).
     *
     * @return User type.
     */
    @Override
    public String getType() {
        return "Client";
    }
}