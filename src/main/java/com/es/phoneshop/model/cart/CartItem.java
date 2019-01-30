package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

public class CartItem implements Serializable{
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public CartItem(CartItem cartItem) {
        quantity = cartItem.quantity;

        Product product = cartItem.product;
        long savedId = product.getId();
        this.product = new Product(
                new Long(savedId),
                new String(product.getCode()),
                new String(product.getDescription()),
                new BigDecimal(product.getPrice().toString()),
                Currency.getInstance("USD"),
                product.getStock(),
                new String(product.getImageUrl()));
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "CartItem [" + product.getCode() + ", " + quantity + "]";
    }
}
