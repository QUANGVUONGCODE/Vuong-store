package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.BrandsRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.BrandsResponse;
import com.example.vuongstore.service.BrandsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/brands")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BrandsController {
    BrandsService brandsService;
    @PostMapping
    ApiResponse<BrandsResponse> createBrands(@RequestBody BrandsRequest request){
        return ApiResponse.<BrandsResponse>builder()
                .result(brandsService.createBrand(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<BrandsResponse> getBrandById(@PathVariable Long id){
        return ApiResponse.<BrandsResponse>builder()
                .result(brandsService.getBrandById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<BrandsResponse>> getAllBrands(){
        return ApiResponse.<List<BrandsResponse>>builder()
                .result(brandsService.getAllBrands())
                .build();
    }


    @PutMapping("/{id}")
    ApiResponse<BrandsResponse> updateBrand(@RequestBody BrandsRequest request, @PathVariable Long id) {
        return ApiResponse.<BrandsResponse>builder()
                .result(brandsService.updateBrand(request, id))
                .build();
    }
    @DeleteMapping("/{id}")
    ApiResponse<String> deleteBrand(@PathVariable Long id) {
        brandsService.deleteBrand(id);
        return ApiResponse.<String>builder()
                .result("Brand deleted successfully")
                .build();
    }
}
