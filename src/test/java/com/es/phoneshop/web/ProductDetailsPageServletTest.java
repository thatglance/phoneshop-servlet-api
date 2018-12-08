package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductDetailsPageServletTest {

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private Product product;
    @Mock
    private ProductDaoService productDaoService;
    @Mock
    private CartService cartService;
    @Mock
    private ViewedProductListService viewedProductListService;
    @Mock
    private ViewedProductList viewedProductList;
    @Mock
    private Cart cart;

    @InjectMocks
    private ProductDetailsPageServlet servlet;


    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(viewedProductListService.getViewedProductList(session)).thenReturn(viewedProductList);
        when(cartService.getCart(session)).thenReturn(cart);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(productDaoService.loadProduct(request)).thenReturn(product);
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("viewedProductList"), any());
        verify(request).setAttribute(eq("cart"), any());
        verify(request).getRequestDispatcher("/WEB-INF/pages/productDetails.jsp");
        verify(request).setAttribute(eq("product"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProductNotFound() throws ServletException, IOException {
        when(productDaoService.loadProduct(request)).thenReturn(null);
        servlet.doGet(request, response);

        verify(response).sendError(404);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        when(productDaoService.loadProduct(request)).thenReturn(product);
        when(request.getParameter(eq("quantity"))).thenReturn("1");
        servlet.doPost(request, response);

        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).setAttribute(eq("cart"), any());
        verify(cartService).addToCart(eq(cart), eq(product), anyString());
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostotEnoughStock() throws ServletException, IOException {
        when(productDaoService.loadProduct(request)).thenReturn(product);
        when(request.getParameter(eq("quantity"))).thenReturn("khgfkhf");
        doThrow(new NotEnoughStockException("test")).when(cartService).addToCart(any(), any(), anyString());
        servlet.doPost(request, response);

        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).setAttribute(eq("cart"), any());
        verify(request).setAttribute(eq("quantityError"), eq("test"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNumberFormat() throws ServletException, IOException {
        when(productDaoService.loadProduct(request)).thenReturn(product);
        when(request.getParameter(eq("quantity"))).thenReturn("khgfkhf");
        doThrow(new NumberFormatException("test")).when(cartService).addToCart(any(), any(), anyString());
        servlet.doPost(request, response);

        verify(request).setAttribute(eq("product"), eq(product));
        verify(request).setAttribute(eq("cart"), any());
        verify(request).setAttribute(eq("quantityError"), eq("Not a number."));
        verify(requestDispatcher).forward(request, response);
    }
}
