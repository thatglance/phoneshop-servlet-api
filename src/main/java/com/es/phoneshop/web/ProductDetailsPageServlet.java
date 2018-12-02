package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.cart.CartService;
import com.es.phoneshop.model.product.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ArrayListProductDao dao;
    private CartService cartService;

    @Override
    public void init() throws ServletException {
        super.init();

        dao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            Product product = loadProduct(request);
            if (product != null) {
                request.setAttribute("product", product);
                request.setAttribute("cart", cartService.getCart(request.getSession()).getCartItems());
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } else {
                throw new IllegalArgumentException("Product is not found!");
            }
        } catch (IllegalArgumentException e) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = loadProduct(request);
        request.setAttribute("product", product);

        request.setAttribute("cart", cartService.getCart(request.getSession()).getCartItems());

        Integer quantity = null;
        try {
            String quantityString = request.getParameter("quantity");
            quantity = Integer.valueOf(quantityString);
        } catch (NumberFormatException e) {
            request.setAttribute("quantityError", "Not a number.");
        }
        if (quantity != null && quantity <= product.getStock()) {
                cartService.addToCart(cartService.getCart(request.getSession()), product, quantity);
                //request.setAttribute("message", "Product is added successfully.");

                response.sendRedirect(request.getRequestURI() + "?message=Product is added successfully.");
        } else {
            if (quantity != null){
                request.setAttribute("quantityError", "Not enough stock.");
            }
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }
    }

    private Product loadProduct(HttpServletRequest request) {
        StringBuffer uri = request.getRequestURL();
        int lastSlashIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(lastSlashIndex + 1);
        Long id = Long.valueOf(stringId);

        return dao.getProduct(id);
    }
}
