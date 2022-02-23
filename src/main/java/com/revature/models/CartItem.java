package com.revature.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int id;

    private int quantity;

    @ManyToOne
    private Product product;

//    @ManyToOne
//    private User user;

    public CartItem() {
    }

//    public CartItem(int id, int quantity, Product product, User user) {
//        this.id = id;
//        this.quantity = quantity;
//        this.product = product;
//        this.user = user;
//    }

    public CartItem(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
    }

    public CartItem(int id, int quantity, Product product) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


    //Two cart items are equivalent if they both hold a product variable with the same product Id
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return this.product.getProductId() == cartItem.product.getProductId();
    }

    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", product=" + product.getProductId() +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
