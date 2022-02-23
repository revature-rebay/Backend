package com.revature.service;

import com.revature.models.CartItem;
import com.revature.models.User;
import com.revature.repo.UserDAO;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;

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

        CartItem item1 = new CartItem(1,);


        user.setCart();
    }



}
