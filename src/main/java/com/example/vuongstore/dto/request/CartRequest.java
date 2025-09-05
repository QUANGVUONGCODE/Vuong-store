package com.example.vuongstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartRequest {
    @JsonProperty("product_id")
    Long productId;

    @JsonProperty("quantity")
    Integer quantity;
}
