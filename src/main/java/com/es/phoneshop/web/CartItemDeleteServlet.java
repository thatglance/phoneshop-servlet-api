package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDaoService;
import com.es.phoneshop.model.product.ProductDaoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CartItemDeleteServlet extends HttpServlet {
    private ProductDaoService productDaoService;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        cartService = CartServiceImpl.getInstance();
        productDaoService = ProductDaoServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDaoService.loadProduct(request);
        Cart cart = cartService.getCart(request.getSession());
        cartService.delete(cart, product);
        response.sendRedirect(request.getContextPath() + "/cart?message=Cart item "
                + product.getCode() + " removed successfully.");
    }
}
