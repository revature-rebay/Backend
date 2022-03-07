//A test. It does test things. Using Mockito and Junit.

package com.revature.service;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repo.ProductDAO;
import com.revature.repo.UserDAO;
import com.revature.utils.CartException;
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
    private User user2 = new User();
    private Product product1 = new Product();
    private Product product2 = new Product();
    private Product product3 = new Product();
    @Mock
    private ProductDAO productDAO;
    private Product product = new Product();

    @BeforeEach
    public void setUp(){
        user.setId(1);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setUserName("JDoe");
        user.setEmail("JDoe@email.com");
        user.setPassWord("password");
        user.setAdmin(false);
        product1 = new Product(1, 50.00, 0, true,100, "Product Name here", "Product Description here");
        product2 = new Product(2, 150.00, 0, true,100, "Product Name here", "Product Description here");
        product3 = new Product(3, 150.00, 0, true,5, "Product Name here", "Product Description here");

        CartItem item1 = new CartItem(1,1, product1, user);
        CartItem item2 = new CartItem(2,2, product2, user);

        user.setCart(new ArrayList<>(Arrays.asList(item1, item2)));
        MockitoAnnotations.openMocks(this);
        service = new CartService(userDao, productDAO);
        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(user));
        Mockito.when(userDao.findById(2)).thenReturn(Optional.of(user2));

        user2.setId(2);
        user2.setFirstName("Jim");
        user2.setLastName("Smith");
        user2.setUserName("jimsmith1");
        user2.setEmail("jsmtih@email.com");
        user2.setPassWord("password");
        user2.setAdmin(false);
        user2.setCart(new ArrayList<>());


        Mockito.when(productDAO.findById(1)).thenReturn(Optional.of(product1));
        Mockito.when(productDAO.findById(2)).thenReturn(Optional.of(product2));
        Mockito.when(productDAO.findById(3)).thenReturn(Optional.of(product3));
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
        cart.forEach(cartItem -> {
            System.out.println(cartItem);
        });
        assertTrue(cart.size() == 2);
        assertEquals(50.0, cart.get(0).getProduct().getProductPrice());

    }

    @Test
    public void testAddToCartFail(){

        //Test empty object
        List<CartItem> cart = service.addToCart(new CartDTO());
        assertTrue(cart.size() == 0);

        //Test invalid user id
        List<CartItem> cart2 = service.addToCart(new CartDTO(-1, 1, 1));
        assertTrue(cart2.size() == 0);

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
        cart.forEach(cartItem -> {
            System.out.println(cartItem);
        });
        assertTrue(cart.size() == 1);
        assertEquals(150.0, cart.get(0).getProduct().getProductPrice());


        //Test remaining item is the second product
        assertEquals(150.0, cart.get(0).getProduct().getProductPrice());
    }

    @Test
    public void testDeleteFail(){
        //Test invalid product Id
        List<CartItem> cart2 = service.deleteFromCart(new CartDTO(1,-1,1));
        assertTrue(cart2.size() == 2);
        assertEquals(50.0, cart2.get(0).getProduct().getProductPrice());
        assertEquals(150.0, cart2.get(1).getProduct().getProductPrice());
    }

    @Test void testDeleteFailBadUserId(){
        //Test invalid user Id
        List<CartItem> cart = service.deleteFromCart(new CartDTO(-1,1,1));
        assertNull(cart);
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
        assertTrue(cart.size() == user.getCart().size());

        List<CartItem> cart2 = service.updateProductQuantity(new CartDTO(0,1,2));
        assertTrue(cart2.size() == 0);

        List<CartItem> cart3 = service.updateProductQuantity(new CartDTO(-1,1,2));
        assertTrue(cart3.size() == 0);

        List<CartItem> cart4 = service.updateProductQuantity(new CartDTO(1,0,2));
        assertTrue(cart4.size() == user.getCart().size());

        List<CartItem> cart5 = service.updateProductQuantity(new CartDTO(1,-1,2));
        assertTrue(cart5.size() == user.getCart().size());
    }

    @Test
    public void checkoutEmptyCart(){
        List<CartItem> cart = service.checkout(user2.getId());
        assertNull(cart);
    }

    @Test
    public void checkoutCartSuccess(){
        List<CartItem> cart = service.checkout(user.getId());
        assertTrue(cart.isEmpty());
        assertTrue(user.getCart().isEmpty());
        assertEquals(99, product1.getCurrentStock());
        assertEquals(98, product2.getCurrentStock());
    }

    @Test
    void checkoutCartFailureOutOfStock(){
        CartItem item3 = new CartItem(3,10, product3, user);
        user.getCart().add(item3);
        assertThrows(CartException.class, () -> {
            service.checkout(user.getId());
        });

        try {
            service.checkout(user.getId());
        }catch (CartException e){
            List<CartItem> notInStock = e.getNotInStock();
            assertFalse(notInStock.isEmpty());
            assertTrue(notInStock.get(0).equals(item3));

        }
    }

    @Test
    void checkoutCartFailureBadUserId(){
        List<CartItem> list = service.checkout(100);
        assertNull(list);
    }

    @Test
    void checkoutCartFailureBadProduct(){
        CartItem item3 = new CartItem(92, 1, new Product(92,1.0,0,false,100,"product","product desc"), user);
        user.getCart().add(item3);

        assertThrows(RuntimeException.class, ()->{
           service.checkout(user.getId());
        });
    }
}
