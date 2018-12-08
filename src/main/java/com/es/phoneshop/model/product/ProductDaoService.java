package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;

public interface ProductDaoService {
    Product loadProduct(HttpServletRequest request);
}
