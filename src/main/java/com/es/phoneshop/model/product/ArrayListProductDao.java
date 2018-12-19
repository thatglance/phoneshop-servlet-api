package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao<T extends Product> extends ProductDao<T> {

    private static volatile ArrayListProductDao<Product> instance;
    //private List<Product> productList;

    private ArrayListProductDao() {
     //   productList = new ArrayList<>();
    }

    public static ArrayListProductDao<Product> getInstance() {
        ArrayListProductDao<Product> tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ArrayListProductDao.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new ArrayListProductDao<>();
                }
            }
        }

        return tempInstance;
    }

    private int matchCount(String query, Product product) {
        String[] queryParts = query.split("\\s+");
        int coincidenceNum = 0;
        String description = product.getDescription();
        for (String part : queryParts) {
            if (description.contains(part)) {
                coincidenceNum++;
            }
        }
        return coincidenceNum;
    }

    private List<T> sortProducts(List<T> products, String sortField, String sortMode) {
        Comparator<T> comparator;

        if (sortField.equals("description")) {
            comparator = Comparator.comparing(T::getDescription);
        } else {
            comparator = Comparator.comparing(T::getPrice);
        }
        if (sortMode.equals("desc")) {
            comparator = comparator.reversed();
        }

        return products.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public synchronized List<T> findProducts(final String query, final String sortField, final String sortMode) {

        Predicate<Product> baseFilter = product -> (product.getPrice() != null) && (product.getStock() > 0);

        List<T> foundProducts = entities.stream().filter(baseFilter).collect(Collectors.toList());
        if (query != null) {
            foundProducts = foundProducts.stream()
                    .collect(Collectors.toMap(Function.identity(), product -> matchCount(query, product)))
                    .entrySet().stream().filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<T, Integer>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        if (sortField != null && sortMode != null) {
            return sortProducts(foundProducts, sortField, sortMode);
        }else {
            return foundProducts;
        }
    }
}
