    package com.example.vuongstore.entity;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import jakarta.persistence.*;
    import lombok.AccessLevel;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import lombok.experimental.FieldDefaults;

    import java.util.Date;

    @Entity
    @Table(name = "orders")
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public class Order {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;

        @ManyToOne
        @JoinColumn(name = "user_id")
        User user;

        @Column(name = "full_name", nullable = false, length = 255)
        @JsonProperty("full_name")
        String fullName;

        @Column(name = "email", nullable = false, length = 100)
        String email;

        @Column(name = "phone_number", nullable = false, length = 10)
        String phoneNumber;

        @Column(name = "address", nullable = false, length = 255)
        String address;

        @Column(name = "note", length = 255)
        String note;

        @Column(name = "order_date")
        Date orderDate;

        @Column(name = "status", length = 255)
        String status;

        @Column(name = "total_money")
        Float totalMoney;

        @ManyToOne
        @JoinColumn(name = "payment_id", nullable = false)
        Payment payment;

        @ManyToOne
        @JoinColumn(name = "shipping_id", nullable = false)
        Shipping shipping;

        boolean active;

        @PrePersist
        public void prePersist(){
            this.orderDate = new Date();
        }
    }
