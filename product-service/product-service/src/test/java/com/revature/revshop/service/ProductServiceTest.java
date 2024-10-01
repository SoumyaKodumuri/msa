package com.revature.revshop.service;

import com.revature.revshop.dto.ProductRequest;
import com.revature.revshop.dto.ProductResponse;
import com.revature.revshop.model.Category;
import com.revature.revshop.model.Product;
import com.revature.revshop.repository.CategoryRepository;
import com.revature.revshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    private Category category;
    private ProductRequest productRequest;
    private Product product;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup for Category and ProductRequest
        category = new Category(1L, "Electronics");
        productRequest = ProductRequest.builder()
                .productName("Product1")
                .productDescription("Description")
                .productImageUrl("imageUrl")
                .userid("1")
                .categoryId(1L)
                .price(100.0)
                .build();

        // Setup for Product entity
        product = Product.builder()
                .productId(1L)
                .productName("Product1")
                .productDescription("Description")
                .productImageUrl("imageUrl")
                .userid("1")
                .category(category)
                .price(100.0)
                .build();
    }

    @Test
    public void createProduct_ShouldReturnProductResponse() {
        // Mocking behavior
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Call method under test
        ProductResponse response = productService.createProduct(productRequest);

        // Assertions
        assertNotNull(response);
        assertEquals("Product1", response.getProductName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void getProductById_ShouldReturnProductResponse() {
        // Mocking behavior
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Call method under test
        Optional<ProductResponse> response = productService.getProductById(1L);

        // Assertions
        assertTrue(response.isPresent());
        assertEquals("Product1", response.get().getProductName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    public void updateProduct_ShouldReturnUpdatedProductResponse() {
        // Mocking behavior
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Call method under test
        Optional<ProductResponse> response = productService.updateProduct(1L, productRequest);

        // Assertions
        assertTrue(response.isPresent());
        assertEquals("Product1", response.get().getProductName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    public void deleteProduct_ShouldReturnTrue() {
        // Mocking behavior
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        // Call method under test
        boolean result = productService.deleteProduct(1L);

        // Assertions
        assertTrue(result);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    public void getAllProducts_ShouldReturnListOfProductResponses() {
        // Mocking behavior
        when(productRepository.findAll()).thenReturn(List.of(product));

        // Call method under test
        List<ProductResponse> response = productService.getAllProducts();

        // Assertions
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("Product1", response.get(0).getProductName());
        verify(productRepository, times(1)).findAll();
    }
}
