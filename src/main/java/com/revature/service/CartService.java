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

    public List<CartItem> addToCart(CartDTO item){

        //User
        Optional<User> userOptional = userDAO.findById(item.userId);
        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }
        User user = userOptional.get();
        ArrayList<CartItem> cartItems = new ArrayList<>(user.getCart());
        //Product
        Optional<Product> productOptional = productDAO.findById(item.productId);
        if(!productOptional.isPresent()){
            return cartItems;
        }
        Product product = productOptional.get();
        CartItem cartItem = new CartItem(item.quantity, product);
        if(item.quantity >99 || item.quantity<0){
            System.out.println("Invalid Quantity.");
            return cartItems;
        }

        //adding
        cartItems.add(cartItem);
        user.setCart(cartItems);
        userDAO.save(user);


        return cartItems;

    }

    public List<CartItem> updateProductQuantity(CartDTO item){

        if(item.productId < 1 || item.userId < 1 || item.quantity > 99 || item.quantity < 0){
            System.out.println("Error, invalid input in update Quantity.");
            return new ArrayList<>();
        }
        if(item.quantity == 0)
            deleteFromCart(item);

        Optional<User> userOptional = userDAO.findById(item.userId);

        if(!userOptional.isPresent()){
            return new ArrayList<>();
        }

        User user = userOptional.get();
        List<CartItem> cartItems = user.getCart();
        for(int i = 0; i < cartItems.size()-1; i++)
        {
            if(cartItems.get(i).getProduct().getProductId() == item.productId)
            {
                cartItems.get(i).setQuantity(item.quantity);
            }
        }
        user.setCart(cartItems);
        userDAO.save(user);
        return(user.getCart());
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
