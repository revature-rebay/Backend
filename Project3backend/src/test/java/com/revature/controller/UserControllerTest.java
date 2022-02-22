package com.revature.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import  java.lang.String[];


@RunWith(SpringRunner.class)
@SpringBootTest //(SpringBootTest.WebEnvironment.MOCK, classes = Project3SetUpApplication.class) //change class in real repo
@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.properties")
class UserControllerTest {

    @Test
    void getUserById() {
    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void loginRequest() {
    }

    @Test
    void logoutUser() {
    }
}