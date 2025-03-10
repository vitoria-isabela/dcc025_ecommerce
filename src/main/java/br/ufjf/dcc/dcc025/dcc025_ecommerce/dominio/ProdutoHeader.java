package br.ufjf.dcc.dcc025.dcc025_ecommerce.dominio;

/**
 * Represents a header product in the e-commerce system.
 * Extends the Produto class but serves as a header, not a purchasable product.
 * 
 * @Author: Vitoria Isabela de Oliveira - 202065097C
 */
public class ProdutoHeader extends Produto {
    private final String title;

    /**
     * Constructs a ProdutoHeader object with a specified title.
     * The superclass constructor is called with dummy values, as they are not
     * relevant for headers.
     * 
     * @param title the title of the header product
     */
    public ProdutoHeader(String title) {
        super("", title, 0.0); // Dummy values
        this.title = title;
    }

    /**
     * Returns the type of the product, which is always "Header".
     * 
     * @return the string "Header"
     */
    @Override
    public String getTipo() {
        return "Header";
    }

    /**
     * Returns a string representation of the ProdutoHeader object, which is its
     * title.
     * 
     * @return the title of the header product
     */
    @Override
    public String toString() {
        return title;
    }
}