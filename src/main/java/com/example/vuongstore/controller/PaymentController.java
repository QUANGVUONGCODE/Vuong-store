package com.example.vuongstore.controller;

import com.example.vuongstore.dto.request.PaymentRequest;
import com.example.vuongstore.dto.response.ApiResponse;
import com.example.vuongstore.dto.response.PaymentResponse;
import com.example.vuongstore.service.PaymentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("${api.prefix}/payment")
@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {
    PaymentService paymentService;

    @PostMapping
    ApiResponse<PaymentResponse> createPayment(@RequestBody PaymentRequest request){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.createPayment(request))
                .build();

    }

    @GetMapping("/{id}")
    ApiResponse<PaymentResponse> getPaymentById(@PathVariable Long id){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.getPaymentById(id))
                .build();
    }

    @GetMapping
    ApiResponse<List<PaymentResponse>> getAllPayment(){
        return ApiResponse.<List<PaymentResponse>>builder()
                .result(paymentService.getAllPayment())
                .build();
    }

    @PutMapping("/{id}")
    ApiResponse<PaymentResponse> updatePayment(@RequestBody PaymentRequest request, @PathVariable Long id){
        return ApiResponse.<PaymentResponse>builder()
                .result(paymentService.updatePayment(request,id))
                .build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<String> deletePaymentById(@PathVariable Long id){
        paymentService.deletePayment(id);
        return ApiResponse.<String>builder()
                .result("Payment deleted successfully")
                .build();
    }
}
