package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.BrandsRequest;
import com.example.vuongstore.dto.response.BrandsResponse;
import com.example.vuongstore.entity.Brands;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BrandsMapper {
    Brands mapToBrands(BrandsRequest brandsRequest);
    BrandsResponse toBrandsResponse(Brands brands);
    void updateBrands(BrandsRequest brandsRequest,@MappingTarget Brands brands);
}
