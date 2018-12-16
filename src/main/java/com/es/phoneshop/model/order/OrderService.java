package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;

import javax.servlet.http.HttpServletRequest;

public interface OrderService {
    Order placeOrder (Cart cart, String name, String deliveryAddress, String phone);
    Order getOrder(HttpServletRequest request);
    Order getOrderBySecureId(HttpServletRequest request);
}
