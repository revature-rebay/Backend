package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    User user;

    @BeforeEach
    void init(){
        user = new User(1, "flodev", "password", "email@email.com", "Eric", "Florence", 1, new ArrayList<>());
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUser(1)).thenReturn(user);
        MvcResult result = mockMvc.perform(get("/user/1").contentType("application/json"))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();

        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    void getCurrentUser() {

    }

    @Test
    void createUser() throws Exception {
        when(userService.saveUser(user)).thenReturn(true);
        mockMvc.perform(post("/user").contentType("application/json").content(objectMapper.writeValueAsString(user))).andExpect(status().isCreated());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(userCaptor.capture());

        assertThat(userCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void loginRequest() throws Exception {
        when(userService.validateAccount(user.getUserName(), user.getPassWord())).thenReturn(user);
        MvcResult result = mockMvc.perform(post("/user/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assertThat(content).isEqualTo(objectMapper.writeValueAsString(user));
    }

    @Test
    void logoutUser() throws Exception {
        mockMvc.perform(post("/user/logout").contentType("application/json"))
                .andExpect(status().isOk());

    }
}