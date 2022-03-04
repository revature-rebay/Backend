package com.revature.utils;
import com.revature.models.CartItem;
import java.util.ArrayList;

public class CartException extends RuntimeException{
    private ArrayList<CartItem> notInStock;

    public CartException() {
        this.notInStock = new ArrayList<>();
    }

    public CartException(ArrayList<CartItem> notInStock, String message) {
        super(message);
        this.notInStock = notInStock;
    }

    public ArrayList<CartItem> getNotInStock() {
        return notInStock;
    }
}
