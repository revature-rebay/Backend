package com.revature.service;

import com.revature.models.CartDTO;
import com.revature.models.CartItem;
import com.revature.repo.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartService {

    private UserDAO userDAO;

    public CartService() {
    }

    @Autowired
    public CartService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public ArrayList<CartItem> getCart(int userId){
        return null;
    }

    public ArrayList<CartItem> addToCart(CartDTO item){

        return new ArrayList<>();
    }

    public ArrayList<CartItem> updateProductQuantity(CartDTO item){

        return null;
    }

    public ArrayList<CartItem> deleteFromCart(CartDTO item){
        return null;
    }

    public boolean clearCart(int userId){
        return false;
    }


}
