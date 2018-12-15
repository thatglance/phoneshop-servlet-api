package com.es.phoneshop.web;

import com.es.phoneshop.model.cart.Cart;
import com.es.phoneshop.model.cart.CartService;
import com.es.phoneshop.model.product.Product;
import com.es.phoneshop.model.product.ProductService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CartPageServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private ProductService productService;
    @Mock
    private CartService cartService;
    @Mock
    private Product product;
    @Mock
    private Cart cart;

    private String[] productIds;

    private String[] quantities;

    @InjectMocks
    private CartPageServlet servlet;

    @Before
    public void setup() {
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getSession()).thenReturn(session);
        when(cartService.getCart(session)).thenReturn(cart);

        quantities = new String[1];
        productIds = new String[1];
        productIds[0] = "1";

        when(productService.loadProductById(anyString())).thenReturn(product);
        when(request.getParameterValues(eq("productId"))).thenReturn(productIds);
        when(request.getParameterValues(eq("quantity"))).thenReturn(quantities);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        servlet.doGet(request, response);

        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException {
        quantities[0] = "1";

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("quantityErrors"), any());
        verify(response).sendRedirect(anyString());
    }

    @Test
    public void testDoPostNotANumber() throws ServletException, IOException {
        quantities[0] = "text";
        doThrow(new NumberFormatException()).when(cartService).updateCart(eq(cart), eq(product), eq(quantities[0]));
        servlet.doPost(request, response);

        verify(request).setAttribute(eq("quantityErrors"), any());
        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPostNegativeQuantity() throws ServletException, IOException {
        quantities[0] = "-1";
        doThrow(new IllegalArgumentException()).when(cartService).updateCart(eq(cart), eq(product), eq(quantities[0]));
        servlet.doPost(request, response);

        verify(request).setAttribute(eq("quantityErrors"), any());
        verify(request).setAttribute(eq("cart"), eq(cart));
        verify(request).getRequestDispatcher("/WEB-INF/pages/cart.jsp");
        verify(requestDispatcher).forward(request, response);
    }
}
