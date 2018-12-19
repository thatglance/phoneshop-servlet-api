package com.es.phoneshop.model.product;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PopularProductListServiceImpl implements PopularProductListService {

    private static volatile PopularProductListServiceImpl instance;
    private PopularProductList popularProductList;

    private PopularProductListServiceImpl() {
        popularProductList = new PopularProductList();
    }

    public static PopularProductListServiceImpl getInstance() {
        PopularProductListServiceImpl tempInstance = instance;
        if (tempInstance == null) {
            synchronized (PopularProductListServiceImpl.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new PopularProductListServiceImpl();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public void addView(Product product) {
        Map<Product, Integer> products = popularProductList.getPopularProducts();
        if (products.containsKey(product)) {
            Integer previousValue = products.get(product);
            products.put(product, previousValue + 1);
        } else {
            products.put(product, 1);
        }
    }

    @Override
    public List<Product> getMostPopularProducts() {
        /*
         foundProducts = foundProducts.stream()
                    .collect(Collectors.toMap(Function.identity(), product -> matchCount(query, product)))
                    .entrySet().stream().filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<T, Integer>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        */
        return popularProductList.getPopularProducts()
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry<Product, Integer>::getValue).reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
