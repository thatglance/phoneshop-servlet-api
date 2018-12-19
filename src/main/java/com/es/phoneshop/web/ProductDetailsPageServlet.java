package com.es.phoneshop.web;

import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.*;
import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductDetailsPageServlet extends HttpServlet {

    private ProductService productService;
    private CartService cartService;
    private ViewedProductListService viewedProductListService;

    @Override
    public void init() throws ServletException {
        super.init();

        cartService = CartServiceImpl.getInstance();
        productService = ProductServiceImpl.getInstance();
        viewedProductListService = ViewedProductListServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Product product = productService.loadProduct(request);
            if (product != null) {
                ViewedProductList viewedProductList = viewedProductListService.getViewedProductList(request.getSession());
                viewedProductListService.addViewedProduct(viewedProductList, product);
                request.setAttribute("viewedProductList", viewedProductList.getViewedProducts());

                request.setAttribute("product", product);
                request.setAttribute("cart", cartService.getCart(request.getSession()));//.getCartItems());
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
        Product product = productService.loadProduct(request);
        request.setAttribute("product", product);

        Cart cart = cartService.getCart(request.getSession());
        request.setAttribute("cart", cart.getCartItems());

        ViewedProductList viewedProductList = viewedProductListService.getViewedProductList(request.getSession());
        request.setAttribute("viewedProductList", viewedProductList.getViewedProducts());

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
