package com.revature.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    private static final long serialVersionUID = -2902502762558688842L;

    @Id
    //@EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true)
    private String userName;
    private String passWord;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private int roleId;
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cart;

    public void addCartItem(CartItem item) {
        this.cart.add(item);
    }
    public void removeCartItem(CartItem item) {
        this.cart.remove(item);
    }
    public void clearCart(){
        this.cart.clear();
    }
    public void updateCartItem(CartDTO item){
        this.cart.replaceAll(cartItem -> {
            if(cartItem.getProduct().getProductId() == item.productId)
            {
                cartItem.setQuantity(item.quantity);
            }
            return cartItem;
        });
    }
}
