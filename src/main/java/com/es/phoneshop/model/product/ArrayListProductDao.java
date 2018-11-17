package com.es.phoneshop.model.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ArrayListProductDao implements ProductDao {

    private static volatile ArrayListProductDao instance;
    private List<Product> productsList;

    private ArrayListProductDao(){
        productsList = new ArrayList<>();
    }

    public static ArrayListProductDao getInstance(){
        ArrayListProductDao tempInstance = instance;
        if (tempInstance == null){
            synchronized (ArrayListProductDao.class){
                tempInstance = instance;
                if (tempInstance == null){
                    instance = tempInstance = new ArrayListProductDao();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public synchronized Product getProduct(Long id) {
        return productsList.stream().filter(p -> p.getId().equals(id)).findFirst().orElse(null);
    }

    @Override
    public synchronized List<Product> findProducts() {
        return productsList.stream().filter(p -> (p.getPrice() != null) && (p.getStock() > 0)).
                collect(Collectors.toList());
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
