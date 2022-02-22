package com.revature.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
//@WebMvcTest(UserController.class)
class UserServiceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userServiceTest;


    @Test
    void getUser() {

    }

    @Test
    void saveUser() {
    }

    @Test
    void validateAccount() {
    }
}