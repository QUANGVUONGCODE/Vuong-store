package com.example.vuongstore.dto.request.requestService;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticationRequest {
    @NotBlank(message = "PHONE_NUMBER_INVALID")
    String phoneNumber;

    @NotBlank(message = "PASSWORD_INVALID")
    String password;
}
