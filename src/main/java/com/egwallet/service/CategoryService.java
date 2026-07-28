package com.egwallet.service;

import com.egwallet.dto.request.CreateCategoryRequest;
import com.egwallet.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(Long userId, CreateCategoryRequest request);
    CategoryResponse getCategoryById(Long userId, Long categoryId);
    Page<CategoryResponse> getAllCategories(Long userId, Pageable pageable);
    Page<CategoryResponse> getCategoriesByType(Long userId, String type, Pageable pageable);
    List<CategoryResponse> getCategoriesList(Long userId);
    CategoryResponse updateCategory(Long userId, Long categoryId, CreateCategoryRequest request);
    void deleteCategory(Long userId, Long categoryId);
    List<CategoryResponse> getDefaultCategories(String type);
}
