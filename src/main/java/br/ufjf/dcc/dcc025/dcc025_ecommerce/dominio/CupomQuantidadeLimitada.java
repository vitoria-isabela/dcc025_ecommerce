package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a limited-quantity coupon in the e-commerce system.
 * Extends the Cupom class and restricts usage based on a maximum number of
 * uses.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class CupomQuantidadeLimitada extends Cupom {
    private int maximoUtilizacoes;
    private int utilizacoesAtuais;

    /**
     * Constructs a CupomQuantidadeLimitada object.
     *
     * @param codigo             Coupon code.
     * @param percentualDesconto Discount percentage.
     * @param maximoUtilizacoes  Maximum number of uses allowed.
     */
    public CupomQuantidadeLimitada(String codigo, double percentualDesconto, int maximoUtilizacoes) {
        super(codigo, percentualDesconto);
        this.maximoUtilizacoes = maximoUtilizacoes;
        this.utilizacoesAtuais = 0;
    }

    /**
     * Checks if the coupon can be applied based on the remaining uses.
     *
     * @param valorTotal The total value to check against (not used in this method).
     * @return True if the coupon can be applied, false otherwise.
     */
    @Override
    public boolean podeSerAplicado(double valorTotal) {
        return utilizacoesAtuais < maximoUtilizacoes;
    }

    /**
     * Applies the discount to the total value and increments the usage count.
     *
     * @param valorTotal The total value to apply the discount to.
     * @return The discounted value.
     */
    @Override
    public double aplicarDesconto(double valorTotal) {
        utilizacoesAtuais++;
        return super.aplicarDesconto(valorTotal);
    }

    /**
     * Returns the maximum number of uses allowed for the coupon.
     *
     * @return The maximum number of uses.
     */
    public int getMaximoUtilizacoes() {
        return maximoUtilizacoes;
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
     * Sets the maximum number of uses allowed for the coupon.
     *
     * @param maximoUtilizacoes The new maximum number of uses.
     */
    public void setMaximoUtilizacoes(int maximoUtilizacoes) {
        this.maximoUtilizacoes = maximoUtilizacoes;
    }
}