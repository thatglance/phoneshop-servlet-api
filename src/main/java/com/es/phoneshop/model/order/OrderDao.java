package com.es.phoneshop.model.order;

import com.es.phoneshop.model.entity.AbstractDaoImpl;

public abstract class OrderDao<T extends Order> extends AbstractDaoImpl<T> {
    public abstract T getEntity(String secureId);

    public abstract void saveBySecureId(T entity);
}
