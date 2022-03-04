package com.revature.service;

import com.revature.models.CartItem;
import com.revature.models.User;
import com.revature.repo.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void initServiceTest(){

        testUser = new User(
                1,
                "eliasc81",
                "$2a$10$2yhPCSH8dCFS0leAii91EeFoD13fODU6Kgk5KBcuIhcLyNizRO9Lq",
                "me@me.com",
                "Elias",
                "Calagiu",
                false,
                new ArrayList<CartItem>()
        );

    }

    @Test
    void saveUpdatedUserSuccess() {
        User updatedTestUser = testUser;
        updatedTestUser.setUserName("EC81");
        Mockito.when(userDAO.findById(updatedTestUser.getId())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userDAO.save(updatedTestUser)).thenReturn(updatedTestUser);

        assertEquals(updatedTestUser, userService.saveUser(updatedTestUser));
    }

    @Test
    void saveNewUserSuccess() {
        User newUser = testUser;
        newUser.setPassWord("password");
        Mockito.when(userDAO.findById(newUser.getId())).thenReturn(Optional.empty());
        Mockito.when(userDAO.save(newUser)).thenReturn(newUser);

        assertEquals(testUser, userService.saveUser(newUser));
    }

    @Test
    void getUserSuccess() {
        Mockito.when(userDAO.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));

        assertEquals(testUser, userService.getUser(testUser.getId()));
    }



    @Test
    void validateAccountSuccess() {
        Mockito.when(userDAO.findByUserName(testUser.getUserName())).thenReturn(testUser);

        assertEquals(testUser, userService.validateAccount(testUser.getUserName(), "password"));
    }

    @Test
    void getUserFail(){
        Mockito.when(userDAO.findById(-1)).thenReturn(Optional.empty());

        assertNull(userService.getUser(-1));
    }

    @Test
    void validateFailUsername(){
        Mockito.when(userDAO.findByUserName("blah")).thenReturn(null);

        assertNull(userService.validateAccount("blah", "password"));
    }

    @Test
    void validateFailPassword(){
        Mockito.when(userDAO.findByUserName(testUser.getUserName())).thenReturn(testUser);

        assertNull(userService.validateAccount(testUser.getUserName(), "assword"));
    }

    @Test
    void saveUpdatedUserFail() {
        Mockito.when(userDAO.findById(testUser.getId())).thenReturn(Optional.ofNullable(testUser));
        Mockito.when(userDAO.save(testUser)).thenThrow(new RuntimeException());

        assertNull(userService.saveUser(testUser));
    }

}