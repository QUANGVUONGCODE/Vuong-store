package com.example.vuongstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageRequest {
    @JsonProperty(value = "product_id")
    Long productId;

    @JsonProperty(value = "image_url")
    String url;
}
