package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.ShippingRequest;
import com.example.vuongstore.dto.response.ShippingResponse;
import com.example.vuongstore.entity.Shipping;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.ShippingMapper;
import com.example.vuongstore.repository.ShippingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ShippingService {
    ShippingRepository shippingRepository;
    ShippingMapper shippingMapper;

    public ShippingResponse createShipping(ShippingRequest request){
        if(shippingRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SHIPPING_EXISTS);
        }
        Shipping shipping = shippingMapper.mapToShipping(request);
        return shippingMapper.toShippingResponse(shippingRepository.save(shipping));
    }

    public ShippingResponse getShippingById(Long id){
        return shippingMapper.toShippingResponse(shippingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        ));
    }

    public List<ShippingResponse> getAllShipping(){
        return shippingRepository.findAll().stream().map(shippingMapper::toShippingResponse).toList();
    }

    public ShippingResponse updateShipping(ShippingRequest request, Long id){
        Shipping shipping = shippingRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ID)
        );
        if(shippingRepository.existsByName(request.getName())){
            throw new AppException(ErrorCode.SHIPPING_EXISTS);
        }
        shippingMapper.updateShipping(request, shipping);
        return shippingMapper.toShippingResponse(shippingRepository.save(shipping));
    }

    public void deleteShipping(Long id){
        if(!shippingRepository.existsById(id)){
            throw new AppException(ErrorCode.INVALID_ID);
        }
        shippingRepository.deleteById(id);
    }
}
