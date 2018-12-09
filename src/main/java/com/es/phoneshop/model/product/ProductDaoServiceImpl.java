package com.es.phoneshop.model.product;

import javax.servlet.http.HttpServletRequest;

public class ProductDaoServiceImpl implements ProductDaoService {
    private static volatile ProductDaoServiceImpl instance;
    private ProductDao dao;

    private ProductDaoServiceImpl() {
        dao = ArrayListProductDao.getInstance();
    }

    public static ProductDaoServiceImpl getInstance() {
        ProductDaoServiceImpl tempInstance = instance;
        if (tempInstance == null){
            synchronized (ProductDaoServiceImpl.class){
                tempInstance = instance;
                if (tempInstance == null){
                    instance = tempInstance = new ProductDaoServiceImpl();
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

        return dao.getProduct(id);
    }

    @Override
    public Product loadProductById(String idString) {
        Long id = Long.valueOf(idString);

        return dao.getProduct(id);
    }
}
