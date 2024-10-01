package com.revature.revshop.service;

import com.revature.revshop.dto.CategoryRequest;
import com.revature.revshop.dto.CategoryResponse;
import com.revature.revshop.model.Category;
import com.revature.revshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Create a new category
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {
        Category category = Category.builder()
                .name(categoryRequest.getName())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return mapToResponse(savedCategory);
    }

    // Read all categories
    public List<CategoryResponse> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Read a category by ID
    public Optional<CategoryResponse> getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(this::mapToResponse);
    }

    // Update a category
    public Optional<CategoryResponse> updateCategory(Long id, CategoryRequest categoryRequest) {
        return categoryRepository.findById(id).map(category -> {
            category.setName(categoryRequest.getName());
            Category updatedCategory = categoryRepository.save(category);
            return mapToResponse(updatedCategory);
        });
    }

    // Delete a category
    public boolean deleteCategory(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Helper method to map Category to CategoryResponse
    private CategoryResponse mapToResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
