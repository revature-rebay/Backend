package com.revature.controller;

import com.revature.models.User;
import com.revature.models.UserDTO;
import com.revature.service.UserService;
import com.revature.utils.Cookies;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://d1fpc6erw3y64i.cloudfront.net"}, allowCredentials = "true")
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    //Service object, required field
    private final UserService userService;

    //GET request at /user/id returns whole user object of user with matching id from db
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@CookieValue(name = "rebay_User") String cookie,
                                            @PathVariable("id") int id){
        //use the cookie to determine if a valid user credentials were sent
        User user = Cookies.isCookieValid(cookie);

        //if the cookie is valid, return the requested user
        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getUser(id));
        }
        //otherwise, return No Content status code
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //GET request at /user/current returns user object of who is currently logged in
    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CookieValue(name = "rebay_User") String cookie){
        //use the cookie to determine if a valid user credentials were sent
        User user = Cookies.isCookieValid(cookie);

        //if cookie is valid, return latest user object from the db
        if(user!=null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(userService.getUser(user.getId()));
        }
        //otherwise, return No Content status code
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDTO userInfo){
        User savedUser = userService.saveUser(new User(userInfo));
        if(savedUser!=null){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginRequest(@RequestBody UserDTO credentials) {
        // Checks if account information is valid
        User user = userService.validateAccount(credentials.getUserName(), credentials.getPassWord());

        if (user != null){
            user.setPassWord("");
            ResponseCookie cookie = Cookies.buildResponseCookie(user);
            return ResponseEntity.status(HttpStatus.OK)
                    .header(HttpHeaders.SET_COOKIE, cookie.toString())
                    .body(user);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

    }

    @PostMapping("/logout")
    public ResponseEntity<User> logoutUser() {
        //Grabs an Empty Cookie that expires immediately
        ResponseCookie emptyCookie = Cookies.nullResponseCookie();
        // Returns 200 with Empty Cookie
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, emptyCookie.toString())
                .build();

    }


}

