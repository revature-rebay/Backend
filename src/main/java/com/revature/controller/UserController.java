package com.revature.controller;

import com.revature.models.User;
import com.revature.service.UserService;
import com.revature.utils.Cookies;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200, http://LocaLhost:8080, http://d1fpc6erw3y64i.cloudfront.net, http://ec2-44-203-89-9.compute-1.amazonaws.com:9090", allowCredentials = "true")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@CookieValue(name = "rebay_User") String cookie,
                                            @PathVariable("id") int id){
        User user = Cookies.isCookieValid(cookie);

        if(user!=null){

            User user_id = userService.getUser(id);

            return ResponseEntity.status(200).body(user_id);
        }
        return ResponseEntity.status(204).build();

    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CookieValue(name = "rebay_User") String cookie){
        User user = Cookies.isCookieValid(cookie);
        if(user!=null){
            user = userService.getUser(user.getId());
            return ResponseEntity.status(200).body(user);
        }
        return ResponseEntity.status(204).build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User savedUser = userService.saveUser(user);
        if(savedUser!=null){
            return ResponseEntity.status(201).build();
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/login")
    public ResponseEntity loginRequest(@RequestBody User user) {

        // Checks if account information is valid
        User u = userService.validateAccount(user.getUserName(), user.getPassWord());
        if (u != null){
            //u.setPassword("");
            ResponseCookie cookie = Cookies.buildResponseCookie(u);
            System.out.println(u.toString());
            return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, cookie.toString()). body(u);
        }
        return ResponseEntity.status(400).build();

    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser() {

//         Grabs an Empty Cookie that expires immediately
        ResponseCookie empty_cookie = Cookies.nullResponseCookie();
        // Returns 200 with Empty Cookie
        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, empty_cookie.toString()).build();

    }


}

