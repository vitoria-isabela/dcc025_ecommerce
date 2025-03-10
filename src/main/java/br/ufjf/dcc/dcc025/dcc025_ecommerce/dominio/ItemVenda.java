package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents an item in a sale transaction within the e-commerce system.
 * Contains a product and the quantity of that product being purchased.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ItemVenda {
    private final Produto produto;
    private int quantidade;

    /**
     * Constructs an ItemVenda object with a specified product and quantity.
     * 
     * @param produto    the product being purchased
     * @param quantidade the quantity of the product being purchased
     */
    public ItemVenda(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    /**
     * Returns the product associated with this sale item.
     * 
     * @return the product being purchased
     */
    public Produto getProduto() {
        return produto;
    }

    /**
     * Returns the quantity of the product being purchased.
     * 
     * @return the quantity of the product
     */
    public int getQuantidade() {
        return quantidade;
    }

    /**
     * Sets the quantity of the product being purchased.
     * 
     * @param quantidade the new quantity of the product
     */
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    /**
     * Calculates and returns the total price of this item (product price *
     * quantity).
     * 
     * @return the total price of the item
     */
    public double getTotal() {
        return produto.getPreco() * quantidade;
    }

    /**
     * Returns a string representation of the ItemVenda object, including the
     * product name, quantity, and total price.
     * 
     * @return a formatted string representing the sale item
     */
    @Override
    public String toString() {
        return produto.getNome() + " (Quantidade: " + quantidade + ", Total: R$" + getTotal() + ")";
    }

    /**
     * Calculates and returns the total price of the item without discounts.
     * 
     * @return the total price of the item without discounts
     */
    public double getTotalSemDesconto() {
        return quantidade * produto.getPreco();
    }
}