package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "cart_item")
public class CartItem {

    @Id
    @Column(name = "cart_item_id")
    private int id;

    private int quantity;

    @ManyToOne
    private Product product;

    @ManyToOne
    private User user;
}
