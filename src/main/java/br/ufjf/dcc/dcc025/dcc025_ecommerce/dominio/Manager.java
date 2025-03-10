package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a manager user in the e-commerce system.
 * Extends the User class and defines a manager user type.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Manager extends User {

    /**
     * Constructs a Manager object.
     * 
     * @param username The username of the manager.
     * @param password The password of the manager.
     * @param name     The name of the manager.
     * @param email    The email of the manager.
     */
    public Manager(String username, String password, String name, String email) {
        super(username, password, name, email, "manager");
    }

    /**
     * Returns the type of the user (Manager).
     * 
     * @return The string "Manager".
     */
    @Override
    public String getType() {
        return "Manager";
    }
}