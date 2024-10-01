package com.revature.revshop.service;

import com.revature.revshop.dto.CategoryRequest;
import com.revature.revshop.dto.CategoryResponse;
import com.revature.revshop.model.Category;
import com.revature.revshop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private CategoryRequest categoryRequest;
    private Category category;

    @BeforeEach
    void setUp() {
        categoryRequest = CategoryRequest.builder()
                .name("Electronics") // Assuming the correct property name is "name"
                .build();

        category = Category.builder()
                .id(1L) // Adjusted to match the expected field names
                .name("Electronics")
                .build();
    }

    @Test
    void createCategory_ShouldReturnCategoryResponse() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        CategoryResponse categoryResponse = categoryService.createCategory(categoryRequest);

        assertNotNull(categoryResponse);
        assertEquals(1L, categoryResponse.getId());
        assertEquals("Electronics", categoryResponse.getName());
    }

    @Test
    void getCategoryById_ShouldReturnCategoryResponse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<CategoryResponse> categoryResponse = categoryService.getCategoryById(1L);

        assertTrue(categoryResponse.isPresent());
        assertEquals(1L, categoryResponse.get().getId());
        assertEquals("Electronics", categoryResponse.get().getName());
    }

    @Test
    void getCategoryById_ShouldReturnEmpty_WhenCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<CategoryResponse> categoryResponse = categoryService.getCategoryById(99L);

        assertFalse(categoryResponse.isPresent());
    }

    @Test
    void updateCategory_ShouldReturnUpdatedCategoryResponse() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Optional<CategoryResponse> updatedCategoryResponse = categoryService.updateCategory(1L, categoryRequest);

        assertTrue(updatedCategoryResponse.isPresent());
        assertEquals(1L, updatedCategoryResponse.get().getId());
        assertEquals("Electronics", updatedCategoryResponse.get().getName());
    }

    @Test
    void updateCategory_ShouldReturnEmpty_WhenCategoryNotFound() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<CategoryResponse> updatedCategoryResponse = categoryService.updateCategory(99L, categoryRequest);

        assertFalse(updatedCategoryResponse.isPresent());
    }

    @Test
    void deleteCategoryById_ShouldReturnTrue() {
        when(categoryRepository.existsById(1L)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1L);

        boolean result = categoryService.deleteCategory(1L);

        assertTrue(result);
        verify(categoryRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteCategoryById_ShouldReturnFalse_WhenCategoryNotFound() {
        when(categoryRepository.existsById(99L)).thenReturn(false);

        boolean result = categoryService.deleteCategory(99L);

        assertFalse(result);
        verify(categoryRepository, never()).deleteById(99L);
    }

    @Test
    void getAllCategories_ShouldReturnListOfCategoryResponses() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));

        List<CategoryResponse> categoryResponses = categoryService.getAllCategories();

        assertNotNull(categoryResponses);
        assertEquals(1, categoryResponses.size());
        assertEquals(1L, categoryResponses.get(0).getId());
        assertEquals("Electronics", categoryResponses.get(0).getName());
    }
}
