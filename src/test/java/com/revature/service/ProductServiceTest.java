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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductServiceTest {

    @Mock
    private ProductDAO mockedDAO;
    private ProductService testInstance;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testInstance = new ProductService(mockedDAO);
        Mockito.when(mockedDAO.findAll()).thenReturn(new ArrayList<>());
//        Mockito.when(mockedDAO.existsById(3)).thenReturn(true);
//        Mockito.when(mockedDAO.existsById(-1)).thenReturn(false);
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

}
