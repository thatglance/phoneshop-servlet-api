package com.es.phoneshop.model.product;

import javax.servlet.http.HttpSession;

public interface ViewedProductListService {
    void addViewedProduct(ViewedProductList viewedProductList, Product product);
    ViewedProductList getViewedProductList(HttpSession session);
}
