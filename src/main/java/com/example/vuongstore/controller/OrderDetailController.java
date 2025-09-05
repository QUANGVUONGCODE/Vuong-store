package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.OrderDetailRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.OrderDetailResponse;
import com.example.vuongstore.service.OrderDetailService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/order-details")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailController {
    OrderDetailService orderDetailService;

    @PostMapping
    ApiResponse<OrderDetailResponse> createOrderDetail(@RequestBody OrderDetailRequest orderDetailRequest){
        return ApiResponse.<OrderDetailResponse>builder()
                .result(orderDetailService.createOrderDetail(orderDetailRequest))
                .build();
    }

    @GetMapping("orders/{id}")
    ApiResponse<List<OrderDetailResponse>> getOrderDetailsByOrderId(@PathVariable Long id){
        return ApiResponse.<List<OrderDetailResponse>>builder()
                .result(orderDetailService.getOrderDetailByOrderId(id))
                .build();
    }

}
