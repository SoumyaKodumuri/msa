package com.revature.revshop.controller;

import com.revature.revshop.dto.ProductRequest;
import com.revature.revshop.dto.ProductResponse;
import com.revature.revshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private ProductRequest productRequest;
    private ProductResponse productResponse;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        productRequest = new ProductRequest("Product1", "Description", "imageUrl", "1", 1L, 100.0);

        productResponse = new ProductResponse(1L, "Product1", "Description", "imageUrl", "1", 1L, 100.0);
    }

    @Test
    void createProduct() {
        when(productService.createProduct(productRequest)).thenReturn(productResponse);

        ResponseEntity<ProductResponse> responseEntity = productController.createProduct(productRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productResponse.getProductId(), responseEntity.getBody().getProductId());
        verify(productService, times(1)).createProduct(productRequest);
    }

    @Test
    void getAllProducts() {
        when(productService.getAllProducts()).thenReturn(Arrays.asList(productResponse));

        ResponseEntity<List<ProductResponse>> responseEntity = productController.getAllProducts();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(1, responseEntity.getBody().size());
        verify(productService, times(1)).getAllProducts();
    }

    @Test
    void getProductById() {
        when(productService.getProductById(1L)).thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponse> responseEntity = productController.getProductById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productResponse.getProductId(), responseEntity.getBody().getProductId());
        verify(productService, times(1)).getProductById(1L);
    }

    @Test
    void updateProduct() {
        when(productService.updateProduct(1L, productRequest)).thenReturn(Optional.of(productResponse));

        ResponseEntity<ProductResponse> responseEntity = productController.updateProduct(1L, productRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(productResponse.getProductId(), responseEntity.getBody().getProductId());
        verify(productService, times(1)).updateProduct(1L, productRequest);
    }

    @Test
    void deleteProduct() {
        when(productService.deleteProduct(1L)).thenReturn(true);

        ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(productService, times(1)).deleteProduct(1L);
    }
}
