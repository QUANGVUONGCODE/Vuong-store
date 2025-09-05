package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.ProductRequest;
import com.example.vuongstore.dto.request.requestUpdate.ProductRequestUpdate;
import com.example.vuongstore.dto.response.ProductResponse;
import com.example.vuongstore.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product mapToProduct(ProductRequest productRequest);
    ProductResponse toProductResponse(Product product);
    void updateProductFromRequest(ProductRequestUpdate productRequest, @MappingTarget Product product);
}
