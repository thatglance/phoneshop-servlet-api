package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao instance;
    private List<Product> productsList;

    private ArrayListProductDao() {
        productsList = new ArrayList<>();
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
        return productsList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<Product> findProducts(final String query, final String sortField, final String sortMode) {

        Comparator<Product> comparator = Comparator.comparing(Product::getId);

        Predicate<Product> filter = product -> (product.getPrice() != null) && (product.getStock() > 0);

        if (query != null) {
            String[] queryParts = query.split("\\s+");

            filter = filter.and(product -> {
                for (String part : queryParts) {
                    if (product.getDescription().contains(part)) {
                        return true;
                    }
                }
                return false;
            });

            comparator = (product1, product2) -> {
                int coincidenceNum1 = 0, coincidenceNum2 = 0;
                for (String part : queryParts) {
                    if (product1.getDescription().contains(part)) {
                        coincidenceNum1++;
                    }
                    if (product2.getDescription().contains(part)) {
                        coincidenceNum2++;
                    }
                }
                return Integer.compare(coincidenceNum2, coincidenceNum1);
            };
        }

        if (sortField != null && sortMode != null) {
            if (sortField.equals("description")) {
                comparator = Comparator.comparing(Product::getDescription);
            } else {
                comparator = Comparator.comparing(Product::getPrice);
            }
            if (sortMode.equals("desc")) {
                comparator = comparator.reversed();
            }
        }

        return productsList.stream().filter(filter)
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    @Override
    public synchronized void save(Product product) {
        if (getProduct(product.getId()) == null) {
            productsList.add(product);
        }
    }

    @Override
    public synchronized void delete(Long id) {
        for (int i = 0; i < productsList.size(); i++) {
            if (productsList.get(i).getId().equals(id)) {
                productsList.remove(i);
            }
        }
    }
}
