package com.example.vuongstore.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    INVALID_KEY(1000, "Invalid Key", HttpStatus.BAD_REQUEST),
    CATEGORY_EXISTS(1001, "Category already exists", HttpStatus.BAD_REQUEST),
    INVALID_ID(1002, "Invalid ID", HttpStatus.BAD_REQUEST),
    NOT_BLANK_NAME(1003, "Name must not be blank", HttpStatus.BAD_REQUEST),
    NAME_SIZE(1004, "Name size must be between 3 and 200 characters", HttpStatus.BAD_REQUEST),
    PRICE_MIN(1005, "Price must be at least 0", HttpStatus.BAD_REQUEST),
    PRICE_MAX(1006, "Price must not exceed 100000000", HttpStatus.BAD_REQUEST),
    PRODUCT_EXISTS(1007, "Product already exists", HttpStatus.BAD_REQUEST),
    INVALID_THUMBNAIL(1008, "Invalid thumbnail URL", HttpStatus.BAD_REQUEST),
    MAXIMUM_IMAGE_COUNT_EXCEEDED(1009, "Maximum image count exceeded", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE(1010, "Invalid image", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_SIZE(1011, "Invalid image size bigger 5MB", HttpStatus.BAD_REQUEST),
    INVALID_IMAGE_URL(1012, "Invalid image URL", HttpStatus.BAD_REQUEST),
    NOT_BLANL_NAME(1013, "Name can not blank", HttpStatus.BAD_REQUEST),
    BRANDS_EXISTS(1014, "Brand already exists", HttpStatus.BAD_REQUEST),
    PAYMENT_EXISTS(1015,"Payment already exists", HttpStatus.BAD_REQUEST),
    SHIPPING_EXISTS(1016,"Shipping already exists", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_INVALID(1017,"Phone Number invalid", HttpStatus.BAD_REQUEST),
    EMAIL_BLANK(1018, "Email is not blank", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1019, "Password invalid", HttpStatus.BAD_REQUEST),
    ROLE_EXISTS(1020, "Role already exists", HttpStatus.BAD_REQUEST),
    USER_EXISTS(1021, "User already exists", HttpStatus.BAD_REQUEST),
    RETYPE_PASSWORD_WRONG(1022, "Retype password wrong", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_EXISTS(1023, "Phone number already exists", HttpStatus.BAD_REQUEST),
    EMAIL_EXISTS(1024, "Email already exists", HttpStatus.BAD_REQUEST),
    USER_ID_REQUIRED(1025, "User id not null", HttpStatus.BAD_REQUEST),
    FULL_NAME_REQUIRED(1026, "Full name is not null", HttpStatus.BAD_REQUEST),
    PHONE_NUMBER_BLANK(1027, "Phone Number is not Blank", HttpStatus.BAD_REQUEST),
    ADDRESS_BLANK(1028, "Address is not blank", HttpStatus.BAD_REQUEST),
    NOTE_MAX_SIZE(1029, "Note max size is 255", HttpStatus.BAD_REQUEST),
    TOTAL_MONEY_REQUIRED(1030, "Total money is not null", HttpStatus.BAD_REQUEST),
    TOTAL_MONEY_POSITIVE(1031, "Total money is positive", HttpStatus.BAD_REQUEST),
    PAYMENT_ID_REQUIRED(1032, "Payment ID is not Null", HttpStatus.BAD_REQUEST),
    SHIPPING_ID_REQUIRED(1033, "Shipping ID is not null", HttpStatus.BAD_REQUEST),
    INVALID_USER_ID(1034, "User id does not exist", HttpStatus.BAD_REQUEST),
    INVALID_PAYMENT_ID(1035, "Payment id does not exist", HttpStatus.BAD_REQUEST),
    INVALID_SHIPPING_ID(1036, "Shipping id does not exist", HttpStatus.BAD_REQUEST),
    INVALID_ORDER_ID(1037, "Order id does not exist", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_ID(1038, "Product id does not exist", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_PRICE(1039, "Price product cannot null", HttpStatus.BAD_REQUEST),
    INVALID_PRODUCT_QUANTITY(1040, "Quantity product cannot null", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTS(1041, "User not exists", HttpStatus.BAD_REQUEST)
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode statusCode;
}
