package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a customer in the e-commerce system.
 *  @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Cliente {

    private String cpf;
    private String nome;
    private String email;
    private String telefone;
    private Endereco endereco;

    /**
     * Constructs a Cliente object.
     *
     * @param cpf      Customer's CPF.
     * @param nome     Customer's name.
     * @param email    Customer's email.
     * @param telefone Customer's phone number.
     * @param endereco Customer's address.
     */
    public Cliente(String cpf, String nome, String email, String telefone, Endereco endereco) {
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    /**
     * Returns the customer's CPF.
     *
     * @return Customer's CPF.
     */
    public String getCpf() {
        return cpf;
    }

    /**
     * Returns the customer's name.
     *
     * @return Customer's name.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the customer's name.
     *
     * @param nome New customer name.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Returns the customer's email.
     *
     * @return Customer's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the customer's email.
     *
     * @param email New customer email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the customer's phone number.
     *
     * @return Customer's phone number.
     */
    public String getTelefone() {
        return telefone;
    }

    /**
     * Sets the customer's phone number.
     *
     * @param telefone New customer phone number.
     */
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    /**
     * Returns the customer's address.
     *
     * @return Customer's address.
     */
    public Endereco getEndereco() {
        return endereco;
    }

    /**
     * Sets the customer's address.
     *
     * @param endereco New customer address.
     */
    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}