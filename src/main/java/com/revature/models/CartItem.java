package com.revature.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Objects;

//contains fields and methods for a CartItem object
@Entity
@Table(name = "cart_item")
public class CartItem {
    //primary key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_item_id")
    private int id;
    //quantity here is the amount of the item in a customer's cart, this is not the current stock of the item
    private int quantity;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

    public CartItem() {
    }

    public CartItem(int id, int quantity, Product product, User user) {
        this.id = id;
        this.quantity = quantity;
        this.product = product;
        this.user = user;
    }

    public CartItem(int quantity, Product product, User user) {
        this.quantity = quantity;
        this.product = product;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


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
                ", product=" + product.toString()+
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(product);
    }
}
