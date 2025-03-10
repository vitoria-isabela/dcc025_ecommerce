package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a seller user in the e-commerce system.
 * Extends the User class and defines a seller user type.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Seller extends User {

    /**
     * Constructs a Seller object.
     * 
     * @param username The username of the seller.
     * @param password The password of the seller.
     * @param name     The name of the seller.
     * @param email    The email of the seller.
     */
    public Seller(String username, String password, String name, String email) {
        super(username, password, name, email, "seller");
    }

    /**
     * Returns the type of the user (Seller).
     * 
     * @return The string "Seller".
     */
    @Override
    public String getType() {
        return "Seller";
    }
}