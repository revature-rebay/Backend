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

@ExtendWith(MockitoExtension.class)
//@SpringBootTest
class UserServiceTest {
    @Mock
    private UserDAO userDAO;
//            = Mockito.mock(UserDAO.class);
    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void initServiceTest(){

        testUser = new User(
                1,
                "eliasc81",
                "$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq\n",
                "me@me.com",
                "Elias",
                "Calagiu",
                1
        );

    }

    @Test
    void saveUser() {
        boolean savedUser= userService.saveUser(testUser);
        assertTrue(savedUser);
    }


    @Test
    void getUser() {
        Mockito.when(userDAO.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));
        User user = userService.getUser(testUser.getId());
        assertEquals(user, testUser);
    }



    @Test
    void validateAccount() {
        Mockito.when(userDAO.findByUserName(testUser.getUserName())).thenReturn(testUser);
        User user = userService.validateAccount(testUser.getUserName(), "password");
        assertEquals(user, testUser);
    }
}