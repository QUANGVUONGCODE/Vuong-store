package com.example.vuongstore.repository;

import com.example.vuongstore.dto.response.OrderResponse;
import com.example.vuongstore.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);

    @Query("SELECT o FROM Order o WHERE " +
            "(:keyword IS NULL OR :keyword = '' OR " +
            "o.fullName LIKE %:keyword% OR o.address LIKE %:keyword% OR o.email LIKE %:keyword% OR o.note LIKE %:keyword%)")
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);


    boolean existsByIdAndUser_Id(Long orderId, Long userId);
}
