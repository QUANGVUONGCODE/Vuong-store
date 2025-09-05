package com.example.vuongstore.mapper;

import com.example.vuongstore.dto.request.CartRequest;
import com.example.vuongstore.dto.request.OrderDetailRequest;
import com.example.vuongstore.dto.response.OrderDetailResponse;
import com.example.vuongstore.entity.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {
    OrderDetailResponse  maptoOrderDetailResponse(OrderDetail orderDetail);
    OrderDetail maptoOrderDetail(OrderDetailRequest request);
    OrderDetail maptoOrderDetail2(CartRequest cartRequest);
}
