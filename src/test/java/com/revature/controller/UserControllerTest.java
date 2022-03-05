package com.revature.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.test.web.servlet.MockMvc;

import javax.servlet.http.Cookie;


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
    Cookie cookie = new Cookie("rebay_User", "rO0ABXNyABhjb20ucmV2YXR1cmUubW9kZWxzLlVzZXJVNUuYZdq5SwIACFoABWFkbWluSQACaWRMAARjYXJ0dAAQTGphdmEvdXRpbC9MaXN0O0wABWVtYWlsdAASTGphdmEvbGFuZy9TdHJpbmc7TAAJZmlyc3ROYW1lcQB+AAJMAAhsYXN0TmFtZXEAfgACTAAIcGFzc1dvcmRxAH4AAkwACHVzZXJOYW1lcQB+AAJ4cAAAAAABc3IAL29yZy5oaWJlcm5hdGUuY29sbGVjdGlvbi5pbnRlcm5hbC5QZXJzaXN0ZW50QmFn/lfFr9pPpkQCAAJMAANiYWdxAH4AAUwAEnByb3ZpZGVkQ29sbGVjdGlvbnQAFkxqYXZhL3V0aWwvQ29sbGVjdGlvbjt4cgA+b3JnLmhpYmVybmF0ZS5jb2xsZWN0aW9uLmludGVybmFsLkFic3RyYWN0UGVyc2lzdGVudENvbGxlY3Rpb25XGLddirpzVAIAC1oAG2FsbG93TG9hZE91dHNpZGVUcmFuc2FjdGlvbkkACmNhY2hlZFNpemVaAAVkaXJ0eVoADmVsZW1lbnRSZW1vdmVkWgALaW5pdGlhbGl6ZWRaAA1pc1RlbXBTZXNzaW9uTAADa2V5dAAWTGphdmEvaW8vU2VyaWFsaXphYmxlO0wABW93bmVydAASTGphdmEvbGFuZy9PYmplY3Q7TAAEcm9sZXEAfgACTAASc2Vzc2lvbkZhY3RvcnlVdWlkcQB+AAJMAA5zdG9yZWRTbmFwc2hvdHEAfgAHeHAA/////wAAAABzcgARamF2YS5sYW5nLkludGVnZXIS4qCk94GHOAIAAUkABXZhbHVleHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhwAAAAAXEAfgADdAAdY29tLnJldmF0dXJlLm1vZGVscy5Vc2VyLmNhcnRwcHBwdAAPYWxleEBzYW1wbGUuY29tdAAEQWxleHQACEFuZGVyc29udAAAdAANV2luZG93U2hvcHBlcg==");

    @BeforeEach
    void init(){
        user = new User(1,
                "WindowShopper",
                "password",
                "alex@sample.com",
                "Alex",
                "Anderson",
                false,
                null);
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
    void getCurrentUser() throws Exception {
        when(userService.getUser(1)).thenReturn(user);
        mockMvc.perform(get("/user/current")
                .contentType("application/json")
                .cookie(cookie))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void createUser() throws Exception {
        UserDTO creds = new UserDTO();
        creds.setPassWord("password");
        creds.setEmail(user.getEmail());
        creds.setUserName(user.getUserName());
        creds.setFirstName(user.getFirstName());
        creds.setLastName(user.getLastName());
        user.setId(0);
        when(userService.saveUser(new User(creds))).thenReturn(user);
        mockMvc.perform(post("/user")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isCreated());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).saveUser(userCaptor.capture());

        assertThat(userCaptor.getValue()).isEqualTo(user);
    }

    @Test
    void loginRequest() throws Exception {
        when(userService.validateAccount(user.getUserName(), user.getPassWord())).thenReturn(user);
        mockMvc.perform(post("/user/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
            .andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(user)));
    }

    @Test
    void logoutUser() throws Exception {
        mockMvc.perform(post("/user/logout").contentType("application/json"))
            .andExpect(status().isOk());
    }
}