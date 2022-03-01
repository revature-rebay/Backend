package com.revature.repo;

import com.revature.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductDAO extends JpaRepository<Product, Integer> {
    List<Product> findByFeaturedProductTrue();
    List<Product> findByDiscountPercentageGreaterThan(Double value);
}
