package com.revature.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, nullable = false)
    private String userName;
    private String passWord;
    @Column(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private boolean admin = false; //defaults to non-admin
    @OnDelete(action = OnDeleteAction.CASCADE)
    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonManagedReference
    private List<CartItem> cart;

    public User(UserDTO user){
        this.userName = user.getUserName();
        this.passWord = user.getPassWord();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

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

