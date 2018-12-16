package com.es.phoneshop.model.order;

import com.es.phoneshop.model.AbstractDao;

public interface OrderDao extends AbstractDao<Order> {
    @Override
    Order get(Long id);

    Order get(String secureId);

    @Override
    void save(Order object);
}
