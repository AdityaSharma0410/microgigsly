package com.categoryservice.categoryservice.service;

import com.categoryservice.categoryservice.dto.CategoryRequest;
import com.categoryservice.categoryservice.dto.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse createCategory(CategoryRequest request);

    CategoryResponse updateCategory(Long id, CategoryRequest request);

    void deleteCategory(Long id);

    List<CategoryResponse> getCategories();

    CategoryResponse getCategory(Long id);

    boolean categoryExists(Long id);
}
