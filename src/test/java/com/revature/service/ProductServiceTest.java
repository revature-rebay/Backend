package com.revature.service;

import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repo.ProductDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class ProductServiceTest {

    @Mock
    private ProductDAO mockedDAO;
    private ProductService testInstance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testInstance = new ProductService(mockedDAO);
        Mockito.when(mockedDAO.findAll()).thenReturn(new ArrayList<>());
        Mockito.when(mockedDAO.existsById(3)).thenReturn(true);
        Mockito.when(mockedDAO.existsById(1)).thenReturn(false);
        Mockito.when(mockedDAO.findById(-1)).thenReturn(null);
        Mockito.when(mockedDAO.findById(3)).thenReturn(java.util.Optional.of(new Product()));
//        Mockito.when(mockedDAO.findByDiscountedTrue()).thenReturn((new ArrayList<>()));
        Mockito.when(mockedDAO.findByFeaturedProductTrue()).thenReturn((new ArrayList<>()));
        Mockito.when(mockedDAO.findByDiscountPercentageGreaterThan(0.0)).thenReturn((new ArrayList<>()));
    }

    @Test
    void getAllProducts() {
        List<Product> testList = testInstance.getAllProducts();
        assertEquals(0, testList.size());
    }

    @Test
    void getProduct() {
        Product product = testInstance.getProduct(3);
        Product product2 = testInstance.getProduct(-1);

        assert (product != null && product2 == null); //
    }

    @Test
    void getFeaturedProducts() {
        List<Product> testList = testInstance.getFeaturedProducts();
        assertEquals(0, testList.size());
    }

    @Test
    void getClearanceProducts() {
        List<Product> testList = testInstance.getClearanceProducts();
        assertEquals(0, testList.size());
    }

    @Test
    void addProduct() {
        Product testProduct = new Product(1, 33.3, 0, false, 0, any(byte[].class));
        Product testProduct2 = new Product(3, 33.3, 0, false, 0, new byte[7]);
        boolean successValue1 = testInstance.addProduct(testProduct);
        testInstance.addProduct(testProduct);
        boolean successValue2 = testInstance.addProduct(testProduct2);

        assertEquals(true, successValue1);
        assertEquals(false, successValue2);
    }

    @Test
    void removeProduct() {
        boolean wasDeleted1 = testInstance.removeProduct(3);
        boolean wasDeleted2 = testInstance.removeProduct(-1);

        assertTrue(wasDeleted1);
        assertFalse(wasDeleted2);
    }

    @Test
    void updateProduct() {
        Product testProduct = new Product(3, 33.3, 0, false, 0, new byte[7]);
        Product testProduct2 = new Product(-1, 33.3, 0, false, 0, any(byte[].class));
        boolean successValue1 = testInstance.updateProduct(testProduct);

        boolean successValue2 = testInstance.updateProduct(testProduct2);

        assertEquals(true, successValue1);
        assertEquals(false, successValue2);

    }
}
