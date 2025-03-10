package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a header coupon in the e-commerce system.
 * Extends the Cupom class but overrides methods to provide header
 * functionality.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class CupomHeader extends Cupom {
    private final String title;

    /**
     * Constructs a CupomHeader object with a specified title.
     *
     * @param title The title of the header coupon.
     */
    public CupomHeader(String title) {
        super("", 0.0);
        this.title = title;
    }

    /**
     * Returns a string representation of the CupomHeader object, which is its
     * title.
     *
     * @return The title of the header coupon.
     */
    @Override
    public String toString() {
        return title;
    }

    /**
     * Checks if the coupon can be applied (always returns false for header
     * coupons).
     *
     * @param valorTotal The total value to check against.
     * @return Always returns false.
     */
    @Override
    public boolean podeSerAplicado(double valorTotal) {
        return false;
    }

    /**
     * Sets the discount percentage (implemented for compatibility).
     *
     * @param percentualDesconto New discount percentage.
     */
    @Override
    public void setPercentualDesconto(double percentualDesconto) {
        this.percentualDesconto = percentualDesconto;
    }
}