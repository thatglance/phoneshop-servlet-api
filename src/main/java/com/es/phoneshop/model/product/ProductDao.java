package com.es.phoneshop.model.product;

import com.es.phoneshop.model.AbstractDao;

import java.util.List;

public interface ProductDao extends AbstractDao<Product> {
    //Product get(Long id);

    @Override
    Product get(Long id);

    @Override
    void save(Product object);

    List<Product> findProducts(String query, String sortField, String sortMode);
    //void save(Product product);
    void delete(Long id);
}
