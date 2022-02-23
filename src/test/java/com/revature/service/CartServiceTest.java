package com.revature.service;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repo.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CartServiceTest {
    private CartService service;
    @Mock
    private UserDAO userDao;
    private User user = new User();

    @BeforeEach
    public void setUp(){
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("JDoe");
        user.setEmail("JDoe@email.com");
        user.setPassWord("password");
        user.setRoleId(1);
        Product product1 = new Product(1, 50.00, 0, true,100, "".getBytes());
        Product product2 = new Product(2, 150.00, 0, true,100, "".getBytes());

        CartItem item1 = new CartItem(1,1, product1);
        CartItem item2 = new CartItem(2,2, product2);

        user.setCart(Arrays.asList(item1, item2));
        MockitoAnnotations.openMocks(this);
        service = new CartService(userDao);
        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(user));
    }

    @Test
    @DisplayName("Test getCart")
    public void testGetCart(){
        List<CartItem> cart = service.getCart(user.getId());
        assertNotNull(cart);
        assertEquals(1, cart.get(0).getQuantity());
        assertEquals(50.00, cart.get(0).getProduct().getProductPrice());

        assertEquals(2, cart.get(1).getQuantity());
    }

    @Test
    @DisplayName("Test getCart - invalid user")
    public void testGetCartFail(){
        List<CartItem> cart = service.getCart(-7);
        assertNotNull(cart);
        assertTrue(cart.isEmpty());

        List<CartItem> cart2 = service.getCart(0);
        assertNotNull(cart2);
        assertTrue(cart2.isEmpty());
    }

    @Test
    public void testAddToCartSuccess(){
        List<CartItem> cart = service.addToCart(new CartDTO(1,1,1));
        assertTrue(cart.size() == 3);
    }

    @Test
    public void testAddToCartFail(){

        //Test empty object
        List<CartItem> cart = service.addToCart(new CartDTO());
        assertTrue(cart.size() == 2);

        assertEquals(50.0, cart.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart.get(1).getProduct().getProductPrice());

        //Test invalid user id
        List<CartItem> cart2 = service.addToCart(new CartDTO(-1, 1, 1));
        assertTrue(cart2.size() == 2);
        assertEquals(50.0, cart2.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart2.get(1).getProduct().getProductPrice());

        //test invalid item id
        List<CartItem> cart3 = service.addToCart(new CartDTO(1, -1, 1));
        assertTrue(cart3.size() == 2);
        assertEquals(50.0, cart3.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart3.get(1).getProduct().getProductPrice());

        //test 0 quantity
        List<CartItem> cart4 = service.addToCart(new CartDTO(1, -1, 0));
        assertTrue(cart4.size() == 2);
        assertEquals(50.0, cart4.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart4.get(1).getProduct().getProductPrice());

        //test too many items
        List<CartItem> cart5 = service.addToCart(new CartDTO(1, 1, 9999));
        assertTrue(cart5.size() == 2);
        assertEquals(50.0, cart5.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart5.get(1).getProduct().getProductPrice());
    }

    @Test
    public void testDeletePass(){
        //Test deleting first product
        List<CartItem> cart = service.deleteFromCart(new CartDTO(1,1,1));
        assertTrue(cart.size() == 1);

        //Test remaining item is the second product
        assertEquals(150.0, cart.get(0).getProduct().getProductPrice());
    }

    @Test
    public void testDeleteFail(){
        //Test invalid user Id
        List<CartItem> cart = service.deleteFromCart(new CartDTO(-1,1,1));
        assertTrue(cart.size() == 2);
        assertEquals(50.0, cart.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart.get(1).getProduct().getProductPrice());

        //Test invalid product Id
        List<CartItem> cart2 = service.deleteFromCart(new CartDTO(1,-1,1));
        assertTrue(cart2.size() == 2);
        assertEquals(50.0, cart2.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart2.get(1).getProduct().getProductPrice());
    }

    @Test
    public void testClearPass(){
        assertTrue(service.clearCart(1));
    }

    @Test
    public void testClearFail(){
        assertFalse(service.clearCart(-1));
        assertFalse(service.clearCart(0));
    }

    @Test
    public void updateProductQuantityPass(){
        List<CartItem> cart = service.updateProductQuantity(new CartDTO(1,1,2));
        assertTrue(cart.size() == 2);
        assertEquals(2, cart.get(1).getQuantity());
    }

    @Test
    public void updateProductQuantityFail(){
        List<CartItem> cart = service.updateProductQuantity(new CartDTO(1,1,-1));
        assertTrue(cart.size() == 2);
        assertEquals(1, cart.get(1).getQuantity());

        List<CartItem> cart2 = service.updateProductQuantity(new CartDTO(0,1,2));
        assertTrue(cart2.size() == 0);

        List<CartItem> cart3 = service.updateProductQuantity(new CartDTO(-1,1,2));
        assertTrue(cart3.size() == 0);

        List<CartItem> cart4 = service.updateProductQuantity(new CartDTO(1,0,2));
        assertTrue(cart4.size() == 0);

        List<CartItem> cart5 = service.updateProductQuantity(new CartDTO(1,-1,2));
        assertTrue(cart5.size() == 0);
    }
}
