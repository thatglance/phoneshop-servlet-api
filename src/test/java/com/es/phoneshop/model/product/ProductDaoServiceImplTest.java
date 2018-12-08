package com.es.phoneshop.model.product;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductDaoServiceImplTest {
    @Mock
    private ProductDao dao;
    @Mock
    private Product product;
    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private ProductDaoServiceImpl productDaoService;

    @Test
    public void loadProductTest() throws NumberFormatException {
        when(request.getRequestURL()).thenReturn(new StringBuffer("/1"));
        when(dao.getProduct(1L)).thenReturn(product);
        Product resultProduct = productDaoService.loadProduct(request);
        assertEquals(product, resultProduct);
    }

    @Test(expected = NumberFormatException.class)
    public void loadProductWrongIdTest() throws NumberFormatException {
        when(request.getRequestURL()).thenReturn(new StringBuffer("/jgf"));
        productDaoService.loadProduct(request);
    }
}
