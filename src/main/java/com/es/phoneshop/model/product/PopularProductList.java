package com.es.phoneshop.model.product;

import java.util.HashMap;
import java.util.Map;

public class PopularProductList {
    private Map<Product, Integer> popularProducts;

    public PopularProductList() {
        popularProducts = new HashMap<>();
    }

    public Map<Product, Integer> getPopularProducts() {
        return popularProducts;
    }

    public void setPopularProducts(Map<Product, Integer> popularProducts) {
        this.popularProducts = popularProducts;
    }
}
