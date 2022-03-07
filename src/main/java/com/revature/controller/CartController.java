//This controller handels all HTTP requests from the CartService on the FrontEnd
//Some references connect to the UserService on the backend as well.


package com.revature.controller;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.service.CartService;
import com.revature.utils.CartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;

//API for all Cart methods
//endpoint starts with http://localhost:4200/cart
@RestController
@RequestMapping("/cart")
@CrossOrigin(value ={"http://localhost:4200", "http://d1fpc6erw3y64i.cloudfront.net", "http://revature-rebay.s3-website-us-east-1.amazonaws.com"}, allowCredentials = "true")
public class CartController {
    private CartService service;

    public CartController() {
    }

    @Autowired
    public CartController(CartService service) {
        this.service = service;
    }

    //gets the cart using a user's ID
    //if there is nothing in a user's cart, 204 is returned along with the empty cart
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> getCart(@PathVariable("userId") int userId){
        List<CartItem> cart = service.getCart(userId);
        if(cart.size() == 0) return ResponseEntity.status(204).body(cart);
        return ResponseEntity.status(200).body(cart);
    }

    //adds an item to a user's cart
    //uses a data transfer object CartDTO
    //will return the updated cart
    //if there is an issue with the request, a 404, is returned with an empty cart
    @PostMapping("/add")
    public ResponseEntity<List<CartItem>> addToCart(@RequestBody CartDTO cartDTO) {
        List<CartItem> cartList = service.addToCart(cartDTO);
        if (cartList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(200).body(cartList);
    }

    //updates the product quantity in a user's cart
    //returns the updated cart
    //if there is an issue with the request, 204 and null are returned
    @PutMapping("/update")
    public ResponseEntity<List<CartItem>> updateProductQuantity(@RequestBody CartDTO cartDTO) {
        List<CartItem> list = service.updateProductQuantity(cartDTO);
        if(list.isEmpty())
        {
            return ResponseEntity.status(204).build();
        }
        return ResponseEntity.status(200).body(list);
    }

    //deletes an item from the cart
    //returns the updated cart
    //if there is an issue with the request, 400 is returned with a null
    @PutMapping("/delete")
    public ResponseEntity<List<CartItem>> deleteFromCart(@RequestBody CartDTO cartDTO) {
        List<CartItem> cartItems = service.deleteFromCart(cartDTO);
        if(cartItems == null){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.status(200).body(cartItems);
    }

    //clears all items in a user's cart
    //accepts a user's ID
    //if clear cart is successfully, 200 is returned
    //if an issue with the request, a 400 is returned with a null
    @DeleteMapping("/{userId}")
    public ResponseEntity clearCart(@PathVariable("userId") int userId) {
        if(service.clearCart(userId)){
            return ResponseEntity.status(200).build();
        }
        return ResponseEntity.badRequest().build();
    }
    //checkout a user
    //accepts a user's ID
    //returns 206 and list of items that are out of stock that are in a user's cart
    //or returns a 500 error in the case of bad info in the database
    //or returns a 400 if an issue with the request
    //or 200 if all items in a user's cart were in stock
    @PutMapping("/checkout/{userId}")
    public ResponseEntity checkout(@PathVariable("userId") int userId) {
        List<CartItem> cart = new ArrayList<>();
        try{
           cart = service.checkout(userId);
            if(cart == null) {
                return ResponseEntity.badRequest().build();
            }
        }
        catch(CartException e){
            return ResponseEntity.status(206).body(e.getNotInStock());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.status(200).build();
    }

}
