package com.example.vuongstore.dto.request;

import com.example.vuongstore.entity.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderRequest {
    @JsonProperty("user_id")
    @NotNull(message = "USER_ID_REQUIRED")
    Long userId;

    @JsonProperty("full_name")
    String fullName;

    @NotNull(message = "EMAIL_BLANK")
    String email;

    @JsonProperty("phone_number")
    @Size(min = 10, max = 10, message = "PHONE_NUMBER_INVALID")
    @NotBlank(message = "PHONE_NUMBER_BLANK")
    String phoneNumber;

    @NotBlank(message = "ADDRESS_BLANK")
    String address;

    @Size(max = 255, message = "NOTE_MAX_SIZE")
    String note;

    @JsonProperty("payment_id")
    @NotNull(message = "PAYMENT_ID_REQUIRED")
    Long paymentId;

    @JsonProperty("shipping_id")
    @NotNull(message = "SHIPPING_ID_REQUIRED")
    Long shippingId;

    @JsonProperty("cart_items")
    List<CartRequest> cartItems;
}
