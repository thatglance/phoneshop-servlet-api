package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;

import javax.servlet.http.HttpSession;
import java.util.Optional;

public class CartServiceImpl implements CartService {

    private static final String CART_ATTRIBUTE = "cart";

    private static volatile CartServiceImpl instance;

    private CartServiceImpl() {
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
    public void addToCart(Cart cart, Product product, String quantityString)
            throws NumberFormatException, NotEnoughStockException {
        int quantity = Integer.valueOf(quantityString);

        Optional<CartItem> cartItemOptional = cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(product.getId())).findAny();
        if (cartItemOptional.isPresent()) {
            CartItem cartItem = cartItemOptional.get();
            if (cartItem.getQuantity() + quantity > product.getStock()) {
                throw new NotEnoughStockException("Not enough stock of product " + product.getCode() +
                        " for result quantity.");
            }
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            if (quantity > product.getStock()) {
                throw new NotEnoughStockException("Not enough stock of product " + product.getCode() + ".");
            }
            cart.getCartItems().add(new CartItem(product, quantity));
        }
    }

}
