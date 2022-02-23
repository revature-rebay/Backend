package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getUserById() throws Exception {
        mockMvc.perform(get("/user/{id}"));
    }

    @Test
    void getCurrentUser() {
    }

    @Test
    void createUser() throws Exception {

        User user = new User(1, "flodev", "password", "email@email.com", "Eric", "Florence", 1, new ArrayList<>());
        when(userService.saveUser(user)).thenReturn(true);
        mockMvc.perform(post("/user").contentType("application/json").content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(userCaptor.capture());

        assertThat(userCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void loginRequest() {
    }

    @Test
    void logoutUser() {
    }
}