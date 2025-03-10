package br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes;

/**
 * Custom exception indicating an invalid coupon attempt.
 * This exception is thrown when a coupon is used but its application conditions
 * are not met.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class CupomInvalidoException extends Exception {
    /**
     * Constructs a CupomInvalidoException with a specified error message.
     * 
     * @param message the detail message explaining why the coupon is invalid
     */
    public CupomInvalidoException(String message) {
        super(message);
    }
}