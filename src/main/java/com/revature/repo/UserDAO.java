package com.revature.repo;

import com.revature.models.CartItem;
import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUserName(String userName);

    @Modifying
    @Query("update CartItem c set c.quantity = ?1, where c.userId = ?2 and c.productId = ?3")
    boolean setCartItemQuantityByUserId(int quantity, Integer userId, Integer productId);

}