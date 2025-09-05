package com.example.vuongstore.repository;

import com.example.vuongstore.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    boolean existsByName(String name);
}
