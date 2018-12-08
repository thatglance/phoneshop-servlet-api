package com.es.phoneshop.model.product;

import javax.servlet.http.HttpSession;
import java.util.List;

public class ViewedProductListServiceImpl implements ViewedProductListService {
    private static final String VIEWED_PRODUCT_LIST_ATTRIBUTE = "viewedProductList";

    private static volatile ViewedProductListServiceImpl instance;

    private ViewedProductListServiceImpl() {
    }

    public static ViewedProductListServiceImpl getInstance() {
        ViewedProductListServiceImpl tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ViewedProductListServiceImpl.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new ViewedProductListServiceImpl();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public void addViewedProduct(ViewedProductList viewedProductList, Product product) {
        List<Product> viewedProducts = viewedProductList.getViewedProducts();
        Long id = product.getId();

        viewedProducts.removeIf(product1 -> product1.getId().equals(id));
        if (viewedProducts.size() >= 3) {
            viewedProducts.remove(viewedProducts.size() - 1);
        }
        viewedProducts.add(0, product);
    }

    @Override
    public ViewedProductList getViewedProductList(HttpSession session) {
        ViewedProductList viewedProductList = (ViewedProductList) session.getAttribute(VIEWED_PRODUCT_LIST_ATTRIBUTE);
        if (viewedProductList == null) {
            viewedProductList = new ViewedProductList();
            session.setAttribute(VIEWED_PRODUCT_LIST_ATTRIBUTE, viewedProductList);
        }

        return viewedProductList;
    }
}
