package com.revature.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_price")
    private double productPrice;

    //Not sure how the sales team wants to handle sales
    //Could have the admin decide the specific amount, or select an enum with a value associated with it, etc.
    @Column(name = "discount_percentage")
    private double discountPercentage;
    //@Column(name = "discount_type")
    //private enum sale; (e.g. "fire", "blowout", etc)

    @Column(name = "featured_product")
    private boolean featuredProduct;
    @Column(name = "product_id")
    private int currentStock;

    //Images can be byte arrays or url links for s3 images
    @Column(name = "product_image")
    private byte[] productImage;
    //private String ProductImageURL;


    public Product() {
    }

    public Product(int productId, double productPrice, double discountPercentage, boolean featuredProduct, int currentStock, byte[] productImage) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.discountPercentage = discountPercentage;
        this.featuredProduct = featuredProduct;
        this.currentStock = currentStock;
        this.productImage = productImage;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public boolean isFeaturedProduct() {
        return featuredProduct;
    }

    public void setFeaturedProduct(boolean featuredProduct) {
        this.featuredProduct = featuredProduct;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public byte[] getProductImage() {
        return productImage;
    }

    public void setProductImage(byte[] productImage) {
        this.productImage = productImage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return productId == product.productId && Double.compare(product.productPrice, productPrice) == 0 && Double.compare(product.discountPercentage, discountPercentage) == 0 && featuredProduct == product.featuredProduct && currentStock == product.currentStock && Arrays.equals(productImage, product.productImage);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(productId, productPrice, discountPercentage, featuredProduct, currentStock);
        result = 31 * result + Arrays.hashCode(productImage);
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productPrice=" + productPrice +
                ", discountPercentage=" + discountPercentage +
                ", featuredProduct=" + featuredProduct +
                ", currentStock=" + currentStock +
                ", productImage=" + Arrays.toString(productImage) +
                '}';
    }
}
