package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.ShippingRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.ShippingResponse;
import com.example.vuongstore.service.ShippingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/shipping")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingController {
    ShippingService shippingService;

    @PostMapping
    ApiResponse<ShippingResponse> createShipping(@RequestBody ShippingRequest request){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.createShipping(request))
                .build();
    }

    @GetMapping("/{id}")
    ApiResponse<ShippingResponse> getShippingById(@PathVariable Long id){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.getShippingById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<ShippingResponse>> getAllShipping(){
        return ApiResponse.<List<ShippingResponse>>builder()
                .result(shippingService.getAllShipping())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<ShippingResponse> updateShipping(@RequestBody ShippingRequest request, @PathVariable Long id){
        return ApiResponse.<ShippingResponse>builder()
                .result(shippingService.updateShipping(request, id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteShipping(@PathVariable Long id){
        shippingService.deleteShipping(id);
        return ApiResponse.<String>builder()
                .result("Shipping deleted successfully")
                .build();
    }
}
