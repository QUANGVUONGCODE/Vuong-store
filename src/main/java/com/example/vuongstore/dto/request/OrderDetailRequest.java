package com.example.vuongstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderDetailRequest {
    @JsonProperty("order_id")
    Long orderId;

    @JsonProperty("product_id")
    Long productId;

    Integer quantity;

}
