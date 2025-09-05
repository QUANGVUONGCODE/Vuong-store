package com.example.vuongstore.repository;

import com.example.vuongstore.entity.Shipping;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<Shipping,Long> {
    boolean existsByName(String name);
}


