package com.es.phoneshop.model.product;

import java.util.List;

public interface ProductDao {
    Product getProduct(Long id);
    List<Product> findProducts(String query, String sortField, String sortMode);
    void save(Product product);
    void delete(Long id);
}
