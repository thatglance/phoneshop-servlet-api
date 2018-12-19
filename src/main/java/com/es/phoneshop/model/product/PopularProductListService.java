package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PopularProductListService {
    void addView(Product product);
    List<Product> getMostPopularProducts();
}
