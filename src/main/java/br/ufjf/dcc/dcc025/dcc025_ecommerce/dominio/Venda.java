package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

import br.ufjf.dcc.dcc025.dcc025_ecommerce.excecoes.CupomInvalidoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a sale transaction in the e-commerce system.
 * Contains information about the customer, purchased items, applied coupons,
 * and sale date.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class Venda {
    private static int contador = 1;
    private String id;
    private Cliente cliente;
    private List<ItemVenda> itens;
    private Cupom cupom;
    private LocalDate data;
    private String formaPagamento;

    /**
     * Constructs a Venda object.
     * Initializes the sale with a unique ID, an empty item list, and the current
     * date.
     */
    public Venda() {
        this.id = "VENDA-" + contador++;
        this.itens = new ArrayList<>();
        this.data = LocalDate.now();
    }

    /**
     * Adds an item to the sale with the specified product and quantity.
     * 
     * @param produto    the product to be added
     * @param quantidade the quantity of the product
     */
    public void adicionarItem(Produto produto, int quantidade) {
        itens.add(new ItemVenda(produto, quantidade));
    }

    /**
     * Removes an item from the sale that matches the given product.
     * 
     * @param produto the product to be removed from the sale
     */
    public void removerItem(Produto produto) {
        itens.removeIf(item -> item.getProduto().equals(produto));
    }

    /**
     * Calculates the total price of the sale, applying any applicable coupon
     * discounts.
     * 
     * @return the total price of the sale
     * @throws CupomInvalidoException if the applied coupon is invalid
     */
    public double calcularTotal() throws CupomInvalidoException {
        double total = itens.stream().mapToDouble(ItemVenda::getTotal).sum();
        if (cupom != null) {
            total = cupom.aplicarDesconto(total);
        }
        return total;
    }

    /**
     * Sets the ID of the sale.
     * 
     * @param id the new sale ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Returns the unique ID of the sale.
     * 
     * @return the sale ID
     */
    public String getId() {
        return id;
    }

    /**
     * Returns a copy of the list of items in the sale.
     * 
     * @return a copy of the item list
     */
    public List<ItemVenda> getItens() {
        return new ArrayList<>(itens);
    }

    /**
     * Returns the coupon applied to the sale, or null if no coupon is applied.
     * 
     * @return the applied coupon or null
     */
    public Cupom getCupom() {
        return cupom;
    }

    /**
     * Applies a coupon to the sale.
     * 
     * @param cupom the coupon to be applied
     */
    public void setCupom(Cupom cupom) {
        this.cupom = cupom;
    }

    /**
     * Returns the date of the sale.
     * 
     * @return the sale date
     */
    public LocalDate getData() {
        return data;
    }

    /**
     * Sets the date of the sale.
     * 
     * @param data the new sale date
     */
    public void setData(LocalDate data) {
        this.data = data;
    }

    /**
     * Returns the customer associated with this sale.
     * 
     * @return the customer object
     */
    public Cliente getCliente() {
        return cliente;
    }

    /**
     * Sets the customer for this sale.
     * 
     * @param cliente the customer object to associate with the sale
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * Returns the total price of the sale, handling potential coupon exceptions.
     * 
     * @return the calculated total price or 0 if an exception occurs
     */
    public double getTotal() {
        try {
            return calcularTotal();
        } catch (CupomInvalidoException e) {
            return 0;
        }
    }

    /**
     * Calculates and returns the total price of all items in the sale without
     * applying any discounts.
     * 
     * @return the total price of all items without discounts
     */
    public double getTotalSemDesconto() {
        return calcularTotalSemDesconto();
    }

    /**
     * Calculates the total price of all items without any discounts applied.
     * 
     * @return the sum of item totals without discounts
     */
    public double calcularTotalSemDesconto() {
        return itens.stream().mapToDouble(ItemVenda::getTotalSemDesconto).sum();
    }

    /**
     * Returns the payment method used for the sale.
     * 
     * @return the payment method
     */
    public String getFormaPagamento() {
        return formaPagamento;
    }

    /**
     * Sets the payment method used for the sale.
     * 
     * @param formaPagamento the payment method
     */
    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }
}