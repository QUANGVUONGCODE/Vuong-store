package com.example.vuongstore.dto.response;

import com.example.vuongstore.entity.Payment;
import com.example.vuongstore.entity.Shipping;
import com.example.vuongstore.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;

    @JsonProperty("user_id")
    User user;

    @JsonProperty("full_name")
    String fullName;

    String email;

    @JsonProperty("phone_number")
    String phoneNumber;

    String address;
    String note;
    String status;

    @JsonProperty("order_date")
    Date orderDate;

    @JsonProperty("total_money")
    Float totalMoney;

    @JsonProperty("payment_id")
    Payment payment;

    @JsonProperty("shipping_id")
    Shipping shipping;

    boolean active;
}
