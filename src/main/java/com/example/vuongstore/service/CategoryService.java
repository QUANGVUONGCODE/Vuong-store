package com.example.vuongstore.service;


import com.example.vuongstore.dto.request.CategoryRequest;
import com.example.vuongstore.dto.response.CategoryResponse;
import com.example.vuongstore.entity.Category;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.CategoryMapper;
import com.example.vuongstore.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CategoryService {
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CategoryRequest categoryRequest){
        if(categoryRepository.existsByName(categoryRequest.getName())){
            throw new AppException(ErrorCode.CATEGORY_EXISTS);
        }
        Category category = categoryMapper.mapToCategory(categoryRequest);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }


    public CategoryResponse getCategoryById(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(CategoryRequest request, Long id){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        category.setName(request.getName());
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        categoryRepository.deleteById(id);
    }
}
