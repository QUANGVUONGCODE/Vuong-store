package com.example.vuongstore.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.BindParam;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductImageResponse {
    Long id;

    @JsonProperty(value = "product_id")
    Long productId;

    @JsonProperty(value = "image_url")
    String url;
}
