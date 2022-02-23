package com.revature.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_tbl")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
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
    @OneToMany  (
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<CartItem> cart;



}
