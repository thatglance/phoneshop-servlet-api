package com.es.phoneshop.model.product;

import java.util.List;

public interface PopularProductListService {
    void addView(Product product);
    List<Product> getMostPopularProducts();
}
