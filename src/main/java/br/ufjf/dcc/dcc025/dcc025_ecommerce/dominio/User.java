package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents an abstract user in the e-commerce system.
 * Provides basic user information and an abstract method for getting the user
 * type.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public abstract class User {
    private String username;
    private String password;
    private String name;
    private String email;
    private String role;

    /**
     * Constructs a User object.
     * 
     * @param username The username of the user.
     * @param password The password of the user.
     * @param name     The name of the user.
     * @param email    The email of the user.
     * @param role     The role of the user (e.g., "manager", "seller", "client").
     */
    public User(String username, String password, String name, String email, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    /**
     * Returns the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the user.
     * 
     * @param username The new username.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Returns the password of the user.
     * 
     * @return The password of the user.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * 
     * @param password The new password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the name of the user.
     * 
     * @return The name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the user.
     * 
     * @param name The new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the email of the user.
     * 
     * @return The email of the user.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user.
     * 
     * @param email The new email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the role of the user.
     * 
     * @return The role of the user.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the user.
     * 
     * @param role The new role.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Abstract method to retrieve the type of the user.
     * Implemented by subclasses to specify their type.
     * 
     * @return The type of the user.
     */
    public abstract String getType();
}