package com.revature.controller;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/cart")
@CrossOrigin(value ="http://localhost:4200")
public class CartController {
    private CartService service;

    public CartController() {
    }

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable("userId") int userId){
        List<CartItem> cart = service.getCart(userId);
        if(cart.size() == 0) return ResponseEntity.status(204).body(cart);
        return ResponseEntity.status(200).body(cart);
    }

    @PostMapping("/add")
    public ResponseEntity<List<CartItem>> addToCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/update")
    public ResponseEntity<List<CartItem>> updateProductQuantity(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<List<CartItem>> deleteFromCart(@RequestBody CartDTO cartDTO) {
        List<CartItem> cartItems = service.deleteFromCart(cartDTO);
        if(cartItems.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).body(cartItems);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity clearCart(@PathVariable("userId") int userId) {
        if(service.clearCart(userId)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.badRequest().build();

    }


}
