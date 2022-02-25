package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.servlet.http.Cookie;
import java.util.ArrayList;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    User user;
    Cookie cookie = new Cookie("rebay_User", "rO0ABXNyABhjb20ucmV2YXR1cmUubW9kZWxzLlVzZXLXuD0dlhlxtgIACEkAAmlkSQAGcm9sZUlkTAAEY2FydHQAEExqYXZhL3V0aWwvTGlzdDtMAAVlbWFpbHQAEkxqYXZhL2xhbmcvU3RyaW5nO0wACWZpcnN0TmFtZXEAfgACTAAIbGFzdE5hbWVxAH4AAkwACHBhc3NXb3JkcQB+AAJMAAh1c2VyTmFtZXEAfgACeHAAAAABAAAAAXNyAC9vcmcuaGliZXJuYXRlLmNvbGxlY3Rpb24uaW50ZXJuYWwuUGVyc2lzdGVudEJhZ/5Xxa/aT6ZEAgACTAADYmFncQB+AAFMABJwcm92aWRlZENvbGxlY3Rpb250ABZMamF2YS91dGlsL0NvbGxlY3Rpb247eHIAPm9yZy5oaWJlcm5hdGUuY29sbGVjdGlvbi5pbnRlcm5hbC5BYnN0cmFjdFBlcnNpc3RlbnRDb2xsZWN0aW9uVxi3XYq6c1QCAAtaABthbGxvd0xvYWRPdXRzaWRlVHJhbnNhY3Rpb25JAApjYWNoZWRTaXplWgAFZGlydHlaAA5lbGVtZW50UmVtb3ZlZFoAC2luaXRpYWxpemVkWgANaXNUZW1wU2Vzc2lvbkwAA2tleXQAFkxqYXZhL2lvL1NlcmlhbGl6YWJsZTtMAAVvd25lcnQAEkxqYXZhL2xhbmcvT2JqZWN0O0wABHJvbGVxAH4AAkwAEnNlc3Npb25GYWN0b3J5VXVpZHEAfgACTAAOc3RvcmVkU25hcHNob3RxAH4AB3hwAP////8AAAAAc3IAEWphdmEubGFuZy5JbnRlZ2VyEuKgpPeBhzgCAAFJAAV2YWx1ZXhyABBqYXZhLmxhbmcuTnVtYmVyhqyVHQuU4IsCAAB4cAAAAAFxAH4AA3QAHWNvbS5yZXZhdHVyZS5tb2RlbHMuVXNlci5jYXJ0cHBwcHQAEGZsb2RldkBnbWFpbC5jb210AARFcmljdAAIRmxvcmVuY2V0ADwkMmEkMTAkMnloUENTSDhkQ0ZTMGxlQWlpOTFFZUZvRDEzZk9EVTZLZ2s1S0JjdUloY0x5Tml6Uk85THF0AAZmbG9kZXY");

    @BeforeEach
    void init(){
        user = new User(1, "flodev", "password", "email@email.com", "Eric", "Florence", 1, new ArrayList<>());
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUser(1)).thenReturn(user);
                mockMvc.perform(get("/user/{id}", 1)
                        .contentType("application/json")
                        .cookie(cookie))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(user)));
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