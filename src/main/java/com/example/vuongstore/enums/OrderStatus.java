package com.example.vuongstore.enums;

public enum OrderStatus {
    PENDING,        // Chờ xác nhận
    CONFIRMED,      // Đã xác nhận
    PAID,           // Đã thanh toán
    SHIPPING,       // Đang giao hàng
    COMPLETED,      // Hoàn thành
    CANCELLED       // Đã hủy
}
