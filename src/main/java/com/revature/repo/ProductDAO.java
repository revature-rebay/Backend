package com.revature.repo;

import com.revature.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> findByFeaturedProduct(boolean bool);
}
