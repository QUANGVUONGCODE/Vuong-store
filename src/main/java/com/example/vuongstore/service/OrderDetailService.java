package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.OrderDetailRequest;
import com.example.vuongstore.dto.response.OrderDetailResponse;
import com.example.vuongstore.entity.Order;
import com.example.vuongstore.entity.OrderDetail;
import com.example.vuongstore.entity.Product;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.OrderDetailMapper;
import com.example.vuongstore.repository.OrderDetailRepository;
import com.example.vuongstore.repository.OrderRepository;
import com.example.vuongstore.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderDetailService {
    OrderDetailRepository orderDetailRepository;
    OrderDetailMapper orderDetailMapper;
    OrderRepository orderRepository;
    ProductRepository productRepository;

    public OrderDetailResponse createOrderDetail(OrderDetailRequest request){
        Order order = orderRepository.findById(request.getOrderId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID));

        Product product = productRepository.findById(request.getProductId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_PRODUCT_ID));

        OrderDetail orderDetail = orderDetailMapper.maptoOrderDetail(request);
        orderDetail.setOrder(order);
        orderDetail.setProduct(product);
        orderDetail.setPrice(product.getPrice());
        orderDetail.setTotalMoney(product.getPrice() * request.getQuantity());
        return orderDetailMapper.maptoOrderDetailResponse(orderDetailRepository.save(orderDetail));
    }

    public List<OrderDetailResponse> getOrderDetailByOrderId(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );

        return orderDetailRepository.findByOrderId(orderId).stream()
                .map(orderDetailMapper::maptoOrderDetailResponse)
                .toList();
    }


}
