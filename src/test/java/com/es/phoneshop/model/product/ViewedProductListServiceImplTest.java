package com.es.phoneshop.model.product;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ViewedProductListServiceImplTest {
    @Mock
    private ViewedProductList viewedProductList;
    @Mock
    private Product product1;
    @Mock
    private Product product2;
    @Mock
    private Product product3;
    @Mock
    private Product product4;
    @Mock
    private HttpSession session;

    private List<Product> actual;
    private ViewedProductListService viewedProductListService = ViewedProductListServiceImpl.getInstance();

    @Before
    public void setup() {
        when(session.getAttribute(eq("viewedProductList"))).thenReturn(viewedProductList);
        when(product1.getId()).thenReturn(1L);
        when(product2.getId()).thenReturn(2L);
        when(product3.getId()).thenReturn(3L);
        when(product4.getId()).thenReturn(4L);
        actual = new ArrayList<>();
        when(viewedProductList.getViewedProducts()).thenReturn(actual);
    }

    @Test
    public void getViewedProductListTest() {
        ViewedProductList result = viewedProductListService.getViewedProductList(session);
        assertEquals(viewedProductList, result);
    }
    @Test
    public void getViewedProductListNullTest() {
        when(session.getAttribute(eq("viewedProductList"))).thenReturn(null);

        ViewedProductList result = viewedProductListService.getViewedProductList(session);
        assertNotEquals(viewedProductList, result);
        verify(session).setAttribute(eq("viewedProductList"), any());
    }

    @Test
    public void addViewedProductSingleProductTest() {
        when(viewedProductList.getViewedProducts()).thenReturn(actual);
        viewedProductListService.addViewedProduct(viewedProductList, product1);
        assertEquals(product1, actual.get(0));
    }
    @Test
    public void addViewedProductThreeProductsTest() {
        when(viewedProductList.getViewedProducts()).thenReturn(actual);
        viewedProductListService.addViewedProduct(viewedProductList, product1);
        viewedProductListService.addViewedProduct(viewedProductList, product2);
        viewedProductListService.addViewedProduct(viewedProductList, product3);
        assertEquals(3, actual.size());
        assertEquals(product1, actual.get(2));
        assertEquals(product2, actual.get(1));
        assertEquals(product3, actual.get(0));
    }
    @Test
    public void addViewedProductFourProductsTest() {
        viewedProductListService.addViewedProduct(viewedProductList, product1);
        viewedProductListService.addViewedProduct(viewedProductList, product2);
        viewedProductListService.addViewedProduct(viewedProductList, product3);
        viewedProductListService.addViewedProduct(viewedProductList, product4);
        assertEquals(3, actual.size());
        assertEquals(product4, actual.get(0));
        assertEquals(product2, actual.get(2));
        assertEquals(product3, actual.get(1));
    }
}
