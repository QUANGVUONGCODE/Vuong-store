package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.PaymentRequest;
import com.example.vuongstore.dto.response.PaymentResponse;
import com.example.vuongstore.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    Payment mapToPayment(PaymentRequest paymentRequest);
    PaymentResponse toPaymentResponse(Payment payment);
    void updatePayment(PaymentRequest paymentRequest, @MappingTarget Payment payment);
}
