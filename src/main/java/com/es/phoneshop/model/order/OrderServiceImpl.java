package com.es.phoneshop.model.order;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartItem;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderServiceImpl implements OrderService {

    private static volatile OrderServiceImpl instance;
    private OrderDao orderDao;

    private OrderServiceImpl() {
        orderDao = ArrayListOrderDao.getInstance();
    }

    public static OrderServiceImpl getInstance() {
        OrderServiceImpl tempInstance = instance;
        if (tempInstance == null) {
            synchronized (OrderServiceImpl.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new OrderServiceImpl();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public Order placeOrder(Cart cart, String name, String deliveryAddress, String phone) {
        Order order = new Order();
        order.setName(name);
        order.setDeliveryAddress(deliveryAddress);
        order.setPhone(phone);
        List<CartItem> cartItems = cart.getCartItems();
        List<CartItem> orderItems = new ArrayList<>();
        cartItems.forEach(cartItem -> {
            try {
                orderItems.add(cartItem.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
        order.setCartItems(orderItems);
        order.setTotalPrice(cart.getTotalPrice());
        order.setCurrency(cart.getCurrency());
        order.setSecureId(UUID.randomUUID().toString());
        orderDao.save(order);
        return order;
    }

    @Override
    public Order getOrder(HttpServletRequest request) throws NumberFormatException{
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(lastSlashIndex + 1);
        Long id = Long.valueOf(stringId);

        return orderDao.get(id);
    }

    @Override
    public Order getOrderBySecureId(HttpServletRequest request) {
        String uri = request.getRequestURI();
        int lastSlashIndex = uri.lastIndexOf("/");
        String secureId = uri.substring(lastSlashIndex + 1);

        return orderDao.get(secureId);
    }
}
