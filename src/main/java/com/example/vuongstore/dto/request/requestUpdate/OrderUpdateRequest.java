package com.example.vuongstore.dto.request.requestUpdate;

import com.example.vuongstore.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderUpdateRequest {
    @JsonProperty("user_id")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    @Size(min = 10, max = 10, message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    String address;

    @Size(max = 255, message = "NOTE_MAX_SIZE")
    String note;


    @JsonProperty("payment_id")
    Long paymentId;

    @JsonProperty("shipping_id")
    Long shippingId;

    @JsonProperty("status")
    OrderStatus status;
}
