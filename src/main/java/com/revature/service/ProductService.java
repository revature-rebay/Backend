package com.revature.service;

import com.revature.models.Product;
import com.revature.repo.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private ProductDAO productDAO;
    private Logger logger = LoggerFactory.getLogger("Product Service Logger");

    @Autowired
    public ProductService(ProductDAO productDAO){
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts(){
        List<Product> productList = productDAO.findAll();
        if (productList == null) {
            logger.error("This Products could not be retrieved");
            return null;
        }else if (productList.isEmpty()) {
            logger.info("The retrieved list of products is empty");
        }else {
            logger.info("The product list was successfully retrieved");
        }
        return productList;
    }

    public Product getProduct(int productId) {
//        if (productDAO.existsById(productId)) {
            Optional<Product> product = productDAO.findById(productId);
            if(product != null && product.isPresent()) {
                logger.info("The product was found and returned");
                return product.get();
            }else {
                logger.error("The product exists, but there was an issue retrieving it");
            }
//        }
        logger.error("There aren't any products associated with the given ID");
        return null;
    }

    public List<Product> getFeaturedProducts(){
        List<Product> featuredProductList = productDAO.findByFeaturedProduct(true);
        if (featuredProductList == null) {
            logger.error("Featured products could not be retrieved");
            return null;
        }else if (featuredProductList.isEmpty()) {
            logger.info("Featured list is currently empty");
        }else {
            logger.info("Featured list was successfully retrieved");
        }
        return featuredProductList;
    }

//    public List<Product> getDiscountedProducts(){
//        List<Product> discountProductList = productDAO.findByDiscountedProduct();
//        if (discountProductList == null) {
//            logger.error("Discount products could not be retrieved");
//            return null;
//        }else if (discountProductList.isEmpty()) {
//            logger.info("Discount list is currently empty");
//        }else {
//            logger.info("Discount list was successfully retrieved");
//        }
//        return discountProductList;
//    }
}