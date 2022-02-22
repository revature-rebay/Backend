package com.revature.repo;

import com.revature.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, Integer> {
    User findByUsername(String userName);

}
