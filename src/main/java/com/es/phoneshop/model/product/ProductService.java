package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;

public interface ProductService {
    Product loadProduct(HttpServletRequest request);
    Product loadProductById(String idString);
}
