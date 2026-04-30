package com.retailpulse.repository;
import com.retailpulse.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findBySaleOrderId(Long saleOrderId);
}
