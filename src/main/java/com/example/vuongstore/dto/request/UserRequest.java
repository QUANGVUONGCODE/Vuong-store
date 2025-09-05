package com.example.vuongstore.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    @JsonProperty("full_name")
    String fullName;

    @Size(min = 10, max = 10, message = "PHONE_NUMBER_INVALID")
    @JsonProperty("phone_number")
    String phoneNumber;

    @JsonProperty("address")
    String address;

    @NotBlank(message = "EMAIL_BLANK")
    @JsonProperty("email")
    String email;

    @JsonProperty("password")
    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    String password;

    @Size(min = 8, max = 32, message = "PASSWORD_INVALID")
    @JsonProperty("retype_password")
    String retypePassword;

    @JsonProperty("date_of_birth")
    Date dateOfBirth;

    @JsonProperty("google_account_id")
    int googleAccountId;

    @JsonProperty("facebook_account_id")
    int facebookAccountId;
}
