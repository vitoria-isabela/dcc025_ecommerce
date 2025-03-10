package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents an electronic product in the e-commerce system.
 * Extends the Produto class and includes attributes specific to electronics.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Eletronico extends Produto {
    private int garantiaMeses;
    private String marca;
    private String especificacoesTecnicas;

    /**
     * Constructs an Eletronico object with specific attributes.
     * 
     * @param codigo                 the product code
     * @param nome                   the product name
     * @param preco                  the product price
     * @param garantiaMeses          the warranty period in months
     * @param marca                  the brand of the electronic product
     * @param especificacoesTecnicas the technical specifications of the product
     */
    public Eletronico(String codigo, String nome, double preco, int garantiaMeses, String marca,
            String especificacoesTecnicas) {
        super(codigo, nome, preco);
        this.garantiaMeses = garantiaMeses;
        this.marca = marca;
        this.especificacoesTecnicas = especificacoesTecnicas;
    }

    /**
     * Returns the warranty period in months for the electronic product.
     * 
     * @return the warranty period in months
     */
    public int getGarantiaMeses() {
        return garantiaMeses;
    }

    /**
     * Returns the brand of the electronic product.
     * 
     * @return the brand of the electronic product
     */
    public String getMarca() {
        return marca;
    }

    /**
     * Returns the technical specifications of the electronic product.
     * 
     * @return the technical specifications of the product
     */
    public String getEspecificacoesTecnicas() {
        return especificacoesTecnicas;
    }

    /**
     * Returns the type of the product, which is "Eletrônico".
     * 
     * @return the string "Eletrônico"
     */
    @Override
    public String getTipo() {
        return "Eletrônico";
    }

    /**
     * Sets the warranty period in months for the electronic product.
     * 
     * @param garantiaMeses the warranty period in months
     */
    public void setGarantiaMeses(int garantiaMeses) {
        this.garantiaMeses = garantiaMeses;
    }

    /**
     * Sets the brand of the electronic product.
     * 
     * @param marca the brand of the electronic product
     */
    public void setMarca(String marca) {
        this.marca = marca;
    }

    /**
     * Sets the technical specifications of the electronic product.
     * 
     * @param especificacoesTecnicas the technical specifications of the product
     */
    public void setEspecificacoesTecnicas(String especificacoesTecnicas) {
        this.especificacoesTecnicas = especificacoesTecnicas;
    }
}