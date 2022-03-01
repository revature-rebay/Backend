package com.revature.controller;

import com.revature.models.Product;
import com.revature.models.User;
import com.revature.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;
    private Logger logger = LoggerFactory.getLogger("Product Controller Logger");

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.status(200).body(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id){
        Product product = productService.getProduct(id);
        if(product != null){
            logger.info("Product found and returned");
            return ResponseEntity.status(200).body(product);
        }
        logger.info("Product not found");
        return ResponseEntity.status(204).build();
    }

    @GetMapping("/featured")
    public ResponseEntity<List<Product>> getFeatured(){
        List<Product> featuredList = productService.getFeaturedProducts();
        if(featuredList != null){
            logger.info("Featured products have been found!");
            return ResponseEntity.status(200).body(featuredList);
        }else{
            logger.info("Featured products have been requested but don't exist!");
            return ResponseEntity.status(204).build();
        }
    }

    @PostMapping
    public ResponseEntity<Boolean> addProduct(@RequestBody Product product){
        boolean wasSaved = productService.addProduct(product);

        if (wasSaved){
            return ResponseEntity.status(201).body(wasSaved);
        }else {
            return ResponseEntity.status(400).body(wasSaved);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") int id){
        boolean wasDeleted = productService.removeProduct(id);
        if(wasDeleted){
            return ResponseEntity.status(200).body(wasDeleted);
        }
        return ResponseEntity.status(400).body(wasDeleted);
    }

    @PutMapping
    public ResponseEntity<Boolean> updateProduct(@RequestBody Product product){
        System.out.println(product);
        boolean wasUpdated = productService.updateProduct(product);
        if (wasUpdated) {
            return ResponseEntity.status(201).body(wasUpdated);
        }else {
            return ResponseEntity.status(400).body(wasUpdated);
        }
    }
}
