package com.revature.controller;

import com.revature.models.Product;
import com.revature.models.User;
import com.revature.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
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

//    @GetMapping("/imgTest")
//    public ResponseEntity<String> imageTest() {
//        System.out.println("Called the image test in backend");
//        File file = new File("C:\\Users\\Bobby\\Documents\\Coding\\Revature_Projects\\Swag_photos\\swag_mug.png");
//
//        byte[] fileContent;
//
//        try {
//            fileContent = Files.readAllBytes(file.toPath());
//            String decodedString = new String(fileContent);
//            String byteString = Base64.getEncoder().encodeToString(fileContent);
//            System.out.println("Encoded string: " + byteString.substring(0, 100));
//            System.out.println("Decoded string: " + decodedString.substring(0, 100));
//
//
//            return ResponseEntity.status(201).body(byteString);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//            return ResponseEntity.status(400).build();
//        }
//    }
}
