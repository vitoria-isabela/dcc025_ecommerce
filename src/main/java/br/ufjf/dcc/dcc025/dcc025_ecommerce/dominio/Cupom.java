package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import com.google.gson.annotations.JsonAdapter;
import br.ufjf.dcc.dcc025.dcc025_ecommerce.persistence.CupomSerializer;
import java.util.Objects;

/**
 * Represents an abstract coupon in the e-commerce system.
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
@JsonAdapter(CupomSerializer.class)
public abstract class Cupom {
    private final String codigo;
    protected double percentualDesconto;
    private boolean ativo;

    /**
     * Constructs a Cupom object.
     *
     * @param codigo             Coupon code.
     * @param percentualDesconto Discount percentage.
     */
    public Cupom(String codigo, double percentualDesconto) {
        this.codigo = codigo;
        this.percentualDesconto = percentualDesconto;
        this.ativo = true;
    }

    /**
     * Returns the coupon code.
     *
     * @return Coupon code.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Returns the discount percentage.
     *
     * @return Discount percentage.
     */
    public double getPercentualDesconto() {
        return percentualDesconto;
    }

    /**
     * Sets the discount percentage.
     *
     * @param percentualDesconto New discount percentage.
     */
    public abstract void setPercentualDesconto(double percentualDesconto);

    /**
     * Checks if the coupon is active.
     *
     * @return True if active, false otherwise.
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Sets the activity status of the coupon.
     *
     * @param ativo New activity status.
     */
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    /**
     * Checks if the coupon can be applied to a given total value.
     *
     * @param valorTotal Total value.
     * @return True if applicable, false otherwise.
     */
    public abstract boolean podeSerAplicado(double valorTotal);

    /**
     * Applies the discount to a given total value.
     *
     * @param valorTotal Total value.
     * @return Discounted value.
     */
    public double aplicarDesconto(double valorTotal) {
        return valorTotal * (1 - percentualDesconto / 100);
    }

    /**
     * Returns a string representation of the coupon.
     *
     * @return String representation.
     */
    @Override
    public String toString() {
        return codigo + " - " + percentualDesconto + "%";
    }

    /**
     * Checks if two Cupom objects are equal.
     *
     * @param o The object to compare with.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cupom cupom = (Cupom) o;
        return Objects.equals(codigo, cupom.codigo);
    }

    /**
     * Returns the hash code of the Cupom object.
     *
     * @return Hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}