package com.revature.controller;

import com.revature.models.User;
import com.revature.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RequestMapping("/user")
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@CookieValue(name = "rebay_User") String cookie,
                                            @PathVariable("id") int id){
        return null;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CookieValue(name = "rebay_User") String cookie){

        return null;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

        return null;
    }

    @PostMapping("/login")
    public ResponseEntity loginRequest(@RequestBody User user) {

        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser() {

        return null;
    }


}

