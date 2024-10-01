package com.revature.revshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.revshop.dto.CategoryRequest;
import com.revature.revshop.dto.CategoryResponse;
import com.revature.revshop.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryResponse categoryResponse;
    private CategoryRequest categoryRequest;

    @BeforeEach
    public void setup() {
        categoryResponse = CategoryResponse.builder()
                .id(1L)
                .name("Electronics")
                .build();

        categoryRequest = CategoryRequest.builder()
                .name("Electronics")
                .build();
    }

    @Test
    public void testCreateCategory() throws Exception {
        Mockito.when(categoryService.createCategory(any(CategoryRequest.class))).thenReturn(categoryResponse);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(categoryResponse.getId()))
                .andExpect(jsonPath("$.name").value(categoryResponse.getName()));
    }

    @Test
    public void testGetCategoryById() throws Exception {
        Mockito.when(categoryService.getCategoryById(1L)).thenReturn(Optional.of(categoryResponse));

        mockMvc.perform(get("/api/categories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryResponse.getId()))
                .andExpect(jsonPath("$.name").value(categoryResponse.getName()));
    }

    @Test
    public void testGetCategoryById_NotFound() throws Exception {
        Mockito.when(categoryService.getCategoryById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/categories/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateCategoryById() throws Exception {
        Mockito.when(categoryService.updateCategory(eq(1L), any(CategoryRequest.class))).thenReturn(Optional.of(categoryResponse));

        mockMvc.perform(put("/api/categories/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryResponse.getId()))
                .andExpect(jsonPath("$.name").value(categoryResponse.getName()));
    }

    @Test
    public void testUpdateCategoryById_NotFound() throws Exception {
        Mockito.when(categoryService.updateCategory(eq(99L), any(CategoryRequest.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/categories/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoryRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCategoryById() throws Exception {
        Mockito.when(categoryService.deleteCategory(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/categories/1"))
                .andExpect(status().isNoContent());

        Mockito.verify(categoryService).deleteCategory(1L); // Verify service interaction
    }

    @Test
    public void testDeleteCategoryById_NotFound() throws Exception {
        Mockito.when(categoryService.deleteCategory(99L)).thenReturn(false);

        mockMvc.perform(delete("/api/categories/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        List<CategoryResponse> categories = Arrays.asList(categoryResponse);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categories);

        mockMvc.perform(get("/api/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(categoryResponse.getId()))
                .andExpect(jsonPath("$[0].name").value(categoryResponse.getName()));
    }
}
