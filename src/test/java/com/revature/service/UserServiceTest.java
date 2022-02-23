package com.revature.service;

import com.revature.models.User;
import com.revature.repo.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserServiceTest {
    @Mock
    private UserDAO userDAO
            = Mockito.mock(UserDAO.class);
//    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void initServiceTest(){
        userService= new UserService(userDAO);
        testUser = new User(
                1,
                "eliasc81",
                "$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq\n",
                "me@me.com",
                "Elias",
                "Calagiu",
                1
        );
        userService= new UserService(userDAO);
        Mockito.when(userDAO.findByUserName(testUser.getUserName())).thenReturn(testUser);
        Mockito.when(userDAO.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));
    }

    @Test
    @Order(1)
    void saveUser() {
        boolean savedUser= userService.saveUser(testUser);
        assertTrue(savedUser);
    }


    @Test
    @Order(2)
    void getUser() {
        User user = userService.getUser(testUser.getId());
        assertEquals(user, testUser);
    }



    @Test
    @Order(3)
    void validateAccount() {
        User user = userService.validateAccount(testUser.getUserName(), testUser.getPassWord());
        assertEquals(user, testUser);
    }
}