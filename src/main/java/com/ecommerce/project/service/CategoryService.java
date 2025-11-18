package com.ecommerce.project.service;

import com.ecommerce.project.model.Category;
import com.ecommerce.project.payload.CategoryDTO;
import com.ecommerce.project.payload.CategoryResponse;

import java.util.List;

// Using interface here instead of class to promote loose coupling and modularity
// Separate the interface for service and its implementation of that service is beneficial in the long run
public interface CategoryService {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);
    CategoryDTO createCategory(CategoryDTO categoryDTO);
    CategoryDTO updateCategory(Long categoryId, CategoryDTO updatedCategoryDTO);
    CategoryDTO deleteCategory(Long categoryId);


}
