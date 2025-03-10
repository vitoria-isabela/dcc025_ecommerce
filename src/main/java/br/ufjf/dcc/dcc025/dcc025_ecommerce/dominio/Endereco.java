package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents an address in the e-commerce system.
 * Holds information about the street, number, complement, city, state, and zip
 * code.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Endereco {
    private String rua;
    private String numero;
    private String complemento;
    private String cidade;
    private String estado;
    private String cep;

    /**
     * Constructs an Endereco object with the specified address details.
     * 
     * @param rua         the street name
     * @param numero      the street number
     * @param complemento the complement (optional)
     * @param cidade      the city
     * @param estado      the state
     * @param cep         the zip code
     */
    public Endereco(String rua, String numero, String complemento, String cidade, String estado, String cep) {
        this.rua = rua;
        this.numero = numero;
        this.complemento = complemento;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = cep;
    }

    /**
     * Returns the street name of the address.
     * 
     * @return the street name
     */
    public String getRua() {
        return rua;
    }

    /**
     * Returns the street number of the address.
     * 
     * @return the street number
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Returns the complement of the address.
     * 
     * @return the complement (optional)
     */
    public String getComplemento() {
        return complemento;
    }

    /**
     * Returns the city of the address.
     * 
     * @return the city
     */
    public String getCidade() {
        return cidade;
    }

    /**
     * Returns the state of the address.
     * 
     * @return the state
     */
    public String getEstado() {
        return estado;
    }

    /**
     * Returns the zip code of the address.
     * 
     * @return the zip code
     */
    public String getCep() {
        return cep;
    }
}