package com.categoryservice.categoryservice.mapper;

import com.categoryservice.categoryservice.dto.CategoryRequest;
import com.categoryservice.categoryservice.dto.CategoryResponse;
import com.categoryservice.categoryservice.model.Category;

public final class CategoryMapper {

    private CategoryMapper() {}

    public static Category toEntity(CategoryRequest request) {
        return Category.builder()
                .name(request.getName())
                .description(request.getDescription())
                .iconUrl(request.getIconUrl())
                .build();
    }

    public static CategoryResponse toResponse(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .iconUrl(category.getIconUrl())
                .isActive(category.getIsActive())
                .createdAt(category.getCreatedAt())
                .updatedAt(category.getUpdatedAt())
                .build();
    }
}
