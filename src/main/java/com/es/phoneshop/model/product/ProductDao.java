package com.es.phoneshop.model.product;

import com.es.phoneshop.model.entity.AbstractDaoImpl;

import java.util.List;

public abstract class ProductDao<T extends Product> extends AbstractDaoImpl<T> {
    public abstract List<T> findProducts(String query, String sortField, String sortMode);
}
