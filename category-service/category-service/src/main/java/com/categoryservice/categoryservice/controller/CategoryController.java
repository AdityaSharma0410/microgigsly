package com.categoryservice.categoryservice.controller;

import com.categoryservice.categoryservice.dto.CategoryRequest;
import com.categoryservice.categoryservice.dto.CategoryResponse;
import com.categoryservice.categoryservice.exception.BadRequestException;
import com.categoryservice.categoryservice.security.CurrentUserService;
import com.categoryservice.categoryservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CurrentUserService currentUserService;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request
    ) {
        requireAdmin();
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {
        requireAdmin();
        return ResponseEntity.ok(categoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        requireAdmin();
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories() {
        return ResponseEntity.ok(categoryService.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.getCategory(id));
    }

    /** Internal endpoint used by task-service via Feign to verify category existence. */
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> categoryExists(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.categoryExists(id));
    }

    private void requireAdmin() {
        String role = currentUserService.getCurrentUserRole();
        if (!"ADMIN".equals(role)) {
            throw new BadRequestException("Only admins can perform this operation");
        }
    }
}
