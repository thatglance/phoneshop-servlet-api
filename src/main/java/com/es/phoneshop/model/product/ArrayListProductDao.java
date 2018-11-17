package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        return productsList.stream().filter(p -> (p.getPrice() != null) && (p.getStock() > 0))
                .filter(p -> query == null || p.getDescription().contains(query))
                .sorted((o1, o2) -> {
                    if(sortField!=null&&sortMode!=null){
                        if(sortField.equals("description")){
                                return sortMode.equals("asc")?o1.getDescription().compareTo(o2.getDescription())
                                        :o2.getDescription().compareTo(o1.getDescription());
                        } else if(sortField.equals("price")){
                            return sortMode.equals("asc")?o1.getPrice().compareTo(o2.getPrice())
                                    :o2.getPrice().compareTo(o1.getPrice());
                        }
                    }
                    return 0;
                })
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
