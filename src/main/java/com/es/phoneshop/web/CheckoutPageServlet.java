package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.cart.CartServiceImpl;
import com.es.phoneshop.model.order.Order;
import com.es.phoneshop.model.order.OrderService;
import com.es.phoneshop.model.order.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckoutPageServlet extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;

    @Override
    public void init() throws ServletException {
        cartService = CartServiceImpl.getInstance();
        orderService = OrderServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("cart", cartService.getCart(request.getSession()));
        request.getRequestDispatcher("WEB-INF/pages/checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = cartService.getCart(request.getSession());
        String name = request.getParameter("name");
        String deliveryAddress = request.getParameter("deliveryAddress");
        String phone = request.getParameter("phone");
        if (!name.isEmpty() && !deliveryAddress.isEmpty() && !phone.isEmpty()) {
            Order order = orderService.placeOrder(cart, name, deliveryAddress, phone);
            cartService.clearCart(cart);
            response.sendRedirect(request.getContextPath() + "/orderOverview/" + order.getSecureId());
        } else {
            request.setAttribute("inputError", "All fields must be filled!");
            request.getRequestDispatcher("WEB-INF/pages/checkout.jsp").forward(request, response);
        }

    }
}
