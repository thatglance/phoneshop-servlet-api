package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.entity.Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

public class Cart extends Entity implements Serializable {
    private List<CartItem> cartItems;
    private BigDecimal totalPrice;
    private Currency currency;

    public Cart() {
        cartItems = new ArrayList<>();
        totalPrice = BigDecimal.ZERO;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public String toString() {
        StringBuffer stringCart = new StringBuffer("{ ");
        cartItems.forEach(cartItem -> stringCart.append(cartItem.toString()).append(", "));
        stringCart.append("}");
        return stringCart.toString();
    }
}
