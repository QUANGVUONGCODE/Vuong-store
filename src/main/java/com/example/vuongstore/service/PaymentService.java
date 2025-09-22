package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.PaymentRequest;
import com.example.vuongstore.dto.response.PaymentResponse;
import com.example.vuongstore.entity.Payment;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.PaymentMapper;
import com.example.vuongstore.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)

public class PaymentService {
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;

    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse createPayment(PaymentRequest request){
        if(paymentRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.PAYMENT_EXISTS);
        }
        Payment payment = paymentMapper.mapToPayment(request);
        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    public PaymentResponse getPaymentById(Long id){
        return paymentMapper.toPaymentResponse(paymentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)));
    }


    public List<PaymentResponse> getAllPayment(){
        return paymentRepository.findAll().stream().map(paymentMapper::toPaymentResponse).toList();
    }

    @PreAuthorize("hasRole('ADMIN')")
    public PaymentResponse updatePayment(PaymentRequest request,Long id){
        Payment payment = paymentRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );

        paymentMapper.updatePayment(request, payment);

        return paymentMapper.toPaymentResponse(paymentRepository.save(payment));
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void deletePayment(Long id){
        if(!paymentRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        paymentRepository.deleteById(id);
    }
}
