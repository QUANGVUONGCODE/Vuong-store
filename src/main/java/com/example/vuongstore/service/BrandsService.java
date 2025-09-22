package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.BrandsRequest;
import com.example.vuongstore.dto.response.BrandsResponse;
import com.example.vuongstore.entity.Brands;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.BrandsMapper;
import com.example.vuongstore.repository.BrandsRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandsService {
    BrandsRepository brandsRepository;
    BrandsMapper brandsMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public BrandsResponse createBrand(BrandsRequest request){
        if(brandsRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.BRANDS_EXISTS);
        }
        Brands brands = brandsMapper.mapToBrands(request);
        return brandsMapper.toBrandsResponse(brandsRepository.save(brands));

    }


    public BrandsResponse getBrandById(Long id) {
        return brandsMapper.toBrandsResponse(brandsRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }

    public List<BrandsResponse> getAllBrands(){
        return brandsRepository.findAll().stream().map(brandsMapper::toBrandsResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public BrandsResponse updateBrand(BrandsRequest request, Long id){
        Brands brands = brandsRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(brandsRepository.existsByName(request.getName())){
                throw new AppException(ErrorCode.BRANDS_EXISTS);
        }
        brandsMapper.updateBrands(request, brands);
        return brandsMapper.toBrandsResponse(brandsRepository.save(brands));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deleteBrand(Long id) {
        if(!brandsRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        brandsRepository.deleteById(id);
    }
}
