package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
import com.es.phoneshop.model.product.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class QuickOrderEntryServlet extends HttpServlet {
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
        request.getRequestDispatcher("/WEB-INF/pages/quickOrder.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] productCodes = request.getParameterValues("productCode");
        String[] quantities = request.getParameterValues("quantity");
        Cart cart = cartService.getCart(request.getSession());
        Map<Long, String> quantityErrors = new HashMap<>();

        int productsNum = (productCodes == null ? 0 : productCodes.length);

        for (int i = 0; i < productsNum; i++) {
            if (!productCodes[i].isEmpty()) {
                Product product = productService.loadProductByCode(productCodes[i]);
                try {
//                cartService.updateCart(cart, product, quantities[i]);
                    cartService.addToCart(cart, product, quantities[i]);
                } catch (NotEnoughStockException | NoSuchElementException | IllegalArgumentException e) {
                    quantityErrors.put(product.getId(), e.getMessage());
                }
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
            request.getRequestDispatcher("/WEB-INF/pages/quickOrder.jsp").forward(request, response);
            //response.sendRedirect(request.getRequestURI());
        }
    }
}
