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
//        User user = Cookies.isCookieValid(cookie);
//
//        if(user!=null){
//
//            User user_id = userService.getUser(id);
//
//            return ResponseEntity.status(200).body(user_id);
//        }
//        return ResponseEntity.status(204).build();
        return null;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getCurrentUser(@CookieValue(name = "rebay_User") String cookie){

//        User user = Cookies.isCookieValid(cookie);
//
//        if(user!=null){
//
//            user = userService.getUser(user.getId());
//
//            return ResponseEntity.status(200).body(user);
//        }
//        return ResponseEntity.status(204).build();
        return null;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){

       // user.setPassword(Encryption.stringToMD5(user.getPassword()));
//
//        if(userService.saveUser(user)){
//            return ResponseEntity.status(201).build();
//        }
//        return ResponseEntity.status(400).build();
        return null;
    }

    @PostMapping("/login")
    public ResponseEntity loginRequest(@RequestBody User user) {

        // Checks if account information is valid
//        User u = userService.validateAccount(user.getUserName(), user.getPassWord());
//        if (u != null){
//            //u.setPassword("");
//            ResponseCookie cookie = Cookies.buildResponseCookie(u);
//            System.out.println(u.toString());
//            return ResponseEntity.status(200).header(HttpHeaders.SET_COOKIE, cookie.toString()). body(u);
//        }
//        return ResponseEntity.status(400).build();
        return null;
    }

    @PostMapping("/logout")
    public ResponseEntity logoutUser() {
        // Grabs an Empty Cookie that expires immediately
//        ResponseCookie empty_cookie = Cookies.nullResponseCookie();
//        // Returns 200 with Empty Cookie
//        return ResponseEntity.status(HttpStatus.OK).header(HttpHeaders.SET_COOKIE, empty_cookie.toString()).build();
        return null;
    }


}
