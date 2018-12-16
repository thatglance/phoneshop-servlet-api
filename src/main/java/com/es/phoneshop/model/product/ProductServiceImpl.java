package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;

public class ProductServiceImpl implements ProductService {
    private static volatile ProductServiceImpl instance;
    private ProductDao dao;

    private ProductServiceImpl() {
        dao = ArrayListProductDao.getInstance();
    }

    public static ProductServiceImpl getInstance() {
        ProductServiceImpl tempInstance = instance;
        if (tempInstance == null){
            synchronized (ProductServiceImpl.class){
                tempInstance = instance;
                if (tempInstance == null){
                    instance = tempInstance = new ProductServiceImpl();
                }
            }
        }

        return tempInstance;
    }

    @Override
    public Product loadProduct(HttpServletRequest request) throws NumberFormatException {
        StringBuffer uri = request.getRequestURL();
        int lastSlashIndex = uri.lastIndexOf("/");
        String stringId = uri.substring(lastSlashIndex + 1);
        Long id = Long.valueOf(stringId);

        return dao.get(id);
    }

    @Override
    public Product loadProductById(String idString) {
        Long id = Long.valueOf(idString);

        return dao.get(id);
    }
}
