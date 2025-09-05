package com.example.vuongstore.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShippingRequest {
    @NotBlank(message = "NOT_BLANK_NAME")
    String name;
    String description;
}
