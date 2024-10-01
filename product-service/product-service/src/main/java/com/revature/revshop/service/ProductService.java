package com.revature.revshop.service;

import com.revature.revshop.dto.ProductRequest;
import com.revature.revshop.dto.ProductResponse;
import com.revature.revshop.model.Category;
import com.revature.revshop.model.Product;
import com.revature.revshop.repository.CategoryRepository; // Import your Category repository
import com.revature.revshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository; // Inject Category repository

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository; // Initialize Category repository
    }

    // Create a new product
    public ProductResponse createProduct(ProductRequest productRequest) {
        // Fetch the category by ID
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found")); // Handle case where category does not exist

        Product product = Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productImageUrl(productRequest.getProductImageUrl())
                .userid(productRequest.getUserid())
                .category(category) // Set the category
                .price(productRequest.getPrice())
                .build();

        Product savedProduct = productRepository.save(product);
        return mapToResponse(savedProduct);
    }


    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        System.out.println("Number of products retrieved: " + products.size());
        return products.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    
    
    

    // Read a product by ID
    public Optional<ProductResponse> getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::mapToResponse);
    }

    // Update a product
    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productRepository.findById(id).map(product -> {
            product.setProductName(productRequest.getProductName());
            product.setProductDescription(productRequest.getProductDescription());
            product.setProductImageUrl(productRequest.getProductImageUrl());
            product.setUserid(productRequest.getUserid());
            product.setPrice(productRequest.getPrice());

            // Fetch the category by ID and set it
            Category category = categoryRepository.findById(productRequest.getCategoryId())
                    .orElseThrow(() -> new IllegalArgumentException("Category not found"));
            product.setCategory(category);

            Product updatedProduct = productRepository.save(product);
            return mapToResponse(updatedProduct);
        });
    }

    // Delete a product
    public boolean deleteProduct(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper method to map Product to ProductResponse
    private ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .productDescription(product.getProductDescription())
                .productImageUrl(product.getProductImageUrl())
                .userid(product.getUserid())
                .categoryId(product.getCategory() != null ? product.getCategory().getId() : null)
                .price(product.getPrice())
                .build();
    }
}
