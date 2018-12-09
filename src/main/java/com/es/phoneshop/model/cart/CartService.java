package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;

public interface CartService {
    Cart getCart(HttpSession session);
    void addToCart(Cart cart, Product product, String quantityString);
    void updateCart(Cart cart, Product product, String quantityString);
    void delete(Cart cart, Product product);
}
