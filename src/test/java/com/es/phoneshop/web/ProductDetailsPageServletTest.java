package com.es.phoneshop.web;

import com.es.phoneshop.model.product.ArrayListProductDao;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockSettings;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private static HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private static RequestDispatcher requestDispatcher;

    private ProductDetailsPageServlet servlet = new ProductDetailsPageServlet();

    @Before
    public void setup() {
        ProductDao dao = ArrayListProductDao.getInstance();
        dao.save(new Product(1L, "sgs", "Samsung Galaxy S II", new BigDecimal(100), Currency.getInstance("USD"), 100, "https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/manufacturer/Samsung/Samsung%20Galaxy%20S.jpg"));
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestURI()).thenReturn("/1");
        servlet.init();
        servlet.doGet(request, response);


        verify(request).getRequestURI();
        verify(request).setAttribute(eq("product"), any());
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetWithWrongId() throws ServletException, IOException {
        servlet.init();

        when(request.getRequestURI()).thenReturn("/hgkdh");
        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoGetProductNotFound() throws ServletException, IOException {
        servlet.init();

        when(request.getRequestURI()).thenReturn("/2");
        servlet.doGet(request, response);

        verify(response).sendError(404);
    }
}
