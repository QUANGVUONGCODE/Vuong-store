package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.OrderRequest;
import com.example.vuongstore.dto.request.requestUpdate.OrderUpdateRequest;
import com.example.vuongstore.dto.response.OrderResponse;
import com.example.vuongstore.entity.Order;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.web.bind.annotation.RequestMapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order maptoOrder(OrderRequest orderRequest);
    OrderResponse maptoOrderResponse(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy= NullValuePropertyMappingStrategy.IGNORE)
    void updateOrder(OrderUpdateRequest request, @MappingTarget Order order);
}
