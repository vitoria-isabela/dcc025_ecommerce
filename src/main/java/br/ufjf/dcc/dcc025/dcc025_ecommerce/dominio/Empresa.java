package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a company in the e-commerce system.
 * It holds the company's CNPJ and name.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Empresa {
    private final String cnpj;
    private final String nome;

    /**
     * Constructs an Empresa object with the specified CNPJ and name.
     * 
     * @param cnpj the CNPJ of the company
     * @param nome the name of the company
     */
    public Empresa(String cnpj, String nome) {
        this.cnpj = cnpj;
        this.nome = nome;
    }

    /**
     * Returns the CNPJ of the company.
     * 
     * @return the CNPJ of the company
     */
    public String getCnpj() {
        return cnpj;
    }

    /**
     * Returns the name of the company.
     * 
     * @return the name of the company
     */
    public String getNome() {
        return nome;
    }
}