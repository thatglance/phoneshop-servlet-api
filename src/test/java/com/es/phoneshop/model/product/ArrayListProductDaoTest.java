package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest
{
    private ProductDao productDao;

    @Before
    public void setup() {
        productDao = ArrayListProductDao.getInstance();
    }

    @Test
    public void testFindProductsNoResults() {
        assertTrue(productDao.findProducts(null, null, null).isEmpty());
    }

    @Test
    public void testGetProduct() {
        Product product = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200),
                Currency.getInstance("USD"), 0, "https://raw.githubusercontent.com/andrewosipenko/" +
                "phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product);
        assertEquals(product, productDao.getProduct(2L));
        assertNull(productDao.getProduct(1L));
    }

    @Test
    public void testFindProducts() {
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S", new BigDecimal(100),
                usd, 100, "https://raw.githubusercontent.com/andrewosipenko/" +
                "phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200),
                usd, 0, "https://raw.githubusercontent.com/andrewosipenko/" +
                "phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        Product product3 = new Product(3L, "sgs3", "Samsung Galaxy S III", null,
                usd, 5, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/" +
                "manufacturer/Samsung/Samsung%20Galaxy%20S%20III.jpg");
        productDao.save(product1);
        productDao.save(product2);
        productDao.save(product3);
        List<Product> expected = new ArrayList<>();
        expected.add(product1);
        List<Product> actual = productDao.findProducts("S", null, null);
        assertEquals(expected, actual);
    }

    @Test
    public void testDelete(){
        Currency usd = Currency.getInstance("USD");
        Product product1 = new Product(1L, "sgs", "Samsung Galaxy S II", new BigDecimal(100),
                usd, 100, "https://raw.githubusercontent.com/andrewosipenko/" +
                "phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg");
        Product product2 = new Product(2L, "sgs2", "Samsung Galaxy S II", new BigDecimal(200),
                usd, 0, "https://raw.githubusercontent.com/andrewosipenko/" +
                "phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S%20II.jpg");
        productDao.save(product1);
        productDao.save(product2);
        assertEquals(product1, productDao.getProduct(1L));
        productDao.delete(1L);
        assertNull(productDao.getProduct(1L));
    }
}
