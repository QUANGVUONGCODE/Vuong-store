package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.ShippingRequest;
import com.example.vuongstore.dto.response.ShippingResponse;
import com.example.vuongstore.entity.Shipping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShippingMapper {
    Shipping mapToShipping(ShippingRequest shippingRequest);
    ShippingResponse toShippingResponse(Shipping shipping);

    void updateShipping(ShippingRequest shippingRequest, @MappingTarget Shipping shipping);
}
