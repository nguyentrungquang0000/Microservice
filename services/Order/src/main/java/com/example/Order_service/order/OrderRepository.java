package com.example.Order_service.order;

import com.example.Order_service.order.modal.enumm.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    List<Orders> findByCustomerId(String customerId);
    Page<Orders> findByCustomerId(String customerId, Pageable pageable);

    Page<Orders> findByCustomerIdAndStatus(String customerId, OrderStatus status, Pageable pageable);
    Page<Orders> findSByStatus(OrderStatus status, Pageable pageable);
}
