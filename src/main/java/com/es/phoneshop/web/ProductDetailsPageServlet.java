package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.product.ProductDaoService;
import com.es.phoneshop.model.product.ProductDaoServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    //private ArrayListProductDao dao;
    private CartService cartService;
    private ProductDaoService productDaoService;

    @Override
    public void init() throws ServletException {
        super.init();

        //dao = ArrayListProductDao.getInstance();
        cartService = CartServiceImpl.getInstance();
        productDaoService = ProductDaoServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productDaoService.loadProduct(request);
            if (product != null) {
                request.setAttribute("product", product);
                request.setAttribute("cart", cartService.getCart(request.getSession()).getCartItems());
                request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
            } else {
                response.sendError(404);
            }
        } catch (NumberFormatException e) {
            response.sendError(404);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = productDaoService.loadProduct(request);
        request.setAttribute("product", product);

        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute("cart", cart.getCartItems());

        String quantityString = request.getParameter("quantity");

        try {
            cartService.addToCart(cart, product, quantityString);
            response.sendRedirect(request.getRequestURI() + "?message=Product is added successfully.");
        } catch (NumberFormatException e) {
            request.setAttribute("quantityError", "Not a number.");
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        } catch (NotEnoughStockException e) {
            request.setAttribute("quantityError", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/pages/productDetails.jsp").forward(request, response);
        }
    }
}
