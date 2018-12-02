package com.es.phoneshop.model.product.cart;

import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final String CART_ATTRIBUTE = "cart";

    private static volatile CartServiceImpl instance;
    //private Cart cart;

    private CartServiceImpl() {
        //cart = new Cart();
    }

    public static CartServiceImpl getInstance() {
        CartServiceImpl tempInstance = instance;
        if (tempInstance == null) {
            synchronized (CartServiceImpl.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new CartServiceImpl();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public Cart getCart(HttpSession session) {
        Cart cart = (Cart) session.getAttribute(CART_ATTRIBUTE);
        if (cart == null) {
            cart = new Cart();
            session.setAttribute(CART_ATTRIBUTE, cart);
        }
        return cart;
    }

    @Override
    public void addToCart(Cart cart, Product product, Integer quantity) {
        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId())).findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

}
