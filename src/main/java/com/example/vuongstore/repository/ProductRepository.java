package com.example.vuongstore.repository;

import com.example.vuongstore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);

    @Query("SELECT p FROM Product p WHERE " +
            "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
            "AND (:brandId IS NULL OR :brandId = 0 OR p.brands.id = :brandId) " +
            "AND (:keyword IS NULL OR :keyword = '' OR p.description LIKE %:keyword% OR p.name LIKE %:keyword%)")
    Page<Product> findAll(Pageable pageable,
                          @Param("categoryId") Long categoryId,
                          @Param("brandId") Long brandId,
                          @Param("keyword") String keyword);


    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findByIdIn(@Param("productIds") List<Long> productIds);
}
