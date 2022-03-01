package com.revature.models;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", insertable = false, updatable = false)
    private int productId;

    @Column(name = "product_price")
    private double productPrice;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_description")
    private String productDescription;

    //Not sure how the sales team wants to handle sales
    //Could have the admin decide the specific amount, or select an enum with a value associated with it, etc.
    @Column(name = "discount_percentage")
    private double discountPercentage;
    //@Column(name = "discount_type")
    //private enum sale; (e.g. "fire", "blowout", etc)

    @Column(name = "featured_product")
    private boolean featuredProduct;
    @Column(name = "current_stock")
    private int currentStock;

    //Images can be byte arrays or url links for s3 images
    @Column(name = "product_image")
    private byte[] productImage;
    //private String ProductImageURL;


    public Product() {
    }

    public Product(int productId, double productPrice, double discountPercentage, boolean featuredProduct, int currentStock,
                   byte[] productImage, String productName, String productDescription) {
        this.productId = productId;
        this.productPrice = productPrice;
        this.discountPercentage = discountPercentage;
        this.featuredProduct = featuredProduct;
        this.currentStock = currentStock;
        this.productImage = productImage;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
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
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductId() == product.getProductId() && Double.compare(product.getProductPrice(), getProductPrice()) == 0 && Double.compare(product.getDiscountPercentage(), getDiscountPercentage()) == 0 && isFeaturedProduct() == product.isFeaturedProduct() && getCurrentStock() == product.getCurrentStock() && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getProductDescription(), product.getProductDescription()) && Arrays.equals(getProductImage(), product.getProductImage());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getProductId(), getProductPrice(), getProductName(), getProductDescription(), getDiscountPercentage(), isFeaturedProduct(), getCurrentStock());
        result = 31 * result + Arrays.hashCode(getProductImage());
        return result;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productPrice=" + productPrice +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", discountPercentage=" + discountPercentage +
                ", featuredProduct=" + featuredProduct +
                ", currentStock=" + currentStock +
                ", productImage=" + Arrays.toString(productImage) +
                '}';
    }
}
