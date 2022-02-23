package com.revature.service;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repo.ProductDAO;
import com.revature.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private UserDAO userDAO;
    private ProductDAO productDAO;

    public CartService() {
    }

    @Autowired
    public CartService(UserDAO userDAO, ProductDAO productDAO) {
        this.userDAO = userDAO;
        this.productDAO = productDAO;
    }


    public List<CartItem> getCart(int userId){
        Optional<User> user = userDAO.findById(userId);
        if(user.isPresent()) return user.get().getCart();
        return new ArrayList<>();
    }

    public ArrayList<CartItem> addToCart(CartDTO item){

        return new ArrayList<>();
    }

    public ArrayList<CartItem> updateProductQuantity(CartDTO item){

        return new ArrayList<>();
    }

    public List<CartItem> deleteFromCart(CartDTO item){
        //Get the User and cartItems List to modify
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }
        User user = userOptional.get();
        ArrayList<CartItem> cartItems = new ArrayList<>(user.getCart());

        //Get the product to remove
        Optional<Product> productOptional = productDAO.findById(item.productId);
        if(!productOptional.isPresent()){
            return cartItems;
        }
        Product product = productOptional.get();
        CartItem cartItem = new CartItem(0,0, product);

        cartItems.remove(cartItem);
        user.setCart(cartItems);
        userDAO.save(user);

        return cartItems;
    }

    public boolean clearCart(int userId){
        Optional<User> userOptional = userDAO.findById(userId);
        if(!userOptional.isPresent()){
            return false;
        }

        User user = userOptional.get();
        user.setCart(new ArrayList<>());
        userDAO.save(user);
        return true;
    }


}
