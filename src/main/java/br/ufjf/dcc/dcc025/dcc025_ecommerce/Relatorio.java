package br.ufjf.dcc.dcc025.dcc025_ecommerce;

/**
 * Interface defining the contract for generating sales reports and listing
 * active
 * coupons.
 * Implementing classes must provide concrete implementations for these methods.
 * 
 * @Author: Vitória Isabela de Oliveira - 202065097C
 */
public interface Relatorio {

    /**
     * Generates a report of all sales.
     * 
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void gerarRelatorioVendas();

    /**
     * Lists all active coupons.
     * 
     * @Author: Vitória Isabela de Oliveira - 202065097C
     */
    void listarCuponsAtivos();
}