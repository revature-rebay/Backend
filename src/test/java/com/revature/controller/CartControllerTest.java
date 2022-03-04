package com.revature.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.service.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CartController.class)
public class CartControllerTest {

    @MockBean
    private CartService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objMapper;

    private User user = new User();
    private User user2 = new User();
    private Product product1 = new Product();
    private Product product2 = new Product();
    private Product product3 = new Product();
    private List<CartItem> cart = new ArrayList<>();
    private CartDTO cartDTO1 = new CartDTO();
    private CartDTO cartDTO2 = new CartDTO();

    @BeforeEach
    void setUp(){
        //User 1 Setup
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

        cart.addAll(Arrays.asList(item1,item2));
        user.setCart(cart);

        //User 2 Setup
        user2.setId(2);
        user2.setFirstName("Jim");
        user2.setLastName("Smith");
        user2.setUserName("jimsmith1");
        user2.setEmail("jsmtih@email.com");
        user2.setPassWord("password");
        user2.setAdmin(false);
        user2.setCart(new ArrayList<>());


        MockitoAnnotations.openMocks(this);

        //Get Cart Mocks
        Mockito.when(service.getCart(1)).thenReturn(cart);
        Mockito.when(service.getCart(2)).thenReturn(new ArrayList<>());

        //Clear Cart Mocks
        Mockito.when(service.clearCart(1)).thenReturn(true);
        Mockito.when(service.clearCart(3)).thenReturn(false);


        //Checkout Mocks
        Mockito.when(service.checkout(1)).thenReturn(new ArrayList<>());
        Mockito.when(service.checkout(2)).thenReturn(null);


    }

    @Test
    void getCartTest() throws Exception {
        mockMvc.perform(get("/cart/"+user.getId()).contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/cart/"+ user2.getId()).contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void clearCartTest() throws Exception {
        mockMvc.perform(delete("/cart/" + user.getId()).contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(delete("/cart/" + 3).contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void checkoutCartTest() throws Exception {
        mockMvc.perform(put("/cart/checkout/" + user.getId()).contentType("application/json"))
                .andExpect(status().isOk());
        mockMvc.perform(put("/cart/checkout/" + user2.getId()).contentType("application/json"))
                .andExpect(status().isBadRequest());



    }



}
