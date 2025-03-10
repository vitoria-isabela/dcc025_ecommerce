package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import java.time.LocalDate;

/**
 * Represents a food product in the e-commerce system.
 * Extends the Produto class and includes specific attributes for food items,
 * such as expiration date, manufacture date, batch number, and whether it is
 * organic.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Alimento extends Produto {
    private LocalDate dataValidade;
    private LocalDate dataFabricacao;
    private String lote;
    private boolean organico;

    /**
     * Constructs an Alimento object.
     *
     * @param codigo         Product code.
     * @param nome           Product name.
     * @param preco          Product price.
     * @param dataValidade   Food expiration date.
     * @param dataFabricacao Food manufacture date.
     * @param lote           Food batch number.
     * @param organico       Indicates whether the food is organic.
     */
    public Alimento(String codigo, String nome, double preco, LocalDate dataValidade, LocalDate dataFabricacao,
            String lote, boolean organico) {
        super(codigo, nome, preco);
        this.dataValidade = dataValidade;
        this.dataFabricacao = dataFabricacao;
        this.lote = lote;
        this.organico = organico;
    }

    /**
     * Returns the expiration date of the food.
     *
     * @return Expiration date.
     */
    public LocalDate getDataValidade() {
        return dataValidade;
    }

    /**
     * Returns the manufacture date of the food.
     *
     * @return Manufacture date.
     */
    public LocalDate getDataFabricacao() {
        return dataFabricacao;
    }

    /**
     * Returns the batch number of the food.
     *
     * @return Batch number.
     */
    public String getLote() {
        return lote;
    }

    /**
     * Checks if the food is organic.
     *
     * @return True if organic, false otherwise.
     */
    public boolean isOrganico() {
        return organico;
    }

    /**
     * Returns the product type (Alimento).
     *
     * @return Product type.
     */
    @Override
    public String getTipo() {
        return "Alimento";
    }

    /**
     * Sets the expiration date of the food.
     *
     * @param dataValidade New expiration date.
     */
    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    /**
     * Sets the manufacture date of the food.
     *
     * @param dataFabricacao New manufacture date.
     */
    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    /**
     * Sets the batch number of the food.
     *
     * @param lote New batch number.
     */
    public void setLote(String lote) {
        this.lote = lote;
    }

    /**
     * Sets whether the food is organic.
     *
     * @param organico New value for the organic attribute.
     */
    public void setOrganico(boolean organico) {
        this.organico = organico;
    }
}