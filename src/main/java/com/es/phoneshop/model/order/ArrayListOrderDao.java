package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao implements OrderDao {
    private static volatile ArrayListOrderDao instance;
    private List<Order> orderList;

    private long maxId;

    private ArrayListOrderDao() {
        orderList = new ArrayList<>();
        maxId = 0;
    }

    public static ArrayListOrderDao getInstance() {
        ArrayListOrderDao tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ArrayListOrderDao.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new ArrayListOrderDao();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public synchronized Order get(Long id) {
        return orderList.stream().filter(order -> order.getId().equals(id)).findAny().orElse(null);
    }

    @Override
    public synchronized void save(Order order) {
        if (get(order.getId()) == null) {
            order.setId(maxId++);
            orderList.add(order);
        }
    }

    @Override
    public Order get(String secureId) {
        return orderList.stream().filter(order -> secureId.equals(order.getSecureId())).findAny().orElse(null);
    }
}
