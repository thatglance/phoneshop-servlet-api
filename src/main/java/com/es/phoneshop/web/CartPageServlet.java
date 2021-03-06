package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class CartPageServlet extends HttpServlet {
    private CartService cartService;
    private ProductService productService;

    @Override
    public void init() throws ServletException {
        super.init();

        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productIds = request.getParameterValues("productId");
        String[] quantities = request.getParameterValues("quantity");
        Cart cart = cartService.getCart(request.getSession());
        Map<Long, String> quantityErrors = new HashMap<>();

        int productIdsNum = (productIds == null ? 0 : productIds.length);
        for (int i = 0; i < productIdsNum; i++) {
            Product product = productService.loadProductById(productIds[i]);
            try {
                cartService.updateCart(cart, product, quantities[i]);
            } catch (NumberFormatException e) {
                quantityErrors.put(product.getId(), "Not a number.");
            } catch (NotEnoughStockException | NoSuchElementException | IllegalArgumentException e) {
                quantityErrors.put(product.getId(), e.getMessage());
            }
        }
        request.setAttribute("quantityErrors", quantityErrors);

        if (quantityErrors.isEmpty()) {
            String message = "?message=Cart updated successfully.";
//            if (!cart.getCartItems().isEmpty()) {
//                message = "?message=Cart updated successfully.";
//            } else {
//                message = "?message=Cart is empty.";
//            }
            response.sendRedirect(request.getRequestURI() + message);
        } else {
            request.setAttribute("cart", cart);
            request.getRequestDispatcher("/WEB-INF/pages/cart.jsp").forward(request, response);
        }
    }
}
