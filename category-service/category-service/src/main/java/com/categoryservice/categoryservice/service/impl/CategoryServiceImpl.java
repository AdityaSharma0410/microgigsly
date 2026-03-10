package com.categoryservice.categoryservice.service.impl;

import com.categoryservice.categoryservice.dto.CategoryRequest;
import com.categoryservice.categoryservice.dto.CategoryResponse;
import com.categoryservice.categoryservice.exception.BadRequestException;
import com.categoryservice.categoryservice.exception.CategoryNotFoundException;
import com.categoryservice.categoryservice.exception.ConflictException;
import com.categoryservice.categoryservice.mapper.CategoryMapper;
import com.categoryservice.categoryservice.model.Category;
import com.categoryservice.categoryservice.repository.CategoryRepository;
import com.categoryservice.categoryservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public CategoryResponse createCategory(CategoryRequest request) {
        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new ConflictException("Category with name '" + request.getName() + "' already exists");
        }
        Category category = CategoryMapper.toEntity(request);
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Category category = findOrThrow(id);
        if (!category.getName().equalsIgnoreCase(request.getName())
                && categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new BadRequestException("Another category with name '" + request.getName() + "' already exists");
        }
        category.updateDetails(request.getName(), request.getDescription(), request.getIconUrl());
        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = findOrThrow(id);
        categoryRepository.delete(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategory(Long id) {
        return CategoryMapper.toResponse(findOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public boolean categoryExists(Long id) {
        return categoryRepository.existsById(id);
    }

    private Category findOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }
}
