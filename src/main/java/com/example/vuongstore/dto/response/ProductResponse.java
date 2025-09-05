package com.example.vuongstore.dto.response;

import com.example.vuongstore.entity.Brands;
import com.example.vuongstore.entity.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductResponse {
    Long id;
    String name;
    Float price;
    String thumbnail;
    String description;

    @JsonProperty(value = "category_id")
    Category category;

    @JsonProperty(value =  "brand_id")
    Brands brands;

    @JsonProperty(value = "created_at")
    LocalDateTime createdAt;

    @JsonProperty(value = "updated_at")
    LocalDateTime updatedAt;


}
