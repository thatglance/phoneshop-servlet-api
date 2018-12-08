package com.es.phoneshop.model.product;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao instance;
    private List<Product> productList;

    private ArrayListProductDao() {
        productList = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance() {
        ArrayListProductDao tempInstance = instance;
        if (tempInstance == null) {
            synchronized (ArrayListProductDao.class) {
                tempInstance = instance;
                if (tempInstance == null) {
                    instance = tempInstance = new ArrayListProductDao();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public synchronized Product getProduct(final Long id) {
        return productList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
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

    private List<Product> sortProducts(List<Product> products, String sortField, String sortMode) {
        Comparator<Product> comparator;

        if (sortField.equals("description")) {
            comparator = Comparator.comparing(Product::getDescription);
        } else {
            comparator = Comparator.comparing(Product::getPrice);
        }
        if (sortMode.equals("desc")) {
            comparator = comparator.reversed();
        }

        return products.stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public synchronized List<Product> findProducts(final String query, final String sortField, final String sortMode) {

        Predicate<Product> baseFilter = product -> (product.getPrice() != null) && (product.getStock() > 0);

        List<Product> foundProducts = productList.stream().filter(baseFilter).collect(Collectors.toList());
        if (query != null) {
            foundProducts = foundProducts.stream()
                    .collect(Collectors.toMap(Function.identity(), product -> matchCount(query, product)))
                    .entrySet().stream().filter(entry -> entry.getValue() > 0)
                    .sorted(Comparator.comparing(Map.Entry<Product, Integer>::getValue).reversed())
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
        }

        if (sortField != null && sortMode != null) {
            return sortProducts(foundProducts, sortField, sortMode);
        }else {
            return foundProducts;
        }
    }

    @Override
    public synchronized void save(Product product) {
        if (getProduct(product.getId()) == null) {
            productList.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        productList.removeIf(product -> product.getId().equals(id));
    }
}
