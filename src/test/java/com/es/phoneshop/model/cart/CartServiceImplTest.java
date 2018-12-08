package com.es.phoneshop.model.cart;

import com.es.phoneshop.model.exception.NotEnoughStockException;
import com.es.phoneshop.model.product.Product;
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
public class CartServiceImplTest {
    @Mock
    private HttpSession session;
    @Mock
    private Cart cart;
    @Mock
    private Product product;

    private List<CartItem> actual;
    private CartService cartService = CartServiceImpl.getInstance();

    @Before
    public void setup() {
        when(session.getAttribute(eq("cart"))).thenReturn(cart);
        actual = new ArrayList<>();
        when(cart.getCartItems()).thenReturn(actual);
        when(product.getStock()).thenReturn(100);
    }

    @Test
    public void getCartTest() {
        Cart result = cartService.getCart(session);
        assertEquals(cart, result);
    }

    @Test
    public void getCartNullTest() {
        when(session.getAttribute(eq("cart"))).thenReturn(null);

        Cart result = cartService.getCart(session);
        assertNotEquals(cart, result);
        verify(session).setAttribute(eq("cart"), any());
    }

    @Test
    public void addToCartTest() throws NumberFormatException, NotEnoughStockException {
        cartService.addToCart(cart, product, "1");
        assertEquals(product, actual.get(0).getProduct());
        assertEquals(1, actual.get(0).getQuantity());
    }
    @Test(expected = NotEnoughStockException.class)
    public void addToCartNotEnoughStockTest() throws NumberFormatException, NotEnoughStockException {
        cartService.addToCart(cart, product, "500");
    }
    @Test(expected = NotEnoughStockException.class)
    public void addToCartNotEnoughResultStockTest() throws NumberFormatException, NotEnoughStockException {
        cartService.addToCart(cart, product, "50");
        cartService.addToCart(cart, product, "51");
    }
}
