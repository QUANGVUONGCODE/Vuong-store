package com.example.vuongstore.dto.response;

import com.example.vuongstore.entity.Order;
import com.example.vuongstore.entity.Product;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailResponse {
    Long id;

    @JsonProperty("order_id")
    Order order;

    @JsonProperty("product_id")
    Product product;

    Float price;

    Integer quantity;

    @JsonProperty("total_money")
    Float totalMoney;
}
