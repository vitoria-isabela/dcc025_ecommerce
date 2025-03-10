package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a clothing product in the e-commerce system.
 * Extends the Produto class and includes clothing-specific attributes.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Roupa extends Produto {
    private String tamanho;
    private String cor;
    private String material;
    private String lote;

    /**
     * Constructs a Roupa object with specific attributes.
     * 
     * @param codigo   the product code
     * @param nome     the product name
     * @param preco    the product price
     * @param tamanho  the size of the clothing
     * @param cor      the color of the clothing
     * @param material the material of the clothing
     * @param lote     the batch or lot number of the clothing
     */
    public Roupa(String codigo, String nome, double preco, String tamanho, String cor, String material, String lote) {
        super(codigo, nome, preco);
        this.tamanho = tamanho;
        this.cor = cor;
        this.material = material;
        this.lote = lote;
    }

    /**
     * Returns the size of the clothing.
     * 
     * @return the size of the clothing
     */
    public String getTamanho() {
        return tamanho;
    }

    /**
     * Returns the color of the clothing.
     * 
     * @return the color of the clothing
     */
    public String getCor() {
        return cor;
    }

    /**
     * Returns the material of the clothing.
     * 
     * @return the material of the clothing
     */
    public String getMaterial() {
        return material;
    }

    /**
     * Returns the batch or lot number of the clothing.
     * 
     * @return the batch/lot number of the clothing
     */
    public String getLote() {
        return lote;
    }

    /**
     * Returns the type of the product, which is "Roupa".
     * 
     * @return the string "Roupa"
     */
    @Override
    public String getTipo() {
        return "Roupa";
    }

    /**
     * Sets the size of the clothing.
     * 
     * @param tamanho the size of the clothing
     */
    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }

    /**
     * Sets the color of the clothing.
     * 
     * @param cor the color of the clothing
     */
    public void setCor(String cor) {
        this.cor = cor;
    }

    /**
     * Sets the material of the clothing.
     * 
     * @param material the material of the clothing
     */
    public void setMaterial(String material) {
        this.material = material;
    }

    /**
     * Sets the batch or lot number of the clothing.
     * 
     * @param lote the batch/lot number of the clothing
     */
    public void setLote(String lote) {
        this.lote = lote;
    }
}