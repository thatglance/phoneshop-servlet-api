package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.product.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private ProductService productService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productService.loadProduct(request);
        Cart cart = cartService.getCart(request.getSession());
        cartService.delete(cart, product);
        response.sendRedirect(request.getContextPath() + "/cart?message=Cart item "
                + product.getCode() + " removed successfully.");
    }
}
