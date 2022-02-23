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
    public CartService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public List<CartItem> getCart(int userId){
        Optional<User> user = userDAO.findById(userId);
        if(user.isPresent()) return user.get().getCart();
        return new ArrayList<>();
    }

    public ArrayList<CartItem> addToCart(CartDTO item){

        return new ArrayList<>();
    }

    public List<CartItem> updateProductQuantity(CartDTO item){

        if(item.productId < 1 || item.userId < 1 || item.quantity > 99){
            System.out.println("Error, invalid input in update Quantity.");
            return new ArrayList<>();
        }
        if(userDAO.setCartItemQuantityByUserId(item.quantity, item.userId, item.productId))
        {
            System.out.println("Returning cart from user " + item.userId);
            return getCart(item.userId);
        }


        return new ArrayList<>();
    }

    public ArrayList<CartItem> deleteFromCart(CartDTO item){
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }

//        Optional<Product> productOptional =

        User user = userOptional.get();
        List<CartItem> cartItems = user.getCart();
        user.setCart(cartItems);

        userDAO.save(user);

        return null;
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
