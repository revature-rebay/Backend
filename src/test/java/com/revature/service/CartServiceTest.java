package com.revature.service;

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
        assertNull(cart);
    }





}
