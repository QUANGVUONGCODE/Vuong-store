package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.OrderRequest;
import com.example.vuongstore.dto.request.requestUpdate.OrderUpdateRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.OrderListResponse;
import com.example.vuongstore.dto.response.OrderResponse;
import com.example.vuongstore.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {
    OrderService orderService;

    @PostMapping
    ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.createOrder(orderRequest))
                .build();
    }

    @GetMapping
    ApiResponse<OrderListResponse> getAllOrders(
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit

    ){
        PageRequest pageRequest = PageRequest.of(
                page,
                limit,
                Sort.by("id").ascending()
        );
        Page<OrderResponse> orderPage = orderService.getAllOrders(keyword, pageRequest);
        List<OrderResponse> orders = orderPage.getContent();
        int totalPages = orderPage.getNumber() + 1;
        return ApiResponse.<OrderListResponse>builder()
                .result(OrderListResponse.builder()
                        .orders(orders)
                        .page(totalPages)
                        .build())
                .build();

    }


    @GetMapping("/user/{id}")
    ApiResponse<List<OrderResponse>> getOrdersByUserId(@PathVariable Long id){
        return ApiResponse.<List<OrderResponse>>builder()
                .result(orderService.getOrderByUserId(id))
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody OrderUpdateRequest request){
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.updateOrder(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deleteOrder(@PathVariable Long id){
        orderService.deleteOrder(id);
        return ApiResponse.<String>builder()
                .result("Order deleted successfully")
                .build();
    }
}
