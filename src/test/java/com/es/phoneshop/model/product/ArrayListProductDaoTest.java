package com.es.phoneshop.model.product;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class ArrayListProductDaoTest {
    private ProductDao productDao;

    @Mock
    private static ProductDao productDaoMock;

    @BeforeClass
    public static void setup() {
        //productDao = ArrayListProductDao.getInstance();
        productDaoMock = ArrayListProductDao.getInstance();

        Currency usd = Currency.getInstance("USD");
        productDaoMock.save(new Product(1L, "sgs", "Samsung S II", new BigDecimal(100), usd, 100, null));
        productDaoMock.save(new Product(2L, "sgs2", "Samsung Galaxy S", new BigDecimal(200), usd, 50, null));
        productDaoMock.save(new Product(3L, "sgs3", "Samsung Galaxy S III", new BigDecimal(300), usd, 5, null));
        productDaoMock.save(new Product(4L, "iphone", "Apple iPhone", new BigDecimal(200), usd, 10, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone.jpg"));
        productDaoMock.save(new Product(5L, "iphone6", "Apple iPhone 6", new BigDecimal(1000), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Apple/Apple%20iPhone%206.jpg"));
        productDaoMock.save(new Product(6L, "htces4g", "HTC EVO Shift 4G", new BigDecimal(320), usd, 3, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/HTC/HTC%20EVO%20Shift%204G.jpg"));
        productDaoMock.save(new Product(7L, "sec901", "Sony Ericsson C901", new BigDecimal(420), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Ericsson%20C901.jpg"));
        productDaoMock.save(new Product(8L, "xperiaxz", "Sony Xperia XZ", new BigDecimal(120), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Sony/Sony%20Xperia%20XZ.jpg"));
        productDaoMock.save(new Product(9L, "nokia3310", "Nokia 3310", new BigDecimal(70), usd, 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Nokia/Nokia%203310.jpg"));

        productDaoMock.save(new Product(10L, "palmp", "Palm Pixi", null, usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Palm/Palm%20Pixi.jpg"));

        productDaoMock.save(new Product(11L, "simc56", "Siemens C56", new BigDecimal(70), usd, 20, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C56.jpg"));
        productDaoMock.save(new Product(12L, "simc61", "Siemens C61", new BigDecimal(80), usd, 30, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20C61.jpg"));

        productDaoMock.save(new Product(13L, "simsxg75", "Siemens SXG75", new BigDecimal(150), usd, 0, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Siemens/Siemens%20SXG75.jpg"));

    }

    @Test
    public void testGetProduct() {
        assertEquals("sgs2", productDaoMock.getProduct(2L).getCode());
        assertNull(productDaoMock.getProduct(14L));
    }

    @Test
    public void testFindProductsNoParameters() {
        assertEquals(11, productDaoMock.findProducts(null, null, null).size());
    }

    @Test
    public void testFindProductsSearch() {
        List<Product> actual = productDaoMock.findProducts("S", null, null);
        assertEquals(8, actual.size());

        actual = productDaoMock.findProducts("A 6", null, null);
        assertEquals(4, actual.size());
        assertEquals("iphone6", actual.get(0).getCode());

        actual = productDaoMock.findProducts("s C 6", null, null);
        assertEquals(8, actual.size());
        assertTrue(actual.get(0).getDescription().contains("Siemens"));
        assertTrue(actual.get(1).getDescription().contains("Siemens"));
        assertEquals("sec901", actual.get(2).getCode());
    }

    @Test
    public void testFindProductsSort() {
        List<Product> actual = productDaoMock.findProducts(null, "description", "asc");
        assertEquals(11, actual.size());
        assertEquals("iphone", actual.get(0).getCode());
        assertEquals("xperiaxz", actual.get(10).getCode());

        actual = productDaoMock.findProducts(null, "description", "desc");
        assertEquals(11, actual.size());
        assertEquals("iphone", actual.get(10).getCode());
        assertEquals("xperiaxz", actual.get(0).getCode());

        actual = productDaoMock.findProducts(null, "price", "asc");
        assertEquals(11, actual.size());
        assertEquals("iphone6", actual.get(10).getCode());
        assertEquals("simc61", actual.get(2).getCode());
        assertEquals(70, actual.get(1).getPrice().intValueExact());
        assertEquals(70, actual.get(0).getPrice().intValueExact());

        actual = productDaoMock.findProducts(null, "price", "desc");
        assertEquals(11, actual.size());
        assertEquals("iphone6", actual.get(0).getCode());
        assertEquals("simc61", actual.get(8).getCode());
        assertEquals(70, actual.get(9).getPrice().intValueExact());
        assertEquals(70, actual.get(10).getPrice().intValueExact());
    }

    @Test
    public void testFindProductsSearchWithSort() {
        String query = "C 6";
        List<Product> actual = productDaoMock.findProducts(query, "description", "asc");
        assertEquals(5, actual.size());
        assertEquals("iphone6", actual.get(0).getCode());
        assertEquals("sec901", actual.get(actual.size() - 1).getCode());

        actual = productDaoMock.findProducts(query, "description", "desc");
        assertEquals(5, actual.size());
        assertEquals("sec901", actual.get(0).getCode());
        assertEquals("iphone6", actual.get(actual.size() - 1).getCode());

        actual = productDaoMock.findProducts(query, "price", "asc");
        assertEquals(5, actual.size());
        assertEquals("simc56", actual.get(0).getCode());
        assertEquals("iphone6", actual.get(actual.size() - 1).getCode());

        actual = productDaoMock.findProducts(query, "price", "desc");
        assertEquals(5, actual.size());
        assertEquals("iphone6", actual.get(0).getCode());
        assertEquals("simc56", actual.get(actual.size() - 1).getCode());
    }

    @Test
    public void testDelete() {
        assertEquals(1L, productDaoMock.getProduct(1L).getId().longValue());
        productDaoMock.delete(1L);
        assertNull(productDaoMock.getProduct(1L));
        productDaoMock.delete(14L);
        assertNull(productDaoMock.getProduct(14L));
    }

    @After
    public void restoreDao() {
        productDaoMock.save(new Product(1L, "sgs", "Samsung S II", new BigDecimal(100), Currency.getInstance("USD"), 100, null));
    }
}
