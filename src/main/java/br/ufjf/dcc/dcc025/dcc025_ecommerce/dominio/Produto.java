package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a product in the e-commerce system.
 * This is an abstract class providing basic product information, with
 * subclasses for specific types.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public abstract class Produto {
    private final String codigo;
    private String nome;
    private double preco;

    /**
     * Constructs a Produto object with the specified code, name, and price.
     * 
     * @param codigo the unique code of the product
     * @param nome   the name of the product
     * @param preco  the price of the product
     */
    public Produto(String codigo, String nome, double preco) {
        this.codigo = codigo;
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Returns the unique code of the product.
     * 
     * @return the product code
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Returns the name of the product.
     * 
     * @return the product name
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the name of the product.
     * 
     * @param nome the new product name
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Returns the price of the product.
     * 
     * @return the product price
     */
    public double getPreco() {
        return preco;
    }

    /**
     * Sets the price of the product.
     * 
     * @param preco the new product price
     */
    public void setPreco(double preco) {
        this.preco = preco;
    }

    /**
     * Abstract method to retrieve the type of the product.
     * Implemented by subclasses to specify their type.
     * 
     * @return the type of the product
     */
    public abstract String getTipo();

    /**
     * Returns a string representation of the product, including its name and price.
     * 
     * @return a formatted string representing the product
     */
    @Override
    public String toString() {
        return nome + " (R$" + preco + ")";
    }
}