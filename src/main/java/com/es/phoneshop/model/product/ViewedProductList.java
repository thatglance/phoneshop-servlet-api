package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;

public class ViewedProductList {
    private List<Product> viewedProducts = new ArrayList<>();

    public List<Product> getViewedProducts(){
        return viewedProducts;
    }

    public void setViewedProducts(List<Product> viewedProducts) {
        this.viewedProducts = viewedProducts;
    }
}
