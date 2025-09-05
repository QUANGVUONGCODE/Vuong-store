package com.example.vuongstore.mapper;
import org.mapstruct.Mapper;
import com.example.vuongstore.dto.request.CategoryRequest;
import com.example.vuongstore.dto.response.CategoryResponse;
import com.example.vuongstore.entity.Category;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Category mapToCategory(CategoryRequest categoryRequest);
    CategoryResponse toCategoryResponse(Category category);
    void updateCategory(CategoryRequest categoryRequest, @MappingTarget Category category);
}
