package com.example.vuongstore.repository;

import com.example.vuongstore.entity.Brands;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandsRepository extends JpaRepository<Brands, Long> {
    boolean existsByName(String name);

}
