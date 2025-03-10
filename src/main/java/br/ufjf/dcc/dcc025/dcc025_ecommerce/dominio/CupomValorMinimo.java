package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a minimum-value coupon in the e-commerce system.
 * Extends the Cupom class and requires a minimum order value for application.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class CupomValorMinimo extends Cupom {
    private double valorMinimo;

    /**
     * Constructs a CupomValorMinimo object.
     *
     * @param codigo             Coupon code.
     * @param percentualDesconto Discount percentage.
     * @param valorMinimo        Minimum order value required.
     */
    public CupomValorMinimo(String codigo, double percentualDesconto, double valorMinimo) {
        super(codigo, percentualDesconto);
        this.valorMinimo = valorMinimo;
    }

    /**
     * Checks if the coupon can be applied based on the order total.
     *
     * @param valorTotal The total order value.
     * @return True if the coupon can be applied, false otherwise.
     */
    @Override
    public boolean podeSerAplicado(double valorTotal) {
        return valorTotal >= valorMinimo;
    }

    /**
     * Returns the minimum order value required for the coupon to be applied.
     *
     * @return The minimum order value.
     */
    public double getValorMinimo() {
        return valorMinimo;
    }

    /**
     * Sets the discount percentage for the coupon.
     *
     * @param percentualDesconto The new discount percentage.
     */
    @Override
    public void setPercentualDesconto(double percentualDesconto) {
        super.percentualDesconto = percentualDesconto;
    }

    /**
     * Sets the minimum order value required for the coupon to be applied.
     *
     * @param valorMinimo The new minimum order value.
     */
    public void setValorMinimo(double valorMinimo) {
        this.valorMinimo = valorMinimo;
    }
}