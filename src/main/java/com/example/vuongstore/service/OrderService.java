package com.example.vuongstore.service;

import com.example.vuongstore.dto.request.CartRequest;
import com.example.vuongstore.dto.request.OrderRequest;
import com.example.vuongstore.dto.request.requestUpdate.OrderUpdateRequest;
import com.example.vuongstore.dto.response.OrderResponse;
import com.example.vuongstore.entity.*;
import com.example.vuongstore.enums.OrderStatus;
import com.example.vuongstore.exception.AppException;
import com.example.vuongstore.exception.ErrorCode;
import com.example.vuongstore.mapper.OrderDetailMapper;
import com.example.vuongstore.mapper.OrderMapper;
import com.example.vuongstore.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    OrderMapper orderMapper;
    UserRepository userRepository;
    PaymentRepository paymentRepository;
    ShippingRepository shippingRepository;
    ProductRepository productRepository;
    OrderDetailMapper orderDetailMapper;
    OrderDetailRepository orderDetailRepository;

    public OrderResponse createOrder(OrderRequest request){
        User user = userRepository.findById(request.getUserId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_ID)
        );

        Payment payment = paymentRepository.findById(request.getPaymentId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_PAYMENT_ID)
        );

        Shipping shipping = shippingRepository.findById(request.getShippingId()).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_SHIPPING_ID)
        );

        Order order = orderMapper.maptoOrder(request);
        order.setUser(user);
        order.setPayment(payment);
        order.setShipping(shipping);
        order.setActive(true);
        order.prePersist();
        order.setStatus(OrderStatus.PENDING.name());
        order = orderRepository.save(order);
        Float totalMoney = 0f;
        for(CartRequest cartRequest : request.getCartItems()){
            Long productId = cartRequest.getProductId();
            Integer quantity = cartRequest.getQuantity();
            Product product = productRepository.findById(productId).orElseThrow(
                    () -> new AppException(ErrorCode.INVALID_PRODUCT_ID)
            );
            Float price = product.getPrice();
            if(price == null){
                throw new AppException(ErrorCode.INVALID_PRODUCT_PRICE);
            }

            if(quantity == null){
                throw new AppException(ErrorCode.INVALID_PRODUCT_QUANTITY);
            }
            OrderDetail orderDetail = orderDetailMapper.maptoOrderDetail2(cartRequest);
            orderDetail.setOrder(order);
            orderDetail.setProduct(product);
            orderDetail.setPrice(price);
            orderDetail.setTotalMoney(price * quantity);
            totalMoney += price * quantity;
            orderDetailRepository.save(orderDetail);
            orderDetailMapper.maptoOrderDetailResponse(orderDetail);
        }
        order.setTotalMoney(totalMoney);
        orderRepository.save(order);
        return orderMapper.maptoOrderResponse(order);
    }

    public Page<OrderResponse> getAllOrders(String keyword,Pageable pageable){
        return orderRepository.findByKeyword(keyword, pageable).map(orderMapper::maptoOrderResponse);
    }


    public List<OrderResponse> getOrderByUserId(Long userId){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_USER_ID)
        );
        return orderRepository.findByUserId(userId).stream()
                .map(orderMapper::maptoOrderResponse)
                .toList();
    }


    public OrderResponse updateOrder(Long orderId, OrderUpdateRequest request){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        orderMapper.updateOrder(request, order);
        if(request.getStatus() != null){
            order.setStatus(request.getStatus().name());
            if(request.getStatus() == OrderStatus.CANCELLED){
                order.setActive(false);
            }
        }
        orderRepository.save(order);
        return orderMapper.maptoOrderResponse(order);
    }

    public void deleteOrder(Long orderId){
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new AppException(ErrorCode.INVALID_ORDER_ID)
        );
        order.setActive(false);
        orderRepository.save(order);
    }
}
