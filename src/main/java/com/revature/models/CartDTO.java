package com.revature.models;

public class CartDTO {
    public int userId;
    public int productId;
    public int quantity;

    public CartDTO() {
    }

    public CartDTO(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
