package com.es.phoneshop.model.order;

import java.util.ArrayList;
import java.util.List;

public class ArrayListOrderDao<T extends Order> extends OrderDao<T> {

    private static volatile ArrayListOrderDao<Order> instance;
    //private List<Order> orderList;

    //private long maxId;

    private ArrayListOrderDao() {
//        orderList = new ArrayList<>();
//        maxId = 0;
    }

    public static ArrayListOrderDao<Order> getInstance() {
        ArrayListOrderDao<Order> tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ArrayListOrderDao.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new ArrayListOrderDao<>();
                }
            }
        }

        return tempInstance;
    }

//    @Override
//    public synchronized void save(Order order) {
//        if (getEntity(order.getId()) == null) {
//            order.setId(maxId++);
//            orderList.add(order);
//        }
//    }

    @Override
    public synchronized T getEntity(String secureId) {
        return entities.stream().filter(entity -> secureId.equals(entity.getSecureId())).findFirst().orElse(null);
    }

    @Override
    public void saveBySecureId(T entity) {
        if (getEntity(entity.getSecureId()) == null) {
            entities.add(entity);
        }
    }
}
